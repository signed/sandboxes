// https://dev.to/cristain/how-to-set-up-typescript-with-nodejs-and-express-2023-gf
import { showcaseBackend } from './showcase-backend.js'

const backend = showcaseBackend({ port: 3000 })
backend.start()
