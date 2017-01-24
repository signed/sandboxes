import {assert} from 'chai';
import {suite, test, setup, teardown, suiteSetup, suiteTeardown} from 'mocha';
import {Rule} from './rule';

suite("rule sample", function () {
  const rule = new Rule();
  rule.setupRule();

  test('before from rule should have run', function () {
    assert.equal(rule.state, 'initialized in setup');
  });

});
