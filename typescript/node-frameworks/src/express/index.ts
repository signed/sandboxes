// https://dev.to/cristain/how-to-set-up-typescript-with-nodejs-and-express-2023-gf
import express,  {type Request, type Response, type Application} from 'express'

const app: Application = express();
const port = 3000;
app.use(express.json())

app.get('/', (_req: Request, res: Response) => {
  res.send('Welcome to Express & TypeScript Server');
});

type PostChallengeRequest = {
  challenge: string
}

type PostChallengeResponse = {
  response: string
}

app.post('/api/challenge', (req: Request<unknown, unknown, PostChallengeRequest>, res: Response<PostChallengeResponse>) => {
  console.log('/api/challenge called' )
  const challenge = req.body.challenge;
  const result =  {
    response: `you can not handle the answer to ${challenge}`
  }
  res.send(result)
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})

