// https://dev.to/cristain/how-to-set-up-typescript-with-nodejs-and-express-2023-gf
import {ExpressBackend} from "./expressBackend.js";


const backend = new ExpressBackend({port: 3000});
backend.start()
