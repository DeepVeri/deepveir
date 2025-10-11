import { getToken, removeToken, isTokenExpired } from './auth'
import router from '../router'

// 创建请求拦截器
const requestInterceptor = async (url, options = {}) => {
  // 检查 token 是否过期
  if (isTokenExpired()) {
    removeToken()
    router.push('/login')
    throw new Error('登录已过期，请重新登录')
  }

  // 添加 token 到请求头
  const token = getToken()
  if (token) {
    options.headers = {
      ...options.headers,
      'Authorization': `Bearer ${token}`
    }
  }

  return { url, options }
}

// 创建响应拦截器
const responseInterceptor = async (response) => {
  if (!response.ok) {
    const data = await response.json()
    
    // 处理 token 过期
    if (response.status === 401 || data.code === 401) {
      removeToken()
      router.push('/login')
      throw new Error('登录已过期，请重新登录')
    }
    
    throw new Error(data.message || '请求失败')
  }
  
  return response
}

// 包装 fetch 请求
export const fetchWithInterceptors = async (url, options = {}) => {
  try {
    // 请求拦截
    const { url: finalUrl, options: finalOptions } = await requestInterceptor(url, options)
    
    // 发送请求
    const response = await fetch(finalUrl, finalOptions)
    
    // 响应拦截
    const interceptedResponse = await responseInterceptor(response)
    
    return interceptedResponse
  } catch (error) {
    console.error('请求错误:', error)
    throw error
  }
}

export default fetchWithInterceptors
