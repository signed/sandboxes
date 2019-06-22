import { RequestMock, Selector, RequestLogger } from 'testcafe';

const httpBinUserAgent = new RegExp('https://httpbin.org/user-agent');
const userAgentStub = RequestMock()
  .onRequestTo(httpBinUserAgent)
  .respond({}, 400, {
    'Access-Control-Allow-Origin': '*'
  });

const requestLogger = RequestLogger({ url: httpBinUserAgent, method: 'GET' }, {
  logRequestBody: true,
  stringifyRequestBody: true
});

fixture('mock remote calls')
  .page('http://localhost:8080/');

test('stub response code', async (tc) => {
  await tc.addRequestHooks(userAgentStub);
  await executeRemoteCall(tc);
  await tc.wait(500).expect(Selector('[data-automation-id=remote-call__status]').textContent).eql('400');
});

test('record calls', async (tc) => {
  await tc.addRequestHooks(requestLogger);
  await executeRemoteCall(tc);
  const loggedRequest = requestLogger.requests[0];
  await tc.expect(loggedRequest.request.url).eql('https://httpbin.org/user-agent').expect(loggedRequest.request.body).eql('');
});

async function executeRemoteCall(tc: TestController) {
  return tc.click(Selector('[data-automation-id=remote-call__execute]'));
}
