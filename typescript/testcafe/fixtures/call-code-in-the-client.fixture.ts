import { ClientFunction } from 'testcafe';

const getPageUrl = ClientFunction(() => window.location.href);

fixture('run code in client')
  .page('http://localhost:8080/');

test('access document uri in client', async (tc) => {
  await tc.expect(tc.eval(() => document['documentURI'])).eql('http://localhost:8080/');
});

test('Get UA', async (tc) => {
  await tc.expect(tc.eval(() => navigator.vendor)).eql('Google Inc.');
});

test('Check the page URL', async (tc) => {
  await tc.expect(getPageUrl()).contains('http://localhost:8080/');
});
