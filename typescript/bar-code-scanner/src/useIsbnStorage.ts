import { create } from 'zustand'
import { immer } from 'zustand/middleware/immer'
import { persist, createJSONStorage } from 'zustand/middleware'

type State = {
  isbnNumbers: string[]
}

export const useIsbnStorage = create<State>()(
  persist(
    immer((_set) => ({
      isbnNumbers: [],
    })),
    {
      name: 'isbn-storage',
      storage: createJSONStorage(() => localStorage),
    },
  ),
)

export const addIsbnNumber = (isbnNumber: string) => {
  const state = useIsbnStorage.getState()
  if (state.isbnNumbers.includes(isbnNumber)) {
    return
  }
  useIsbnStorage.setState((state) => {
    state.isbnNumbers.push(isbnNumber)
  })
}
