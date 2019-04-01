const index = require('../src/index');

describe('using typescript library in javascript', () => {
    it('should work with just the import', () => {
        expect(index.sum(1, 2)).toBe(3);
    });

    it('should work with constant', () => {
        expect(index.version()).toEqual('version: 1.0.0');
    });
    it('should work with functions', () => {
        expect(index.helloPaul()).toEqual('Hello Paul');
    });
});
