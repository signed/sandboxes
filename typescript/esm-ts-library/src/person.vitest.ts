import { expect, test } from 'vitest';
import Person, { sortByName } from './person.js';

test('basic vitest example', () => {
  const one = new Person(1, 'Bob');
  const two = new Person(2, 'Susan');
  const three = new Person(3, 'Alice');

  const sorted = sortByName([one, two, three]);

  expect(sorted[0]!.id).toEqual(3)
  expect(sorted[1]!.id).toEqual(1)
  expect(sorted[2]!.id).toEqual(2)
});
