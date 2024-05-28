import { useStore } from './store.ts'

export async function runLogic() {
  const deviceInfos = await navigator.mediaDevices.enumerateDevices()
  const cameras = deviceInfos.filter((info) => info.kind === 'videoinput')

  useStore.subscribe(
    (state) => state.camera.current,
    async (cur, _prev) => {
      const state = useStore.getState()
      if (state.mediaStream !== 'no-stream') {
        state.mediaStream.getTracks().forEach((track) => track.stop())
      }
      if (cur === 'no-selection') {
        state.switchMediaStream('no-stream')
        return
      }

      const mediaStream = await navigator.mediaDevices.getUserMedia({
        video: {
          deviceId: cur.deviceId,
          groupId: cur.groupId,
        },
      })
      state.switchMediaStream(mediaStream)
    },
  )

  const state = useStore.getState()
  state.availableCameras(cameras)
  state.selectCamera(cameras[0])
}
