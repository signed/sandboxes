import { expect } from 'chai';
import Person, { sortByName } from './person.js';

it('basic mocha example', () => { // the single test
  const one = new Person(1, 'Bob');
  const two = new Person(2, 'Susan');
  const three = new Person(3, 'Alice');

  const sorted = sortByName([one, two, three]);

  expect(sorted[0]!.id).to.equal(3)
  expect(sorted[1]!.id).to.equal(1)
  expect(sorted[2]!.id).to.equal(2)
});
