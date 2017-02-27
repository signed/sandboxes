var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');

const config = {
    entry: {
        main: './app/index.js',
        timescales: './app/timescale/timescales.js',
        negativebar: './app/negativebar/negativebar.js'
    },

    module: {
        loaders: [
            {test: /\.tsv$/, loader: "file-loader"},
        ],
    },

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js'
    },
    plugins: [
        new HtmlWebpackPlugin({
            chunks: ['main']
        }),
        new HtmlWebpackPlugin({
            filename: 'timescale.html',
            chunks: ['timescales']
        }),
        new HtmlWebpackPlugin({
            filename: 'negativebar.html',
            chunks: ['negativebar']
        })]
};

module.exports = config;
