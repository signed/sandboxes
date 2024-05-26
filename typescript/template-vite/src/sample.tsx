import { useState, useReducer } from 'react'

export function Sample({ url }: FetchProps) {
  const [state, dispatch] = useReducer(greetingReducer, initialState)
  const [buttonClicked, setButtonClicked] = useState(false)

  const { error, greeting } = state

  const fetchGreeting = async (url: string) =>
    fetch(url)
      .then(async (response) => {
        const data = await response.json()
        const { greeting } = data
        dispatch({ type: 'SUCCESS', greeting })
        setButtonClicked(true)
      })
      .catch((error) => {
        dispatch({ type: 'ERROR', error })
      })

  const buttonText = buttonClicked ? 'Ok' : 'Load Greeting'

  return (
    <div>
      <button onClick={() => fetchGreeting(url)} disabled={buttonClicked}>
        {buttonText}
      </button>
      {greeting && <h1>{greeting}</h1>}
      {error && <p role="alert">Oops, failed to fetch!</p>}
    </div>
  )
}

type Maybe<T> = T | null

type State = {
  error: Maybe<Error>
  greeting: Maybe<string>
}

type Action = { type: 'SUCCESS'; greeting: string } | { type: 'ERROR'; error: Error }

const initialState: State = {
  error: null,
  greeting: null,
}

function greetingReducer(state: State, action: Action) {
  switch (action.type) {
    case 'SUCCESS': {
      return {
        error: null,
        greeting: action.greeting,
      }
    }
    case 'ERROR': {
      return {
        error: action.error,
        greeting: null,
      }
    }
    default: {
      return state
    }
  }
}

type FetchProps = { url: string }
