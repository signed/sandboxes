import { useIsbnStorage } from './useIsbnStorage.ts'

const downloadData = (filename: string, data: object) => {
  const blob = new Blob([JSON.stringify(data)], { type: 'text/json' })
  const link = document.createElement('a')

  link.download = filename
  link.href = window.URL.createObjectURL(blob)
  link.dataset.downloadurl = ['text/json', link.download, link.href].join(':')
  link.click()
  link.remove()
}
const downloadScannedCodes = (data: object) => {
  downloadData('isbn-numbers.json', data)
}

export const IsbnNumbers = () => {
  const { isbnNumbers } = useIsbnStorage()
  return (
    <div>
      <button
        onClick={() => downloadScannedCodes(isbnNumbers)}
        className="inline-flex items-center rounded bg-gray-300 px-4 py-2 font-bold text-gray-800 hover:bg-gray-400"
      >
        <svg className="mr-2 h-4 w-4 fill-current" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
          <path d="M13 8V2H7v6H2l8 8 8-8h-5zM0 18h20v2H0v-2z" />
        </svg>
        <span>Download {isbnNumbers.length}</span>
      </button>

      <ul>
        {isbnNumbers.map((isbn) => (
          <li key={isbn}>{isbn}</li>
        ))}
      </ul>
    </div>
  )
}
