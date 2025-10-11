<template>
  <div class="ai-login-bg">
    <!-- 背景动画 -->
    <div class="ai-bg-animation">
      <div class="ai-bg-circle"></div>
      <div class="ai-bg-circle"></div>
      <div class="ai-bg-circle"></div>
    </div>
    
    <!-- AI插画 -->
    <div class="ai-illustration">
      <svg width="200" height="200" viewBox="0 0 200 200">
        <defs>
          <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#00ffe7;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#1da1f2;stop-opacity:1" />
          </linearGradient>
        </defs>
        <circle cx="100" cy="100" r="80" fill="none" stroke="url(#grad1)" stroke-width="2">
          <animate attributeName="r" values="80;85;80" dur="3s" repeatCount="indefinite" />
        </circle>
        <path d="M100,40 Q140,100 100,160 Q60,100 100,40" fill="none" stroke="url(#grad1)" stroke-width="2">
          <animate attributeName="d" values="M100,40 Q140,100 100,160 Q60,100 100,40;M100,35 Q145,100 100,165 Q55,100 100,35;M100,40 Q140,100 100,160 Q60,100 100,40" dur="4s" repeatCount="indefinite" />
        </path>
      </svg>
    </div>

    <div class="ai-login-card">
      <div class="ai-logo">
        <h2>Deep Echo</h2>
        <p class="ai-slogan">AI智能 · 科技社交</p>
      </div>
      <div class="ai-tabs">
        <button 
          :class="['ai-tab-btn', { active: activeTab === 'login' }]"
          @click="activeTab = 'login'"
        >登录</button>
        <button 
          :class="['ai-tab-btn', { active: activeTab === 'register' }]"
          @click="activeTab = 'register'"
        >注册</button>
      </div>
      <form v-if="activeTab === 'login'" @submit.prevent="handleLogin" class="ai-form">
        <div class="ai-form-group">
          <input 
            type="text" 
            v-model="loginForm.username" 
            placeholder="用户名"
            class="ai-input"
            required
          />
        </div>
        <div class="ai-form-group">
          <input 
            :type="showPassword ? 'text' : 'password'" 
            v-model="loginForm.password" 
            placeholder="密码"
            class="ai-input"
            required
          />
          <span class="ai-toggle-pwd" @click="showPassword = !showPassword" title="显示/隐藏密码">
            <svg v-if="showPassword" width="22" height="22" viewBox="0 0 24 24" fill="none"><circle cx="12" cy="12" r="3" stroke="#00ffe7" stroke-width="2"/><path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z" stroke="#1da1f2" stroke-width="2"/></svg>
            <svg v-else width="22" height="22" viewBox="0 0 24 24" fill="none"><circle cx="12" cy="12" r="3" stroke="#1da1f2" stroke-width="2"/><path d="M17.94 17.94A10.97 10.97 0 0 1 12 19c-7 0-11-7-11-7a21.77 21.77 0 0 1 5.06-6.06M1 1l22 22" stroke="#00ffe7" stroke-width="2"/></svg>
          </span>
        </div>
        <button type="submit" class="ai-btn" :class="{ 'loading-x': isLoading }">
          {{ isLoading ? '登录中...' : '登录' }}
        </button>
      </form>
      <form v-else @submit.prevent="handleRegister" class="ai-form">
        <div class="ai-form-group">
          <input 
            type="text" 
            v-model="registerForm.username" 
            placeholder="用户名"
            class="ai-input"
            required
          />
        </div>
        <div class="ai-form-group">
          <input 
            type="email" 
            v-model="registerForm.email" 
            placeholder="邮箱"
            class="ai-input"
            required
          />
        </div>
        <div class="ai-form-group">
          <input 
            :type="showRegisterPassword ? 'text' : 'password'" 
            v-model="registerForm.password" 
            placeholder="密码"
            class="ai-input"
            required
          />
        </div>
        <div class="ai-form-group">
          <input 
            :type="showRegisterPassword ? 'text' : 'password'" 
            v-model="registerForm.confirmPassword" 
            placeholder="确认密码"
            class="ai-input"
            required
          />
          <span class="ai-toggle-pwd" @click="showRegisterPassword = !showRegisterPassword" title="显示/隐藏密码">
            <svg v-if="showRegisterPassword" width="22" height="22" viewBox="0 0 24 24" fill="none"><circle cx="12" cy="12" r="3" stroke="#00ffe7" stroke-width="2"/><path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z" stroke="#1da1f2" stroke-width="2"/></svg>
            <svg v-else width="22" height="22" viewBox="0 0 24 24" fill="none"><circle cx="12" cy="12" r="3" stroke="#1da1f2" stroke-width="2"/><path d="M17.94 17.94A10.97 10.97 0 0 1 12 19c-7 0-11-7-11-7a21.77 21.77 0 0 1 5.06-6.06M1 1l22 22" stroke="#00ffe7" stroke-width="2"/></svg>
          </span>
        </div>
        <button type="submit" class="ai-btn" :class="{ 'loading-x': isRegistering }">
          {{ isRegistering ? '注册中...' : '注册' }}
        </button>
      </form>
      <div class="ai-divider"><span>或</span></div>
      <div class="ai-social-login">
        <button class="ai-social-btn wechat-x"><span class="ai-social-icon">💬</span> 微信登录</button>
        <button class="ai-social-btn weibo-x"><span class="ai-social-icon">🌐</span> 微博登录</button>
      </div>
      <div class="ai-back-home">
        <button @click="goToHome" class="ai-back-btn">← 返回首页</button>
      </div>
      <div class="ai-sidebar-user" v-if="currentUser">
        <img :src="currentUser.avatar" :alt="currentUser.username" class="ai-avatar" />
        <div class="ai-user-info">
          <span class="ai-username">{{ currentUser.username }}</span>
          <span class="ai-user-email" v-if="currentUser.email">{{ currentUser.email }}</span>
          <button @click="handleLogout" class="ai-logout-btn">退出</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data() {
    return {
      activeTab: 'login',
      showPassword: false,
      showRegisterPassword: false,
      isLoading: false,
      isRegistering: false,
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
      },
      currentUser: null
    }
  },
  created() {
    // 检查 token 是否有效，无效则清除本地用户信息
    const token = localStorage.getItem('token');
    const expireTime = localStorage.getItem('tokenExpireTime');
    const now = Math.floor(Date.now() / 1000);
    if (!token || (expireTime && now > parseInt(expireTime))) {
      localStorage.removeItem('token');
      localStorage.removeItem('tokenExpireTime');
      localStorage.removeItem('user');
      this.currentUser = null;
    } else {
      // token 有效才显示用户信息
      const user = localStorage.getItem('user');
      if (user) {
        try {
          this.currentUser = JSON.parse(user);
        } catch (error) {
          this.currentUser = null;
        }
      }
    }
  },
  methods: {
    async handleLogin() {
      if (!this.loginForm.username || !this.loginForm.password) {
        alert('请输入用户名和密码');
        return;
      }

      this.isLoading = true;
      try {
        const response = await fetch('http://127.0.0.1:8080/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.loginForm.username,
            password: this.loginForm.password
          })
        });

        const result = await response.json();
        // 只有登录成功才跳转
        if (result.code === 200 && result.data) {
          // 存储token和用户信息
          localStorage.setItem('token', result.data.token);
          // 将毫秒级时间戳转换为秒级存储
          localStorage.setItem('tokenExpireTime', Math.floor(result.data.expireTime / 1000));
          localStorage.setItem('user', JSON.stringify(result.data));
          // 跳转到首页
          this.$router.push('/');
          // 如需刷新可加 window.location.reload();
        } else {
          // 登录失败，提示错误，不跳转
          alert(result.message || '登录失败');
        }
      } catch (error) {
        console.error('登录错误:', error);
        alert('网络错误，请稍后重试');
      } finally {
        this.isLoading = false;
      }
    },
    async handleRegister() {
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        alert('两次输入的密码不一致')
        return
      }

      this.isRegistering = true
      try {
        const response = await fetch('http://127.0.0.1:8080/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.registerForm.username,
            password: this.registerForm.password,
            email: this.registerForm.email
          })
        })

        const data = await response.json()
        
        if (response.ok) {
          alert('注册成功！')
          // 注册成功后自动切换到登录标签页
          this.activeTab = 'login'
          // 清空注册表单
          this.registerForm = {
            username: '',
            email: '',
            password: '',
            confirmPassword: ''
          }
        } else {
          alert(data.message || '注册失败，请稍后重试')
        }
      } catch (error) {
        console.error('注册失败:', error)
        alert('注册失败，请检查网络连接')
      } finally {
        this.isRegistering = false
      }
    },
    goToHome() {
      this.$router.push('/')
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('tokenExpireTime');
      localStorage.removeItem('user');
      this.currentUser = null;
      this.$router.push('/login');
    },
    goToLogin() {
      this.activeTab = 'login';
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Exo+2:wght@500;700&display=swap');
.ai-login-bg {
  min-height: 100vh;
  background: #0a0e17;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'Exo 2', 'PingFang SC', Arial, sans-serif;
  position: relative;
  overflow: hidden;
}

.ai-bg-animation {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 1;
}

.ai-bg-circle {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(45deg, #00ffe733, #1da1f233);
  filter: blur(60px);
  animation: float 8s infinite ease-in-out;
}

.ai-bg-circle:nth-child(1) {
  width: 300px;
  height: 300px;
  top: -100px;
  left: -100px;
  animation-delay: 0s;
}

.ai-bg-circle:nth-child(2) {
  width: 400px;
  height: 400px;
  top: 50%;
  right: -200px;
  animation-delay: -2s;
}

.ai-bg-circle:nth-child(3) {
  width: 200px;
  height: 200px;
  bottom: -50px;
  left: 50%;
  animation-delay: -4s;
}

@keyframes float {
  0%, 100% {
    transform: translate(0, 0);
  }
  25% {
    transform: translate(50px, 50px);
  }
  50% {
    transform: translate(0, 100px);
  }
  75% {
    transform: translate(-50px, 50px);
  }
}

.ai-illustration {
  position: absolute;
  top: 60px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  filter: drop-shadow(0 0 16px #1da1f2aa);
  animation: pulse 2.5s infinite alternate;
}

@keyframes pulse {
  0% { filter: drop-shadow(0 0 8px #1da1f2aa); }
  100% { filter: drop-shadow(0 0 24px #00ffe7cc); }
}

.ai-login-card {
  background: rgba(16, 20, 30, 0.95);
  border-radius: 22px;
  box-shadow: 0 8px 40px 0 #00ffe733, 0 1.5px 8px #1da1f2cc;
  padding: 60px 38px 38px 38px;
  width: 100%;
  max-width: 410px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  margin: 120px 0 24px 0;
  z-index: 3;
  backdrop-filter: blur(10px);
}

.ai-logo {
  text-align: center;
  margin-bottom: 32px;
}

.ai-logo h2 {
  color: #00ffe7;
  font-size: 2.3rem;
  font-weight: 900;
  margin-bottom: 16px;
  letter-spacing: 2px;
  text-shadow: 0 2px 16px #1da1f2cc;
  animation: glow 2s ease-in-out infinite alternate;
}

@keyframes glow {
  from {
    text-shadow: 0 2px 16px #1da1f2cc;
  }
  to {
    text-shadow: 0 2px 24px #00ffe7cc;
  }
}

.ai-slogan {
  color: #b0eaff;
  font-size: 1.08rem;
  margin-bottom: 0;
  letter-spacing: 1px;
}

.ai-tabs {
  display: flex;
  width: 100%;
  margin-bottom: 32px;
  border-bottom: 1.5px solid #232a36;
}
.ai-tab-btn {
  flex: 1;
  background: none;
  border: none;
  font-size: 1.18rem;
  font-weight: 700;
  color: #b0eaff;
  padding: 16px 0 12px 0;
  cursor: pointer;
  border-bottom: 2.5px solid transparent;
  border-radius: 0;
  transition: color 0.2s, border-color 0.2s;
  font-family: inherit;
}
.ai-tab-btn.active {
  color: #00ffe7;
  border-bottom: 2.5px solid #1da1f2;
  background: #1a2330;
}
.ai-form-group {
  margin-bottom: 22px;
  position: relative;
}
.ai-input {
  width: 100%;
  padding: 15px 18px;
  border-radius: 12px;
  border: none;
  background: rgba(35, 42, 54, 0.8);
  font-size: 1.13rem;
  color: #eafaff;
  outline: none;
  box-shadow: 0 0 0 1.5px rgba(35, 42, 54, 0.8);
  transition: all 0.3s ease;
}
.ai-input:focus {
  background: rgba(35, 42, 54, 0.9);
  box-shadow: 0 0 0 2.5px #00ffe7cc, 0 0 8px #1da1f2cc;
}
.ai-input::placeholder {
  color: #7fdfff;
  opacity: 0.8;
  font-size: 1.08rem;
  letter-spacing: 1px;
}
.ai-toggle-pwd {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.3rem;
  color: #00ffe7;
  cursor: pointer;
  user-select: none;
  transition: color 0.2s, filter 0.2s;
  filter: drop-shadow(0 0 4px #1da1f2cc);
}
.ai-toggle-pwd:hover {
  color: #1da1f2;
  filter: drop-shadow(0 0 8px #00ffe7cc);
}
.ai-btn {
  width: 100%;
  background: linear-gradient(90deg, #1da1f2 0%, #00ffe7 100%);
  color: #fff;
  border: none;
  border-radius: 12px;
  padding: 15px 0;
  font-size: 1.18rem;
  font-weight: 800;
  margin-top: 10px;
  margin-bottom: 4px;
  transition: all 0.3s ease;
  box-shadow: 0 2px 12px #00ffe733;
  position: relative;
  overflow: hidden;
}
.ai-btn::before {
  content: '';
  position: absolute;
  left: -60%;
  top: 0;
  width: 40%;
  height: 100%;
  background: linear-gradient(120deg, #fff 0%, #00ffe7 100%);
  opacity: 0.18;
  transform: skewX(-20deg);
  transition: left 0.4s;
}
.ai-btn:hover::before {
  left: 110%;
}
.ai-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 24px #00ffe7aa;
}
.ai-btn:active {
  transform: translateY(1px);
}
.loading-x {
  opacity: 0.7;
  pointer-events: none;
}
.ai-divider {
  width: 100%;
  text-align: center;
  margin: 22px 0 12px 0;
  color: #3a4a5a;
  font-size: 1.02rem;
  position: relative;
}
.ai-divider span {
  background: #181c24;
  padding: 0 14px;
  position: relative;
  z-index: 1;
}
.ai-divider:before {
  content: '';
  display: block;
  position: absolute;
  left: 0; right: 0; top: 50%;
  height: 1px;
  background: #232a36;
  z-index: 0;
}
.ai-social-login {
  display: flex;
  gap: 16px;
  width: 100%;
  justify-content: center;
  margin-bottom: 12px;
}
.ai-social-btn {
  flex: 1;
  background: #232a36;
  color: #00ffe7;
  border: none;
  border-radius: 8px;
  padding: 12px 0;
  font-size: 1.08rem;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: background 0.2s, color 0.2s, box-shadow 0.2s;
  cursor: pointer;
  box-shadow: 0 1px 8px #1da1f233;
  position: relative;
  overflow: hidden;
}
.ai-social-btn:hover {
  background: #1da1f2;
  color: #fff;
  box-shadow: 0 2px 16px #00ffe7aa;
}
.ai-social-icon {
  font-size: 1.3em;
  filter: drop-shadow(0 0 4px #00ffe7cc);
}
.ai-back-home {
  margin-top: 12px;
  width: 100%;
  text-align: center;
}
.ai-back-btn {
  background: none;
  border: none;
  color: #00ffe7;
  font-size: 1.08rem;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 20px;
  transition: background 0.2s, color 0.2s;
}
.ai-back-btn:hover {
  background: #1da1f2;
  color: #fff;
}
.ai-sidebar-user {
  margin-top: 18px;
  width: 100%;
  text-align: center;
}
.ai-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  margin-right: 8px;
  border: 2px solid #00ffe7;
  box-shadow: 0 0 8px #1da1f2cc;
}
.ai-user-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.ai-username {
  font-size: 1.12rem;
  font-weight: 700;
  margin-top: 4px;
  color: #00ffe7;
  text-shadow: 0 1px 8px #1da1f2cc;
}
.ai-user-email {
  font-size: 1rem;
  color: #b0eaff;
}
.ai-logout-btn {
  background: none;
  border: none;
  color: #00ffe7;
  font-size: 1.08rem;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 20px;
  transition: background 0.2s, color 0.2s;
  margin-top: 6px;
}
.ai-logout-btn:hover {
  background: #1da1f2;
  color: #fff;
}
@media (max-width: 500px) {
  .ai-login-card {
    padding: 24px 8px 18px 8px;
    max-width: 98vw;
    margin: 80px 0 24px 0;
  }
  
  .ai-illustration {
    display: none;
  }
  
  .ai-logo h2 {
    font-size: 2rem;
  }
  
  .ai-slogan {
    font-size: 1rem;
  }
}
</style> 