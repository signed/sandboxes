import {ProductionCode} from '../src/main';
import {suite, test, setup, teardown, suiteSetup, suiteTeardown} from 'mocha';
import {assert} from 'chai';

setup(function () {
  //console.log("top.js setup")
});


suite('main', function () {
  test('initialize properly', function () {
    assert.equal(42, new ProductionCode().value);
  });
});


