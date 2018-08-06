const startRover = (initialPosition) => {
    return (directions) => {
        if (directions.length == 0) {
            return initialPosition;
        }
        return {...initialPosition, y: 1};
    }
};

module.exports = {startRover};