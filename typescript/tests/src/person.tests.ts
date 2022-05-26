import {describe, it, expect} from '@jest/globals'
import Person, { sortByName } from './person';

describe('sort by name', () => {
  it('basic example', () => {
    const one = new Person(1, 'Bob');
    const two = new Person(2, 'Susan');
    const three = new Person(3, 'Alice');

    const sorted = sortByName([one, two, three]);
    expect(sorted[0]!.id).toBe(3);
    expect(sorted[1]!.id).toBe(1);
    expect(sorted[2]!.id).toBe(2);
  });
});
