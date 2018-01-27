const webpack = require('webpack');

const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const distDir = path.resolve(__dirname, 'dist');

module.exports = {
  context: path.resolve(__dirname, 'app'),
  entry: {
    app: './scripts/app.js',
    vendor: ['angular', 'angular-moment', '@uirouter/angularjs', 'angular-nvd3', 'angular-ui-bootstrap']
  },
  devtool: 'source-map',
  devServer: {
    contentBase: distDir,
    port: 9000
  },
  plugins: [
    new CleanWebpackPlugin(['dist']),
    new CopyWebpackPlugin([
      {from:'index.html',to: distDir}
    ]),
    new ExtractTextPlugin('styles.css'),
    new webpack.optimize.CommonsChunkPlugin({name: "vendor", filename: "vendor.bundle.js"})
  ],
  output: {
    filename: '[name].bundle.js',
    path: distDir
  },
  module: {
    rules: [
      { test: /\.html$/, loader: 'ng-cache-loader?prefix=views' },
      { test: /\.css$/, loader: ExtractTextPlugin.extract({loader: 'css-loader', query: {sourceMap: true} } ) }
    ]
  }
};