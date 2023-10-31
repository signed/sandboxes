import {afterEach, beforeEach} from "vitest";

interface Backend {
  start(): void

  stop(): void
}

interface BackendControl {
  start<T extends Backend>(this: void, backend: T): T
}

export const setupBackendControl = () => {
  const backends: Backend[] = []

  beforeEach(() => {
    backends.length = 0
  })

  afterEach(() => {
    backends.forEach(backend => backend.stop())
  })

  return new (class implements BackendControl {
    start<T extends Backend>(this: void, backend: T): T {
      backend.start()
      backends.push(backend)
      return backend
    }
  })()
}
