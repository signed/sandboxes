// https://dev.to/cristain/how-to-set-up-typescript-with-nodejs-and-express-2023-gf
import type {NextFunction, Request} from 'express';
import express, {type Application, type Response} from 'express'
import type {Server} from 'node:net'
import * as path from 'node:path'

type ExpressBackendConfiguration = {
  port: number;
}

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


export class ExpressBackend {
  private readonly configuration: ExpressBackendConfiguration;
  private delegate: Server | 'not started' = 'not started'
  private readonly app: Application = express();

  constructor(configuration: ExpressBackendConfiguration) {
    this.configuration = configuration;
  }

  start() {
    if (this.delegate !== 'not started') {
      return
    }
    // add middleware
    this.app.disable('x-powered-by');
    this.app.use(express.json())
    this.app.use('/cdn', express.static(path.join(__dirname, 'public')))


    // add routes
    this.app.get('/', (_req: Request, res: Response) => {
      res.send('Welcome to Express & TypeScript Server');
    });

    this.app.get('/throw-error', () => {
      throw Error('look at me')
    })

    this.app.get('/handle-rejected-promises', asyncWrapper(async (_req, res) => {
      const body = await new Promise<string>((_resolve, reject) => {
        setTimeout(() => {
          reject(new Error('has to be an error'))
        }, 0)
      });
      res.send(body)
    }))

    this.app.post('/api/challenge', (req: Request<unknown, unknown, PostChallengeRequest>, res: Response<PostChallengeResponse>) => {
      const challenge = req.body.challenge;
      const result = {
        response: `you can not handle the answer to ${challenge}`
      }
      res.send(result)
    })


    this.app.use((_req, res, _next) => {
      res.status(404).send()
    })

    // custom default error handler for exception thrown synchronously
    // every request handler has to handle its own errors
    // if an exception escapes the request handler, this is always an Internal Server Error HTTP 500
    this.app.use((_error: Error, _req: Request, res: Response, _next: NextFunction) => {
      res.status(500).send()
    })

    // start
    this.delegate = this.app.listen(this.configuration.port, () => {
      console.log(`started`)
    })
  }

  port() {
    if (this.delegate === 'not started') {
      throw new Error('You have to server.start() first')
    }
    const address = this.delegate.address()
    if (address === null || typeof address === 'string') {
      throw new Error('no port information')
    }
    return address.port
  }

  stop() {
    if (this.delegate === 'not started') {
      return
    }
    this.delegate.close()
    this.delegate = 'not started'
    console.log('stopped')
  }
}


const backend = new ExpressBackend({port: 3000});
backend.start()
