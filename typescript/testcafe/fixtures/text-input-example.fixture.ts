import { ClientFunction, Selector } from 'testcafe';

fixture('interact with input elements')
  .page('http://localhost:8080/');

test('email input', async (tc) => {
  const emailInput = Selector('#email-input');

  await tc.typeText(emailInput, 'initial@example.org');
  await tc.expect(emailInput.value).eql('initial@example.org');

  await tc.typeText(emailInput, 'updated@example.org', { replace: true });
  await tc.expect(emailInput.value).eql('updated@example.org');
});
