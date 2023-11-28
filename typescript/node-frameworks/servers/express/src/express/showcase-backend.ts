import type {NextFunction, Request, Response} from "express";
import express from "express";
import path from "node:path";
import {fileURLToPath} from "node:url";
import type { ExpressBackendConfiguration} from "../backend-rule/express-backend.js";
import {ExpressBackend} from "../backend-rule/express-backend.js";

type PostChallengeRequest = {
  challenge: string
}
type PostChallengeResponse = {
  response: string
}
const asyncWrapper = (asyncFn: (req: Request, res: Response, next: NextFunction) => Promise<void>) => {
  return function (req: Request, res: Response, next: NextFunction) {
    asyncFn(req, res, next).catch(next)
  }
}

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename);

export const showcaseBackend = (configuration: ExpressBackendConfiguration) => {
  const app = express()
  // add middleware
  app.disable('x-powered-by');
  app.use(express.json())
  app.use('/cdn', express.static(path.join(__dirname, 'public')))


  // add routes
  app.get('/', (_req: Request, res: Response) => {
    res.send('Welcome to Express & TypeScript Server');
  });

  app.get('/throw-error', () => {
    throw Error('look at me')
  })

  app.get('/handle-rejected-promises', asyncWrapper(async (_req, res) => {
    const body = await new Promise<string>((_resolve, reject) => {
      setTimeout(() => {
        reject(new Error('has to be an error'))
      }, 0)
    });
    res.send(body)
  }))

  app.post('/api/challenge', (req: Request<unknown, unknown, PostChallengeRequest>, res: Response<PostChallengeResponse>) => {
    const challenge = req.body.challenge;
    const result = {
      response: `you can not handle the answer to ${challenge}`
    }
    res.send(result)
  })

  app.use((_req, res, _next) => {
    res.status(404).send()
  })

  // custom default error handler for exception thrown synchronously
  // every request handler has to handle its own errors
  // if an exception escapes the request handler, this is always an Internal Server Error HTTP 500
  app.use((_error: Error, _req: Request, res: Response, _next: NextFunction) => {
    res.status(500).send()
  })


  return new ExpressBackend(app, configuration);
};
