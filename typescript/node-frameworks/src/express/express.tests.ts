import {describe, expect, test} from "vitest";
import axios, {AxiosHeaders} from 'axios'
import {ExpressBackend} from "./express.js";
import {setupBackendControl} from "../backend-control-rule.js";

const control = setupBackendControl();

test('hello express', async () => {
  const backend = control.start(new ExpressBackend({port: 0}))
  const response = await axios.get(`http://localhost:${backend.port()}`);
  expect(response.status).toEqual(200)
  expect(response.data).toEqual('Welcome to Express & TypeScript Server')
  const headers = response.headers;
  if (!(headers instanceof AxiosHeaders)) {
    throw new Error('no axios header')
  }
  expect(headers.getContentType()).toEqual('text/html; charset=utf-8')
});

test('server static content', async () => {
  const backend = control.start(new ExpressBackend({port: 0}));
  const response = await axios.get(`http://localhost:${backend.port()}/cdn/guard.txt`, {
    validateStatus
  });
  expect(response.status).toEqual(200)
  expect(response.data).toEqual('Stop! Who there?\n')
  const headers = response.headers;
  if (!(headers instanceof AxiosHeaders)) {
    throw new Error('no axios header')
  }
  expect(headers.getContentType()).toEqual('text/plain; charset=UTF-8')
});

describe('make fingerprinting express as server harder', () => {
  test('do not expose details about the server', async () => {
    const backend = control.start(new ExpressBackend({port: 0}));
    const response = await axios.get(`http://localhost:${backend.port()}`);

    const headers = response.headers;
    if (!(headers instanceof AxiosHeaders)) {
      throw new Error('no axios header')
    }
    expect(headers.has('x-powered-by')).toEqual(false);
  });

  test('use a custom 404 message', async () => {
    const backend = control.start(new ExpressBackend({port: 0}));
    const response = await axios.get(`http://localhost:${backend.port()}/not-mapped-in-the-router`, {
      validateStatus
    });
    expect(response.status).toEqual(404)
    expect(response.data).toEqual('')

    const headers = response.headers;
    if (!(headers instanceof AxiosHeaders)) {
      throw new Error('no axios header')
    }
    expect(headers.getContentType()).toBeUndefined()
  });

  test('use custom 500 response', async () => {
    const backend = control.start(new ExpressBackend({port: 0}));
    const response = await axios.get(`http://localhost:${backend.port()}/throw-error`, {
      validateStatus
    });
    expect(response.status).toEqual(500)
    expect(response.data).toEqual('')
    const headers = response.headers;
    if (!(headers instanceof AxiosHeaders)) {
      throw new Error('no axios header')
    }
    expect(headers.has('x-powered-by')).toEqual(false);
    expect(headers.getContentType()).toBeUndefined()
  })
});

const validateStatus = function (_status: number) {
  return true;
}
