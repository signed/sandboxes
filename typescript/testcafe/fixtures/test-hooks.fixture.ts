import 'testcafe';

fixture.only('the fixture')
  .page`http://example.com`
  .beforeEach(async t => {
    t.ctx.someProp = 'initialize shared prop';
    console.log(`before each "${t.ctx.someProp}"`);
  })
  .afterEach(async t => {
    console.log(`after each "${t.ctx.someProp}"`);
  });

test('asdf', async t => {
  console.log(`the test "${t.ctx.someProp}"`);
});

test
  .before(async t => {
    console.log(`before "${t.ctx.someProp}"`);
  })('override test hooks', async t => {
    console.log(`test with hook overrides "${t.ctx.someProp}"`);
  })
  .after(async t => {
    console.log(`after "${t.ctx.someProp}"`);
  });
