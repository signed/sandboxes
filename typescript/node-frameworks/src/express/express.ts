// https://dev.to/cristain/how-to-set-up-typescript-with-nodejs-and-express-2023-gf
import type {Request} from 'express';
import express, {type Application, type Response} from 'express'
import type {Server} from 'node:net'


type ExpressBackendConfiguration = {
  port: number;
}

type PostChallengeRequest = {
  challenge: string
}

type PostChallengeResponse = {
  response: string
}


export class ExpressBackend {
  private readonly configuration: ExpressBackendConfiguration;
  private delegate: Server| 'not started' = 'not started'
  private readonly app: Application = express();

  constructor(configuration: ExpressBackendConfiguration) {
    this.configuration = configuration;
  }

  start() {
    // add middleware
    this.app.use(express.json())

    // add routes
    this.app.get('/', (_req: Request, res: Response) => {
      //await Promise.resolve()
      res.send('Welcome to Express & TypeScript Server');
    });

    this.app.post('/api/challenge', (req: Request<unknown, unknown, PostChallengeRequest>, res: Response<PostChallengeResponse>) => {
      console.log('/api/challenge called')
      const challenge = req.body.challenge;
      const result = {
        response: `you can not handle the answer to ${challenge}`
      }
      res.send(result)
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
  }
}


const backend = new ExpressBackend({port: 3000});
backend.start()
