const HtmlWebpackPlugin = require('html-webpack-plugin'); // Require  html-webpack-plugin plugin

module.exports = {
  entry: __dirname + "/src/app/index.js", // webpack entry point. Module to start building dependency graph
  output: {
    path: __dirname + '/dist', // Folder to store generated bundle
    filename: 'bundle.js',  // Name of generated bundle after build
    publicPath: '/' // public URL of the output directory when referenced in a browser
  },
  module: {  // where we defined file patterns and their loaders
      rules: [
        {
          test: /\.(js|jsx)$/,
          use: 'babel-loader',
          exclude: [
            /node_modules/
          ]
        },
        {
          test: /\.html/,
          loader: 'raw-loader'
        }
      ]
  },
  plugins: [  // Array of plugins to apply to build chunk
      new HtmlWebpackPlugin({
          template: __dirname + "/src/public/index-template.html",
          inject: 'body'
      })
  ],
  devServer: {  // configuration for webpack-dev-server
      contentBase: './src/public',  //source of static assets
      port: 3000, // port to run dev-server
      proxy: [{
          context: ['/api/'],
          target: 'http://localhost:8080',
      }]
  }
};