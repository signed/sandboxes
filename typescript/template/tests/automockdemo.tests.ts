jest.mock('../src/automockdemo');
import * as am from '../src/automockdemo';

describe('one', () => {
  // The draw back of this approach is the type error when calling mockReturnValue
  it('should be mocked', () => {
    am.one.mockReturnValue('1');
    expect(am.one()).toBe('1');
  });

  it('should ', () => {
    expect(am.two()).toBeUndefined();
  });

  it('should clear mock state between tests', () => {
    expect(am.one()).toBeUndefined();
  });
});
