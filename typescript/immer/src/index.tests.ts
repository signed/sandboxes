import {expect, test} from "vitest";
import {nextState} from './';

test('state created by immer is immutable on all levels', () => {
  expect(() => nextState.push({title: 'evil', done: true})).toThrow(TypeError)
  expect(() => nextState[0].done = true).toThrow(TypeError)
});

