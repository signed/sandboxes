import { ClientFunction, Selector } from 'testcafe';

fixture('client functions')
  .page('http://localhost:8080/');

test('write into local storage', async t => {
  const setLocalStorage = ClientFunction((key: string, value: string): void => localStorage.setItem(key, value));
  await setLocalStorage('key', 'the-value');
  await t.click(Selector('[data-automation-id=load-from-local-storage]'))
    .expect(Selector('[data-automation-id=value-from-local-storage]').textContent).eql('the-value');
});
