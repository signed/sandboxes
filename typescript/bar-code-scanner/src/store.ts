import { create } from 'zustand'
import { immer } from 'zustand/middleware/immer'

interface State {
  camera: {
    available: MediaDeviceInfo[]
    current: MediaDeviceInfo | 'no-selection'
  }
}

type Actions = {
  availableCameras: (cameras: State['camera']['available']) => void
  selectCamera: (camera: State['camera']['current']) => void
}

export const useStore = create<State & Actions>()(
  immer((set) => ({
    camera: {
      available: [],
      current: 'no-selection',
    },
    availableCameras: (cameras) =>
      set((state) => {
        state.camera.available = cameras
      }),
    selectCamera: (camera) => {
      set((state) => {
        state.camera.current = camera
      })
    },
  })),
)
