const library = require('@signed/library');

function version() {
    return `version: ${library.version}`
}

function helloPaul() {
    return library.hello('Paul');
}

function sum(a, b) {
    return a + b;
}

module.exports = {
    version,
    helloPaul,
    sum
};