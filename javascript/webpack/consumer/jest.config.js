module.exports = {
  "preset": 'ts-jest',
  "roots": [
    "<rootDir>/test"
  ],
  "moduleDirectories": [
    "node_modules",
    "<rootDir>/src"
  ],
  "setupTestFrameworkScriptFile": "<rootDir>/test/scripts/testSetup.ts"
};