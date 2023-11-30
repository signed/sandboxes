import { test, expect } from 'vitest'
import express, { type RequestHandler } from 'express'
import { setupBackendControl } from './backend-rule/backend-rule.js'
import { ExpressBackend } from './backend-rule/express-backend.js'
import type { AxiosResponse } from 'axios'
import axios from 'axios'

const { start } = setupBackendControl()

const flup: RequestHandler = (request, response) => {
  response.send(JSON.stringify(request.body))
}

test('add json middleware per route instead of globally', async () => {
  const app = express()
  app.post('/json-middleware', [express.json(), flup])
  app.post('/no-json-middleware', [flup])

  const backend = start(new ExpressBackend(app, { port: 0 }))

  const responseWithMiddleware = await axios.post<any, AxiosResponse<{ one: number }>>(
    `http://localhost:${backend.port()}/json-middleware`,
    { one: 1 },
  )
  expect(responseWithMiddleware.data.one).toEqual(1)

  const responseWithoutMiddleware = await axios.post(`http://localhost:${backend.port()}/no-json-middleware`, {
    one: 1,
  })
  expect(responseWithoutMiddleware.data).toEqual('')
})
