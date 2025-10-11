import { createApp } from 'vue'
import App from './App.vue'
import router from './router'  // 修改路径为 './router'，因为 index.js 是默认入口文件
import axios from 'axios'

// 配置 axios
axios.defaults.baseURL = 'http://localhost:8080'  // 后端服务器地址
axios.defaults.timeout = 5000  // 请求超时时间
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // 添加请求日志
    console.log('Request:', {
      url: config.url,
      method: config.method,
      params: config.params,
      data: config.data,
      headers: config.headers
    })
    
    return config
  },
  error => {
    console.error('Request Error:', error)
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axios.interceptors.response.use(
  response => {
    // 添加响应日志
    console.log('Response:', {
      url: response.config.url,
      status: response.status,
      data: response.data
    })
    return response
  },
  error => {
    // 详细的错误处理
    if (error.response) {
      // 服务器返回了错误状态码
      console.error('Response Error:', {
        url: error.config.url,
        status: error.response.status,
        data: error.response.data
      })
      
      switch (error.response.status) {
        case 401:
          // 未授权，清除 token 并跳转到登录页
          localStorage.removeItem('token')
          router.push('/login')
          break
        case 403:
          console.error('权限不足，请检查您的访问权限')
          break
        case 404:
          console.error('请求的资源不存在:', error.config.url)
          break
        case 500:
          console.error('服务器错误，请稍后重试')
          break
        default:
          console.error('请求失败:', error.response.data.message || '未知错误')
      }
    } else if (error.request) {
      // 请求已发出但没有收到响应
      console.error('Network Error:', {
        url: error.config.url,
        message: '网络连接失败，请检查网络设置'
      })
    } else {
      // 请求配置出错
      console.error('Request Config Error:', error.message)
    }
    
    return Promise.reject(error)
  }
)

// 创建 Vue 应用实例
const app = createApp(App)

// 全局注册请求工具
app.config.globalProperties.$fetch = axios

// 添加全局错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('Vue Error:', {
    error: err,
    component: vm,
    info: info,
    stack: err.stack
  })
  
  // 开发环境下显示更详细的错误信息
  if (import.meta.env.DEV) {
    console.error('详细错误堆栈:', err.stack)
  }
}

// 使用路由并挂载应用
app.use(router).mount('#app')