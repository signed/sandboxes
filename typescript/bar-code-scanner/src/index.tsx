import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'

function scan() {
  if (!('BarcodeDetector' in globalThis)) {
    console.log('Barcode Detector is not supported by this browser.')
  } else {
    console.log('Barcode Detector supported!')

    const image = document.getElementById('code') as HTMLImageElement

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

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <div className="text-lg">Hello Vite!</div>
    <button onClick={scan}> scan</button>
    <img alt="static ean code" id="code" src="/ean-13.png" />
  </React.StrictMode>,
)
