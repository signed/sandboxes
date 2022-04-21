import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'
import { createMachine, interpret } from 'xstate'

// Stateless machine definition
// machine.transition(...) is a pure function used by the interpreter.
const toggleMachine = createMachine({
  id: 'toggle',
  initial: 'inactive',
  states: {
    inactive: { on: { TOGGLE: 'active' } },
    active: { on: { TOGGLE: 'inactive' } },
  },
})

// Machine instance with internal state
const toggleService = interpret(toggleMachine)
  .onTransition((state) => console.log(state.value))
  .start()
// => 'inactive'

toggleService.send('TOGGLE')
// => 'active'

toggleService.send('TOGGLE')
// => 'inactive'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
