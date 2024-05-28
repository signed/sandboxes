import { useStore } from './store.ts'

export async function runLogic() {
  const deviceInfos = await navigator.mediaDevices.enumerateDevices()
  const cameras = deviceInfos.filter((info) => info.kind === 'videoinput')
  const state = useStore.getState()
  state.availableCameras(cameras)
  state.selectCamera(cameras[0])
}
