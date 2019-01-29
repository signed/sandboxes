import { RequestMock, Selector } from 'testcafe';

const mock = RequestMock()
  .onRequestTo(new RegExp('https://httpbin.org/user-agent'))
  .respond({}, 400, {
    'Access-Control-Allow-Origin': '*'
  });

fixture('mock remote calls')
  .page('http://localhost:8080/')
  .requestHooks(mock);

test('mock response code', async (tc) => {
  await tc.expect(Selector('[data-automation-id=remote-call__status]').textContent).eql('400');
});
