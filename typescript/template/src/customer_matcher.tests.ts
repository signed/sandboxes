import {describe, expect, test} from "vitest";
import './matchers/toEndWith'

describe('ends with matcher', () => {
  test('actually ends with the suffix ', () => {
    expect('Hello Matcher').toEndWith('Matcher');
  });
  test('does not end with the suffix', () => {
    expect('Hello Matcher').not.toEndWith('Bogus');
  });
});
