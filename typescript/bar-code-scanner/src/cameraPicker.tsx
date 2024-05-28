import { useStore } from './store.ts'

export const CameraPicker = () => {
  const { available, current } = useStore((state) => state.camera)
  const selectCamera = useStore((state) => state.selectCamera)
  return (
    <ul>
      {available.map((camera) => {
        const selected = current !== 'no-selection' && camera.deviceId === current.deviceId
        const selectedMarker = selected ? '*' : ''
        return (
          <li key={camera.deviceId} onClick={() => selectCamera(camera)}>
            {camera.label + selectedMarker}
          </li>
        )
      })}
    </ul>
  )
}
