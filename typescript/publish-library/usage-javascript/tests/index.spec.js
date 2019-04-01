const index = require('../src/index');

describe('using typescript library', () => {
    test('adds 1 + 2 to equal 3', () => {
        expect(index.sum(1, 2)).toBe(3);
    });

    it('should ', () => {
        expect(index.version()).toEqual('version: 1.0.0');
    });
});
