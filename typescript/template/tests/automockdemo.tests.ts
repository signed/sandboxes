jest.mock('../src/automockdemo');
import { mocked as mock } from 'ts-jest';
import * as am from '../src/automockdemo';

const amMock = mock(am, true);

describe('one', () => {
  it('should be mocked', () => {
    amMock.one.mockReturnValue('1');
    expect(am.one()).toBe('1');
  });

  it('should ', () => {
    expect(am.two()).toBeUndefined();
  });

  it('should clear mock state between tests', () => {
    expect(am.one()).toBeUndefined();
  });
});
