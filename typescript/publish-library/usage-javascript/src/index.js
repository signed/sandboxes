const library = require('@signed/library');

function version() {
    return `version: ${library.version}`
}

function sum(a, b) {
    return a + b;
}

module.exports = {
    version,
    sum
};