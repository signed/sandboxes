import { assign, createMachine } from 'xstate'

export type Data = {
  inSession: boolean
  nextPollInMillis: number
}

interface PollingContext {
  nextPollInMillis: number
  subsequentFails: number
  lastData: void | Data
}

const initialContext: PollingContext = {
  nextPollInMillis: 0,
  subsequentFails: 0,
  lastData: undefined,
}

export const pollingMachine = createMachine(
  {
    preserveActionOrder: true, //https://xstate.js.org/docs/guides/context.html#action-order upcoming breaking change in v5
    schema: {
      services: {} as {
        pollResource: {
          data: Data
        }
      },
      events: {} as { type: 'Poll' } | { type: 'Pause' } | { type: 'Ready' },
    },
    tsTypes: {} as import('./pollingMachine.typegen').Typegen0,
    id: 'poll',
    initial: 'idle',
    context: initialContext,
    states: {
      idle: {
        on: {
          Poll: { target: 'active' },
        },
      },
      active: {
        initial: 'scheduleNext',
        on: {
          Pause: { target: 'idle', actions: 'resetContext' },
        },
        states: {
          scheduleNext: {
            after: {
              pollDelay: { target: 'polling' },
            },
          },
          polling: {
            invoke: {
              src: 'pollResource',
              onDone: {
                target: 'verify',
                actions: 'assignDataToContext',
              },
              onError: {
                target: 'scheduleNext',
                actions: 'assignErrorToContext',
              },
            },
          },
          verify: {
            always: [{ target: 'ready', cond: 'readyCondition' }, { target: 'scheduleNext' }],
          },
          ready: {
            type: 'final',
            entry: 'onReady',
          },
          expired: {
            type: 'final',
            entry: 'onExpire',
          },
        },
      },
    },
  },
  {
    actions: {
      assignDataToContext: assign((context, event) => {
        const data = event.data
        return {
          nextPollInMillis: data.nextPollInMillis ?? 5050,
          lastData: data,
          subsequentFails: 0,
        }
      }),
      assignErrorToContext: assign((context) => {
        return {
          nextPollInMillis: 30000, // be smarter on backoff
          subsequentFails: context.subsequentFails + 1,
        }
      }),
      resetContext: assign((_context) => {
        return {
          nextPollInMillis: 0,
          subsequentFails: 0,
          lastData: undefined,
        }
      }),
    },
    delays: {
      pollDelay: (context) => {
        return context.nextPollInMillis
      },
    },
  },
)
