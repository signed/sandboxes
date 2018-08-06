const {expect} = require('chai');

const startRover = (initialPosition) => {
    return (directions) => {
        if (directions.length == 0) {
            return initialPosition;
        }
        return {...initialPosition, y: 1};
    }
};


const finalPosition = () => {
    return startRover;
};

const startingAt = (x) => (y) => direction => {
    return {x, y, direction}
};

describe("hello rover", () => {
    it("rover without commands stays in position", () => {
        expect(finalPosition()(startingAt(0)(0)('N'))([])).to.be.deep.equal({x: 0, y: 0, direction: 'N'})
    });
    describe("move", () => {
        it("one step forward", () => {
            expect(startRover(startingAt(0)(0)('N'))(['f'])).to.be.deep.equal({x: 0, y: 1, direction: 'N'})
        });
    });
});

