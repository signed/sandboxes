import React, { useEffect, useRef } from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'

// Fill the photo with an indication that none has been
// captured.
function clearphoto(photo: HTMLImageElement, canvas: HTMLCanvasElement) {
  const context = canvas.getContext('2d')
  if (context) {
    context.fillStyle = '#AAA'
    context.fillRect(0, 0, canvas.width, canvas.height)
  }

  const data = canvas.toDataURL('image/png')
  photo.setAttribute('src', data)
}

const initialDimensions = () => {
  const width = 640 // We will scale the photo width to this
  const height = width / (4 / 3) // This will be computed based on the input stream
  return {
    width,
    height,
  }
}

function scan() {
  if (!('BarcodeDetector' in globalThis)) {
    console.log('Barcode Detector is not supported by this browser.')
  } else {
    console.log('Barcode Detector supported!')

    const image = document.getElementById('photo') as HTMLImageElement

    console.log(image)
    // create new detector
    const barcodeDetector = new BarcodeDetector({
      formats: ['ean_13'],
    })
    barcodeDetector
      .detect(image)
      .then((data) => {
        const values = data.map((detected) => detected.rawValue)
        console.log(values)
      })
      .catch((e) => console.log(e))
  }
}

const FotoBoth = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null)
  const videoRef = useRef<HTMLVideoElement>(null)
  const photoRef = useRef<HTMLImageElement>(null)
  const dimensionsRef = useRef(initialDimensions())

  useEffect(() => {
    // The width and height of the captured photo. We will set the
    // width to the value defined here, but the height will be
    // calculated based on the aspect ratio of the input stream.

    // |streaming| indicates whether or not we're currently streaming
    // video from the camera. Obviously, we start at false.

    let streaming = false

    // The various HTML elements we need to configure or control. These
    // will be set by the startup() function.

    let video: HTMLVideoElement
    let canvas: HTMLCanvasElement
    let photo: HTMLImageElement

    function startup() {
      const videoFromRef = videoRef.current
      if (!videoFromRef) {
        console.log('no video from ref')
        return
      }
      video = videoFromRef
      canvas = document.getElementById('canvas') as HTMLCanvasElement
      photo = document.getElementById('photo') as HTMLImageElement

      navigator.mediaDevices
        .getUserMedia({ video: true, audio: false })
        .then((stream) => {
          video.srcObject = stream
          video.play()
        })
        .catch((err) => {
          console.error(`An error occurred: ${err}`)
        })

      video.addEventListener(
        'canplay',
        () => {
          console.log('can play')
          if (!streaming) {
            dimensionsRef.current.height = video.videoHeight / (video.videoWidth / dimensionsRef.current.width)
            console.log()

            // Firefox currently has a bug where the height can't be read from
            // the video, so we will make assumptions if this happens.
            if (isNaN(dimensionsRef.current.height)) {
              dimensionsRef.current.height = dimensionsRef.current.width / (4 / 3)
            }

            video.setAttribute('width', dimensionsRef.current.width.toString())
            video.setAttribute('height', dimensionsRef.current.height.toString())
            canvas.setAttribute('width', dimensionsRef.current.width.toString())
            canvas.setAttribute('height', dimensionsRef.current.height.toString())
            streaming = true
          }
        },
        false,
      )

      clearphoto(photo, canvas)
    }
    // Set up our event listener to run the startup process
    // once loading is complete.
    window.addEventListener('load', startup, false)
  }, [])
  // Capture a photo by fetching the current contents of the video
  // and drawing it into a canvas, then converting that to a PNG
  // format data URL. By drawing it on an offscreen canvas and then
  // drawing that to the screen, we can change its size and/or apply
  // other changes before drawing it.

  // Capture a photo by fetching the current contents of the video
  // and drawing it into a canvas, then converting that to a PNG
  // format data URL. By drawing it on an offscreen canvas and then
  // drawing that to the screen, we can change its size and/or apply
  // other changes before drawing it.
  function takepicture() {
    console.log('taking picture')
    const canvas = canvasRef.current
    if (!canvas) {
      console.log('no canvas')
      return
    }
    const photo = photoRef.current
    if (!photo) {
      console.log('no photo element')
      return
    }
    const context = canvas.getContext('2d')
    const { height, width } = dimensionsRef.current
    const video = videoRef.current
    console.log(dimensionsRef.current)
    if (width && height && context && video) {
      canvas.width = width
      canvas.height = height
      context.drawImage(video, 0, 0, width, height)

      const data = canvas.toDataURL('image/png')
      photo.setAttribute('src', data)
    } else {
      console.log('wupsi')
      clearphoto(photo, canvas)
    }
  }

  function startCamera() {
    const video = document.getElementById('video') as HTMLVideoElement
    navigator.mediaDevices
      .getUserMedia({ video: true, audio: false })
      .then((stream) => {
        video.srcObject = stream
        video.play()
      })
      .catch((err) => {
        console.error(`An error occurred: ${err}`)
      })
  }

  function scanFromCamera() {
    takepicture()
    setTimeout(() => {
      scan()
    }, 500)
  }

  return (
    <>
      <div className="camera">
        <button onClick={startCamera}>start camera</button>
        <button onClick={scanFromCamera}>Scan for code</button>
        <video id="video" ref={videoRef}>
          Video stream not available.
        </video>
      </div>

      <canvas id="canvas" ref={canvasRef} />
      <div className="output">
        <img ref={photoRef} id="photo" alt="The screen capture will appear in this box." />
      </div>
    </>
  )
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <FotoBoth />
    <div className="text-lg">Hello Vite!</div>
    <button onClick={scan}> scan</button>
    <img alt="static ean code" id="code" src="/ean-13.png" />
  </React.StrictMode>,
)
