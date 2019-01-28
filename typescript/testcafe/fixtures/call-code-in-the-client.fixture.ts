import { Selector } from 'testcafe';

fixture('Getting Started')
  .page('http://localhost:8080/');

test.only('access document uri in client', async (tc) => {
  await tc.expect(tc.eval(() => document['documentURI'])).eql('http://localhost:8080/') ;
});
