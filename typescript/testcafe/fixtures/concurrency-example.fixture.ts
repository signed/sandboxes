// sample taken from https://github.com/DevExpress/testcafe/issues/2228
import { ClientFunction } from 'testcafe';

fixture.skip('Concurrent').page('example.com');

const timeline: Array<string> = [];

test('Long test', async t => {
  timeline.push('long started');

  await t.wait(5000);

  timeline.push('long finished');
});

test('Short test', async t => {
  timeline.push('short started');

  await t.wait(1000);

  timeline.push('short finished');
});

test('Results', async t => {
  await t.wait(6000);

  await t.expect(timeline).eql(['long started', 'short started', 'short finished', 'long finished']);
});
