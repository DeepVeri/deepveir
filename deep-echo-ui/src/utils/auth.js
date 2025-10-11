// Token 相关的操作
const TOKEN_KEY = 'token'
const TOKEN_EXPIRE_KEY = 'tokenExpireTime'
const USER_KEY = 'user'

export const getToken = () => {
  return localStorage.getItem(TOKEN_KEY)
}

export const setToken = (token, expireTime) => {
  localStorage.setItem(TOKEN_KEY, token)
  // 将毫秒级时间戳转换为秒级存储
  localStorage.setItem(TOKEN_EXPIRE_KEY, Math.floor(expireTime / 1000))
}

export const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(TOKEN_EXPIRE_KEY)
  localStorage.removeItem(USER_KEY)
}

export const isTokenExpired = () => {
  const expireTime = localStorage.getItem(TOKEN_EXPIRE_KEY)
  if (!expireTime) return true
  return Math.floor(Date.now() / 1000) > parseInt(expireTime)
}

export const getUser = () => {
  const user = localStorage.getItem(USER_KEY)
  return user ? JSON.parse(user) : null
}

export const setUser = (user) => {
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}
