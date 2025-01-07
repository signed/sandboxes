import {describe, test} from "vitest";
import {diff, Options} from "json-diff-ts"
import {deepNestedChange, starWars} from "./json.mother.js";

// https://github.com/ltwlf/json-diff-ts

describe('json-diff-ts', () => {
  test('explore', () => {
    const original = {a: 1, b: 'old', match: 'this'};
    const candidate = {b: 'new', c: {}, match: 'this'};
    const options = {} satisfies Options
    console.log(diff(original, candidate, options));
  });

  test('StarWars', () => {
    const {original, candidate} = starWars();
    const options = {} satisfies Options
    console.log(JSON.stringify(diff(original, candidate, options), null, 2));
  });

  test('Deep Nested', () => {
    const {original, candidate} = deepNestedChange();
    const options = {} satisfies Options
    console.log(JSON.stringify(diff(original, candidate, options), null, 2));
  });
});
