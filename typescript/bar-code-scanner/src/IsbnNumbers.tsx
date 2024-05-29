import { useIsbnStorage } from './useIsbnStorage.ts'

export const IsbnNumbers = () => {
  const state = useIsbnStorage()
  return (
    <div>
      {state.isbnNumbers.length}
      <ul>
        {state.isbnNumbers.map((isbn) => (
          <li key={isbn}>{isbn}</li>
        ))}
      </ul>
    </div>
  )
}
