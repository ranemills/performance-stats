const webpack = require('webpack');

const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  context: path.resolve(__dirname, 'app'),
  entry: {
    app: './scripts/app.js',
    vendor: ['angular', 'angular-moment', '@uirouter/angularjs', 'angular-nvd3', 'angular-ui-bootstrap']
  },
  devtool: 'source-map',
  plugins: [
    // new CleanWebpackPlugin(['dist']),
    new webpack.optimize.CommonsChunkPlugin({name: "vendor", filename: "vendor.bundle.js"})
  ],
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist')
  },
  module: {
    rules: [
      { test: /\.html$/, loader: 'ng-cache-loader?prefix=views' }
    ]
  }
};