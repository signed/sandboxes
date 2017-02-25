var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');

const config = {
    entry: './app/index.js',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    plugins: [new HtmlWebpackPlugin()],
    devtool: 'source-map'
};

module.exports = config;
