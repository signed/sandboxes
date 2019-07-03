import 'testcafe';

const printPropertiesFromContext = (t: TestController, name: string) => {
  console.log(`${name} "${t.ctx.someProp}" "${t.fixtureCtx.fixtureProperty}"`);
};

fixture('the fixture')
  .page`http://example.com`
  .before(async (ctx) => {
    ctx.fixtureProperty = 'initialized in fixture before';
    console.log(`fixture before "${ctx.fixtureProperty}"`);
  })
  .beforeEach(async t => {
    t.ctx.someProp = 'initialize shared prop';
    printPropertiesFromContext(t, 'before each');
  })
  .afterEach(async t => {
    printPropertiesFromContext(t, 'after each');
  })
  .after(async (ctx) => {
    console.log(`fixture after "${ctx.fixtureProperty}"`);
  });

test('use test hooks from fixture', async t => {
  printPropertiesFromContext(t, 'the test');
});

test
  .before(async t => {
    printPropertiesFromContext(t, 'test before');
  })('override test hooks', async t => {
    printPropertiesFromContext(t, 'test with hook overrides');
  })
  .after(async t => {
    printPropertiesFromContext(t, 'test after');
  });
