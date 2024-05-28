import { create } from 'zustand'
import { immer } from 'zustand/middleware/immer'
import { subscribeWithSelector } from 'zustand/middleware'

interface State {
  camera: {
    available: MediaDeviceInfo[]
    current: MediaDeviceInfo | 'no-selection'
  }
  mediaStream: MediaStream | 'no-stream'
  capture: {
    reader: ReadableStreamDefaultReader<VideoFrame> | 'no-reader'
    intervalIdentifier: null | ReturnType<typeof globalThis.setInterval>
    image: 'no-image' | ImageBitmap
  }
}

type Actions = {
  availableCameras: (cameras: State['camera']['available']) => void
  selectCamera: (camera: State['camera']['current']) => void
  switchMediaStream: (mediaStream: State['mediaStream']) => void
  switchReader: (reader: State['capture']['reader']) => void
  newIntervalIdentifier: (intervalIdentifier: State['capture']['intervalIdentifier']) => void
  newImage: (image: State['capture']['image']) => void
}

export const useStore = create<State & Actions>()(
  subscribeWithSelector(
    immer((set) => ({
      camera: {
        available: [],
        current: 'no-selection',
      },
      mediaStream: 'no-stream',
      capture: {
        reader: 'no-reader',
        intervalIdentifier: null,
        image: 'no-image',
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
      switchMediaStream: (mediaStream) => {
        set((state) => {
          state.mediaStream = mediaStream
        })
      },
      switchReader: (reader) => {
        set((state) => {
          state.capture.reader = reader
        })
      },
      newIntervalIdentifier: (timeoutIdentifier) => {
        set((state) => {
          state.capture.intervalIdentifier = timeoutIdentifier
        })
      },
      newImage: (image) => {
        set((state) => {
          state.capture.image = image
        })
      },
    })),
  ),
)
