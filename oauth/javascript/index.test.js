const {expect} = require('chai');
const {startRover} = require('./index');

const finalPosition = () => {
    return startRover;
};

describe("hello rover", () => {
    const initialPosition = {x: 0, y: 0, direction: 'N'};

    it.only("rover without commands stays in position", () => {
        expect(finalPosition()(initialPosition)([])).to.be.deep.equal({x: 0, y: 0, direction: 'N'})
    });
    describe("move", () => {
        it("one step forward", () => {
            const finalPosition = startRover(initialPosition)([]);
            expect(finalPosition).to.be.deep.equal({x: 0, y: 0, direction: 'N'})
        });
    });
});

