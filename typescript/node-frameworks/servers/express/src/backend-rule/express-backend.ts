import type { Server } from 'node:net'
import type { Application } from 'express'
import type { Backend } from './backend-rule.js'

export type ExpressBackendConfiguration = {
  port: number
}

export class ExpressBackend implements Backend {
  private readonly configuration: ExpressBackendConfiguration
  private delegate: Server | 'not started' = 'not started'
  private readonly app: Application

  constructor(app: Application, configuration: ExpressBackendConfiguration) {
    this.configuration = configuration
    this.app = app
  }

  start() {
    if (this.delegate !== 'not started') {
      return
    }

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
