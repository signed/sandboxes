import { useEffect, useRef } from 'react'
import { useStore } from './store.ts'

export const VideoPreview = () => {
  const videoRef = useRef<HTMLVideoElement>(null)
  const mediaStream = useStore((state) => state.mediaStream)

  useEffect(() => {
    const video = videoRef.current
    if (video === null) {
      return
    }
    if (mediaStream === 'no-stream') {
      video.srcObject = null
    } else {
      video.srcObject = mediaStream
    }
  }, [mediaStream])

  return <video ref={videoRef} autoPlay muted playsInline />
}
