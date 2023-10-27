import {expect, test} from "vitest";
import axios from 'axios'
import {ExpressBackend} from "./express.js";

test('hello express', async () => {
  const backend = new ExpressBackend({port: 0});
  backend.start()
  const response = await axios.get(`http://localhost:${backend.port()}`);
  expect(response.data).toEqual('Welcome to Express & TypeScript Server')
  backend.stop()
});
