/**
 * # Model
 *
 * A constructor for a simple data model
 * object. Has a `type` and contains arbitrary
 * attributes.
 *
 * @param {String} type
 */
export class Model {
  _type
  _attrs: Record<string, any>

  constructor(type: string) {
    this._type = type
    this._attrs = {}
  }

  /**
   * .set (key, value)
   *
   * Set an attribute to be stored in this model.
   */
  set(key: string, value: any) {
    this._attrs[key] = value
  }

  /**
   * .get (key)
   *
   * Get an attribute that is stored in this model.
   *
   * @param {String} key
   */
  get(key: string) {
    return this._attrs[key]
  }
}

// https://github.com/vitest-dev/vitest/blob/main/packages/expect/src/jest-extend.ts
declare global {
  module Chai {
    interface Model {
      (type: string, message?: string): Assertion
    }

    interface Age extends LanguageChains, NumericComparison {
      (age: number, message?: string): Assertion
    }

    interface Assertion {
      model: Model
      age: Age
    }
  }
}

export const ChaiPluginExample: Chai.ChaiPlugin = function (chai, utils) {
  const Assertion = chai.Assertion

  function chainModelAge() {
    // @ts-ignore
    utils.flag(this, 'model.age', true)
  }

  function assertModelAge(this: Chai.AssertionStatic, expectedAge: number, message?: string) {
    const ssfi = utils.flag(this, 'ssfi')
    // make sure we are working with a model
    new Assertion(this._obj, message, ssfi, true).to.be.instanceof(Model)

    // make sure we have an age and its a number
    const actualAge = this._obj.get('age')
    new Assertion(actualAge, message, ssfi, true).to.be.a('number')

    // do our comparison
    this.assert(
      actualAge === expectedAge,
      'expected #{this} to have age #{exp} but got #{act}',
      'expected #{this} to not have age #{act}',
      expectedAge,
      actualAge,
    )
  }

  Assertion.addChainableMethod('age', assertModelAge, chainModelAge)

  Assertion.overwriteMethod('above', function (this: Chai.AssertionStatic, _super: any) {
    return function assertAge(this: Chai.Assertion & Chai.AssertionStatic, expectedAge: number) {
      if (utils.flag(this, 'model.age')) {
        const obj = this._obj

        // first we assert we are actually working with a model
        new Assertion(obj).instanceof(Model)

        // next, make sure we have an age
        new Assertion(obj).to.have.deep.nested.property('_attrs.age').a('number')

        // now we compare
        const actualAge = obj.get('age')
        this.assert(
          actualAge > expectedAge,
          'expected #{this} to have an age above #{exp} but got #{act}',
          'expected #{this} to not have an age above #{exp} but got #{act}',
          expectedAge,
          actualAge,
        )
      } else {
        _super.apply(this, arguments)
      }
    }
  })

  Assertion.overwriteMethod('below', function (this: Chai.AssertionStatic, _super: any) {
    return function assertAge(this: Chai.Assertion & Chai.AssertionStatic, expectedAge: number) {
      if (utils.flag(this, 'model.age')) {
        const obj = this._obj
        // first we assert we are actually working with a model
        new Assertion(obj).instanceof(Model)
        // next, make sure we have an age
        new Assertion(obj).to.have.deep.nested.property('_attrs.age').a('number')

        // now we compare
        const age = obj.get('age')
        this.assert(
          age < expectedAge,
          'expected #{this} to have an age above #{exp} but got #{act}',
          'expected #{this} to not have an age above #{exp} but got #{act}',
          expectedAge,
          age,
        )
      } else {
        _super.apply(this, arguments)
      }
    }
  })

  Assertion.overwriteProperty('ok', function (this: Chai.AssertionStatic, _super: any) {
    return function checkModel(this: Chai.Assertion & Chai.AssertionStatic) {
      const obj = this._obj
      if (obj && obj instanceof Model) {
        const negate = utils.flag(this, 'negate')
        if (negate && !Object.hasOwn(obj._attrs, 'id')) {
          return
        }
        new Assertion(obj).to.have.nested.property('_attrs.id')

        const assertIdType = new Assertion(obj.get('id'), 'model assert ok id type')
        utils.transferFlags(this, assertIdType, false) // false means don't transfer `object` flag
        assertIdType.is.a('number')
      } else {
        _super.call(this)
      }
    }
  })

  Assertion.addMethod('model', function (expectedType) {
    const actualType = this._obj

    // first, our instanceof check, shortcut
    new Assertion(this._obj).to.be.instanceof(Model)

    // second, our type check
    this.assert(
      actualType._type === expectedType,
      'expected #{this} to be of type #{exp} but got #{act}',
      'expected #{this} to not be of type #{act}',
      expectedType,
      actualType._type,
    )
  })
}
