import { hello } from '../src';

const name: string = 'Susan';

describe('one', () => {
  it('should fly', () => {
    expect(hello(name)).toBe('Hello Susan!');
  });
});
