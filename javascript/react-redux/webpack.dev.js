const path = require("path");
const merge = require("webpack-merge");
const common = require("./webpack.config");

module.exports = merge(common, {
  mode: "development",
  devtool: "inline-source-map",
  serve: {
    // https://github.com/webpack-contrib/webpack-serve#options
    content: path.resolve(__dirname, "dist")
  }
});
