const startBtn = document.querySelector('button#start') as HTMLButtonElement
const stopBtn = document.querySelector('button#stop') as HTMLButtonElement
const intervalSec = document.querySelector('input#interval') as HTMLInputElement
const videoElem = document.querySelector('video') as HTMLVideoElement
const imageCountSpan = document.querySelector('span#image_count') as HTMLSpanElement
const imagesDiv = document.querySelector('div#images') as HTMLDivElement
const storage: ImageBitmap[] = [] // Use this array as our database
let stream: MediaStream
let captureInterval: ReturnType<typeof setInterval>

//hideVid.onclick = () => videoElem.hidden = hideVid.checked;
startBtn.onclick = async () => {
  startBtn.disabled = true

  stream = await navigator.mediaDevices.getUserMedia({ video: true })
  videoElem.onplaying = () => console.log('video playing stream:', videoElem.srcObject)
  videoElem.srcObject = stream

  const [track] = stream.getVideoTracks()
  const processor = new MediaStreamTrackProcessor({ track })
  const reader = await processor.readable.getReader()

  async function readFrame() {
    const { value: frame, done } = await reader.read()
    // value = frame
    if (frame) {
      const bitmap = await createImageBitmap(frame)
      console.log(bitmap)
      storage.push(bitmap)
      const counter = Number.parseInt(imageCountSpan.innerText)
      imageCountSpan.innerText = (counter + 1).toString()
      frame.close()
    }
    if (done) clearInterval(captureInterval)
  }

  stopBtn.disabled = false
  const secondsBetweenCapture = parseInt(intervalSec.value)
  const interval = (secondsBetweenCapture >= 1 ? secondsBetweenCapture : 1) * 1000
  captureInterval = setInterval(async () => await readFrame(), interval)
}

stopBtn.onclick = () => {
  // close the camera
  stream.getTracks().forEach((track) => track.stop())

  // Display each image
  async function showImages() {
    storage.forEach((bitmap) => {
      const width = bitmap.width
      const height = bitmap.height

      const canvas = document.createElement('canvas')
      canvas.width = width
      canvas.height = height
      const ctx = canvas.getContext('bitmaprenderer')
      if (ctx) {
        ctx.transferFromImageBitmap(bitmap)
        imagesDiv.appendChild(canvas)
      }
    })
    storage.length = 0
  }

  console.log('stored images')
  showImages()
}
