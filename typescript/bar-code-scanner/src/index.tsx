import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { CameraPicker } from './cameraPicker.tsx'
import { runLogic } from './logic.tsx'
import { VideoPreview } from './VideoPreview.tsx'
import { IsbnNumbers } from './IsbnNumbers.tsx'

await runLogic()

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <div className="text-lg">Hello Vite!</div>
    <CameraPicker />
    <VideoPreview />
    <IsbnNumbers />
  </React.StrictMode>,
)
