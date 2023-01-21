module.exports = {
    root: true,
    plugins: [
        '@typescript-eslint',
        'eslint-plugin-expect-type'
    ],
    parser: '@typescript-eslint/parser',
    parserOptions: {
        project: "./tsconfig.json",
    },
    extends: [
        'eslint:recommended',
        'plugin:@typescript-eslint/recommended',
        'plugin:@typescript-eslint/recommended-requiring-type-checking',
        'plugin:@typescript-eslint/strict',
        'plugin:expect-type/recommended'
    ]
};
