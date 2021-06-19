'use strict' // No I18N
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path') // No I18N
const os = require('os'); // no I18N

module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static', // No I18N
    assetsPublicPath: '/',
    proxyTable: {
			// proxy all requests starting with /api to jsonplaceholder
			'/memes': {
				 target: 'http://ec2-18-218-95-245.us-east-2.compute.amazonaws.com:8081/',
			}
    },

    // Various Dev Server settings
		host: `localhost`, // can be overwritten by process.env.HOST // no I18N
		// host: 'sathish-2784.csez.zohocorpin.com', // can be overwritten by process.env.HOST // no I18N
    port: 9090, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

    // Use Eslint Loader?
    // If true, your code will be linted during bundling and
    // linting errors and warnings will be shown in the console.
    useEslint: false,
    // If true, eslint errors and warnings will also be shown in the error overlay
    // in the browser.
    showEslintErrorsInOverlay: false,

    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map', // No I18N

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'), // No I18N

    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'), // No I18N
    assetsSubDirectory: 'static', // No I18N
    assetsPublicPath: '/',

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map', // No I18N

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'], // No I18N

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
