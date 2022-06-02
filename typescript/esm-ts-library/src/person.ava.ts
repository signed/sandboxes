import test, { ExecutionContext } from 'ava';
import Person, { sortByName } from './person.js';

test('basic example', async (t: ExecutionContext) => {
  const one = new Person(1, 'Bob');
  const two = new Person(2, 'Susan');
  const three = new Person(3, 'Alice');

  const sorted = sortByName([one, two, three]);

  t.is(sorted[0]!.id, 3);
  t.is(sorted[1]!.id, 1);
  t.is(sorted[2]!.id, 2);
});
