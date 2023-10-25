import {describe, test, expect} from "vitest";
import { hello } from './index.js';

const name: string = 'Susan';

describe('one', () => {
  test('should fly', () => {
    expect(hello(name)).toBe('Hello Susan!');
  });
});
