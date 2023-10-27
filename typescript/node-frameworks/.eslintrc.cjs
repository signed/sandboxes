/* eslint-env node */
// https://typescript-eslint.io/docs/
module.exports = {
    root: true,
    parser: '@typescript-eslint/parser',
    plugins: ['@typescript-eslint'],
    env: {
        'node': true,
        'es2021': true
    },
    extends: ['eslint:recommended', 'plugin:@typescript-eslint/recommended'],
    overrides: [
        {
            files: ['*.ts'],
            rules: {
                '@typescript-eslint/no-empty-function': 'off',
                '@typescript-eslint/no-explicit-any': 'off',
                '@typescript-eslint/no-unused-vars': ['error', {argsIgnorePattern: '^_'}],
                '@typescript-eslint/no-namespace': ['error', {allowDeclarations: true}],
                "@typescript-eslint/consistent-type-imports": "error"
            },
        },
    ],
};
