// This file was automatically generated. Edits will be overwritten

export interface Typegen0 {
  '@@xstate/typegen': true
  eventsCausingActions: {
    resetContext: 'Pause'
    assignDataToContext: 'done.invoke.poll.active.polling:invocation[0]'
    assignErrorToContext: 'error.platform.poll.active.polling:invocation[0]'
    onReady: ''
    onExpire: 'xstate.init'
  }
  internalEvents: {
    'done.invoke.poll.active.polling:invocation[0]': {
      type: 'done.invoke.poll.active.polling:invocation[0]'
      data: unknown
      __tip: 'See the XState TS docs to learn how to strongly type this.'
    }
    'error.platform.poll.active.polling:invocation[0]': {
      type: 'error.platform.poll.active.polling:invocation[0]'
      data: unknown
    }
    '': { type: '' }
    'xstate.after(pollDelay)#poll.active.scheduleNext': { type: 'xstate.after(pollDelay)#poll.active.scheduleNext' }
    'xstate.init': { type: 'xstate.init' }
  }
  invokeSrcNameMap: {
    pollResource: 'done.invoke.poll.active.polling:invocation[0]'
  }
  missingImplementations: {
    actions: 'onReady' | 'onExpire'
    services: 'pollResource'
    guards: 'readyCondition'
    delays: never
  }
  eventsCausingServices: {
    pollResource: 'xstate.after(pollDelay)#poll.active.scheduleNext'
  }
  eventsCausingGuards: {
    readyCondition: ''
  }
  eventsCausingDelays: {
    pollDelay: 'xstate.init'
  }
  matchesStates:
    | 'idle'
    | 'active'
    | 'active.scheduleNext'
    | 'active.polling'
    | 'active.verify'
    | 'active.ready'
    | 'active.expired'
    | { active?: 'scheduleNext' | 'polling' | 'verify' | 'ready' | 'expired' }
  tags: never
}
