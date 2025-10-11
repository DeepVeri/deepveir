import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue' // 引入 @vitejs/plugin-vue 插件

export default defineConfig({
  base: '/', // 确保基础路径正确
  plugins: [vue()], // 添加 vue 插件
  server: {
    port: 5173, // 确保端口与访问地址一致
    open: true, // 自动打开浏览器
    proxy: {
      // 如果有后端接口，添加代理配置
      '/api': {
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
        rewrite: (path) => path
      }
    }
  }
})