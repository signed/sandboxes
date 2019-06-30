import 'testcafe';

fixture.only('the fixture')
  .page`http://example.com`
  .beforeEach(async t => {
    console.log('before each');
  })
  .afterEach(async t => {
    console.log('after each');
  });

test('asdf', async t => {
  console.log('the test');
});

test
  .before(async t => {
    console.log('before');
  })('override test hooks', async t => {
    console.log('test with hook overrides');
  }).after(async t => {
    console.log('after');
  });
