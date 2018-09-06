const recursive = (counter) => {
    if (0 === counter) {
        return;
    }
    const newCounter = counter - 1;
    console.log(newCounter);
    recursive(newCounter);
};

describe('just a test', () => {
    it('should run', () => {
        recursive(10);
    });
});