const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const paths = {
    src: path.join(__dirname, ''),
    dist: path.join(__dirname, 'dist'),
    data: path.join(__dirname, 'data')
};

const config = {
    mode: 'development',
    context: paths.src,
    entry: {
        main: './app/index.js',
        timescales: './app/timescale/timescales.js',
        negativebar: './app/negativebar/negativebar.js'
    },

    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].bundle.js'
    },

    module: {
        rules: [
            {
                test: /\.tsv$/,
                exclude: [/node_modules/],
                use: [{
                    loader: "file-loader"
                }]
            }

        ],
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
