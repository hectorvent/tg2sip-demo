module.exports = {
    entry: {
      index: './src/index.js',
      login: './src/login.js'
    },
    output: {
      path: __dirname+'/public/app',
      publicPath: '/app/',
      filename: '[name].js'
    },
    module: {
      rules: [
        {
          test: /\.css$/,
          use: ['style-loader', 'css-loader']
        },
        {
          test: /\.js$/,
          exclude: /node_modules/,
          use: {
               loader: 'babel-loader',
               options: {
                 presets: [
                  ["@babel/preset-env", {
                    useBuiltIns: "usage",
                    corejs: 3
                  }]
                 ],
                 plugins: ['@babel/plugin-proposal-class-properties']
               }
        }
      }
      ]
    }
  };