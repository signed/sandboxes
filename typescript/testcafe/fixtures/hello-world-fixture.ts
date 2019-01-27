import { Selector } from 'testcafe';

fixture('Getting Started').page('http://localhost:8080/');

test('My first test', async (tc) => {
  const selector = Selector('#root > ul > li:nth-child(2)');
  await tc.expect(selector.textContent).eql('two');
});
