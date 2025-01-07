import {describe, test} from "vitest";
import {diff, Options} from "json-diff-ts"
import {deepNestedChange, DiffInput, starWars} from "./json.mother.js";

// https://github.com/ltwlf/json-diff-ts

describe('json-diff-ts', () => {
  test('explore', () => {
    const original = {a: 1, b: 'old', match: 'this'};
    const candidate = {b: 'new', c: {}, match: 'this'};
    diffFor(() => ({original, candidate}), options())
  });

  test('StarWars', () => {
    diffFor(starWars, options())
  });

  test('Deep Nested', () => {
    diffFor(deepNestedChange, options())
  });
});

const options = (options: Options = {}): Options => {
  return options
}

const diffFor = (diffInputProvider: () => DiffInput, options?: Options) => {
  const {original, candidate} = diffInputProvider();
  console.log(JSON.stringify(diff(original, candidate, options), null, 2));
}

