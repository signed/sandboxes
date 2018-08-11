import { hello } from '../src';

describe('one', () => {
  it('should fly', () => {
    expect(hello('world')).toBe('Hello world!');
  });
});
