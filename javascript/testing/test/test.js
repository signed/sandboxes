import {assert} from 'chai';
import {suite, test, setup, teardown, suiteSetup, suiteTeardown } from 'mocha'

suite('Array', function () {

  suite('#indexOf()', function () {
    suiteSetup(function () {
      doLog('suiteSetup');
    });

    suiteTeardown(function () {
      doLog('suiteTeardown')
    });

    setup(function () {
      doLog('setup')
    });

    teardown(function () {
      doLog('teardown')
    });

    test('should return -1 when the value is not present', function () {
      assert.equal(-1, [1, 2, 3].indexOf(4));
    });

    test('should return the index when the value is present', function () {
      assert.equal(1, [1, 2, 3].indexOf(2));
    });
  });

  function doLog(message) {
    console.log(message)
  }
});
