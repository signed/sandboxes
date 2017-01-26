import {ProductionCode} from '../src/es6/code';
import {ConstructorMethod} from '../src/es5/code'
import {suite, test, setup, teardown, suiteSetup, suiteTeardown} from 'mocha';
import {assert} from 'chai';

setup(function () {
  //console.log("top.js setup")
});

suite('main', function () {

  suite('es6', function () {
    const productionCode = new ProductionCode();

    test('initialize properly', function () {
      assert.equal(42, productionCode.value);
    });

    test('call a method', function () {
      assert.equal(43, productionCode.sampleMethod(1));
    });
  });

  suite('es5', function () {
    const constructorMethod = new ConstructorMethod();
    test('initialize properly', function () {
      assert.equal(42, constructorMethod.value);
    });

    test('call a method', function () {
      assert.equal(43, constructorMethod.sampleMethod(1));
    });
  });

});


