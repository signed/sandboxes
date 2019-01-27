import { Selector } from 'testcafe';
import { ReactSelector, waitForReact } from 'testcafe-react-selectors';

fixture('Getting Started')
  .page('http://localhost:8080/')
  .beforeEach(async () => {
    await waitForReact();
  });

test('vanilla selectors', async (tc) => {
  const selector = Selector('#root > ul > li').nth(1);
  await tc.expect(selector.textContent).eql('two');
});

test('react selectors', async (tc) => {
  const selector = ReactSelector('ListItem').nth(1);
  await tc.expect(selector.textContent).eql('two');
});
