import {describe, test} from "vitest";
import {diff, DiffOptions, diffString} from "json-diff";
import {deepNestedChange, starWars} from "./json.mother.js";

// https://github.com/andreyvit/json-diff

describe('json-diff', () => {
  test('explore', () => {
    const original = {a: 1, b: 'old', match: 'this'};
    const changed = {b: 'new', c: {}, match: 'this'};
    const options = {keysOnly: true, excludeKeys: ['a']} satisfies DiffOptions;
    console.log(diff(original, changed, options))
    console.log(diffString(original, changed, options))
  });

  test('StarWars', () => {
    const { original, candidate} = starWars();
    const options = { } satisfies DiffOptions;
    console.log(diffString(original, candidate, options))
  });

  test('Deep Nested', () => {
    const {original, candidate} = deepNestedChange();
    const options = {} satisfies DiffOptions
    console.log(diffString(original, candidate, options));
  });
});
