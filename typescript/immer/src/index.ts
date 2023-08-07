// https://immerjs.github.io/immer/
import {produce} from "immer"

type Task = {
  title: string
  done: boolean
}

const baseState: Task [] = [
  {
    title: "Learn TypeScript",
    done: true
  },
  {
    title: "Try Immer",
    done: false
  }
]

export const nextState = produce(baseState, draft => {
  draft[1].done = true
  draft.push({title: "Tweet about it", done:false})
})