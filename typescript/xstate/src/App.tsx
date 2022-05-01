import { useMachine } from '@xstate/react'
import { useState } from 'react'
import { Data, pollingMachine } from './machines/pollingMachine'

export function App() {
  const [data, setData] = useState<Data>()
  const [state, send] = useMachine(pollingMachine, {
    services: {
      pollResource: async () => {
        const response = await fetch('https://httpbin.org/get', {
          method: 'GET',
          headers: {
            accept: 'application/json',
          },
        })
        if (response.status === 200) {
          return await response.json()
        }
        throw new Error(`status code ${response.status}`)
      },
    },
    guards: {
      readyCondition: (context) => {
        return context.lastData?.inSession === true
      },
    },
    actions: {
      onReady: (context) => {
        if (context.lastData) {
          setData(context.lastData)
        }
      },
      onExpire: () => {
        console.log('expired')
      },
    },
  })

  return (
    <>
      <button
        onClick={() => {
          send({ type: 'Poll' })
        }}
      >
        Poll
      </button>
      <button
        onClick={() => {
          send({ type: 'Pause' })
        }}
      >
        Pause
      </button>
      <pre>{JSON.stringify(data, null, 2)}</pre>

      <pre>{JSON.stringify(state.value, null, 2)}</pre>
      <pre>{JSON.stringify(state.context, null, 2)}</pre>
    </>
  )
}
