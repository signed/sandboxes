https://devblogs.microsoft.com/typescript/announcing-typescript-4-7/
https://yonatankra.com/how-to-use-the-new-ecmascript-module-in-typescript/


https://gist.github.com/sindresorhus/a39789f98801d908bbc7ff3ecc99d99c


# Modules
https://exploringjs.com/es6/ch_modules.html

# Testing
https://stackoverflow.com/questions/59584492/do-any-javascript-test-frameworks-support-es-modules
https://github.com/avajs/ava/blob/main/docs/recipes/typescript.md#for-packages-with-type-module

# Examples

https://github.com/sindresorhus/ky targeting deno


# docs
https://nodejs.org/api/esm.html
https://github.com/TypeStrong/ts-node#options
https://github.com/TypeStrong/ts-node#transpileonly

# mocha
- https://stackoverflow.com/questions/40635956/overriding-tsconfig-json-for-ts-node-in-mocha
- https://github.com/mochajs/mocha-examples/issues/47
- https://mochajs.org/#configuring-mocha-nodejs
- https://github.com/mochajs/mocha/blob/master/example/config/.mocharc.yml
- https://gist.github.com/jordansexton/2a0c3c360aa700cc9528e89620e82c3d

"test": "env TS_NODE_COMPILER_OPTIONS='{\"module\": \"commonjs\" }' mocha -r ts-node/register 'tests/**/*.ts'"

# ava
- https://github.com/avajs/ava/blob/main/docs/recipes/typescript.md
- https://github.com/avajs/ava/issues/2593


https://github.com/TypeStrong/ts-node#recipes
