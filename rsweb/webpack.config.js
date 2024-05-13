const config = require('config')
const fse = require('fs-extra')
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

fse.writeFileSync(path.resolve(__dirname, 'build/client.json'), JSON.stringify(config));

module.exports = {
    mode: "development",
    entry: './src/main/webshell/console.js',
    output: {
        path: path.resolve(__dirname, 'src/main/resources/static'),
        filename: 'app.bundle.js'
    },
    plugins: [
        new CleanWebpackPlugin(),
        new CopyWebpackPlugin([
            {context: 'src/main/webshell/', from: 'config/*.json'}
        ]),
        new HtmlWebpackPlugin({
            template: 'src/main/webshell/index.html'
        })
    ],
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|svg|jpg|gif)$/,
                use: [
                    'file-loader'
                ]
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    'file-loader'
                ]
            }
            ,
            {
                test: /\.json$/,
                include: path.resolve(__dirname, 'src/main/webshell/config'),
                use: [
                   'json-loader'
                ]
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.json', '.wasm', '.mjs'],
        alias: {
            config: path.resolve(__dirname, 'build/client.json')
        }
    },
    devtool: 'inline-source-map',
    devServer: {
        contentBase: './dist',
        hot: true
    }
};
