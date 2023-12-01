const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      "/payresult": {
        target: "http://localhost:3000",
        changeOrigin: true,
      },
    },
  },
});
