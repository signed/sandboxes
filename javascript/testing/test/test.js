import {assert} from "chai";

describe('Array', () => {
  describe('#indexOf()', () => {
    before(function() {
      console.log('before')
    });

    after(function() {
      console.log('after')
    });

    beforeEach(function() {
      console.log('before each')
    });

    afterEach(function() {
      console.log('after each')
    });

    it('should return -1 when the value is not present', () => {
      assert.equal(-1, [1, 2, 3].indexOf(4));
    });

    it('should return the index when the value is not present', () => {
      assert.equal(1, [1, 2, 3].indexOf(2));
    });
  });
});
