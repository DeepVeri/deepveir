<template>

  
  <div class="profile-page-x">
    <!-- 添加返回按钮 -->
    <div class="back-to-home-x">
      <button @click="goToHome" class="back-btn-x">
        ← 返回首页
      </button>
    </div>
    
    <div class="profile-container-x">
      <!-- 左侧导航 -->
      <div class="profile-sidebar-x">
        <div class="user-info-x">
          <div class="avatar-container-x">
            <img 
              :src="avatarPreview || userInfo.avatar || defaultAvatar" 
              alt="用户头像" 
              class="avatar-x"
            >
            <div class="avatar-upload-x">
              <button 
                @click="openAvatarModal" 
                class="upload-btn-x"
              >
                <span class="icon-x">📷</span>
                更换头像
              </button>
            </div>
          </div>
          <h2 class="username-x">{{ userInfo.username }}</h2>
          <p class="role-x">{{ userInfo.role === 'USER' ? '普通用户' : '管理员' }}</p>
        </div>
        <nav class="profile-nav-x">
          <button 
            v-for="tab in tabs" 
            :key="tab.id"
            :class="['nav-item-x', { active: currentTab === tab.id }]"
            @click="currentTab = tab.id"
          >
            <span class="icon-x">{{ tab.icon }}</span>
            {{ tab.name }}
          </button>
        </nav>
      </div>

      <!-- 右侧内容区 -->
      <div class="profile-content-x">
        <!-- 基本信息 -->
        <div v-if="currentTab === 'basic'" class="tab-content-x">
          <h3 class="section-title-x">基本信息</h3>
          <form @submit.prevent="updateBasicInfo" class="form-x">
            <div class="form-group-x">
              <label>用户名</label>
              <input type="text" v-model="basicInfo.username" disabled>
            </div>
            <div class="form-group-x">
              <label>邮箱</label>
              <input type="email" v-model="basicInfo.email">
            </div>
            <div class="form-group-x">
              <label>个人简介</label>
              <textarea v-model="basicInfo.bio" rows="4" placeholder="介绍一下自己吧..."></textarea>
            </div>
            <button type="submit" class="save-btn-x" :disabled="isSaving">
              {{ isSaving ? '保存中...' : '保存修改' }}
            </button>
          </form>
        </div>

        <!-- 修改密码 -->
        <div v-if="currentTab === 'password'" class="tab-content-x">
          <h3 class="section-title-x">修改密码</h3>
          <form @submit.prevent="updatePassword" class="form-x">
            <div class="form-group-x">
              <label>当前密码</label>
              <input 
                :type="showCurrentPassword ? 'text' : 'password'" 
                v-model="passwordForm.currentPassword"
              >
              <button 
                type="button" 
                class="toggle-password-x"
                @click="showCurrentPassword = !showCurrentPassword"
              >
                {{ showCurrentPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
            <div class="form-group-x">
              <label>新密码</label>
              <input 
                :type="showNewPassword ? 'text' : 'password'" 
                v-model="passwordForm.newPassword"
              >
              <button 
                type="button" 
                class="toggle-password-x"
                @click="showNewPassword = !showNewPassword"
              >
                {{ showNewPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
            <div class="form-group-x">
              <label>确认新密码</label>
              <input 
                :type="showNewPassword ? 'text' : 'password'" 
                v-model="passwordForm.confirmPassword"
              >
            </div>
            <button type="submit" class="save-btn-x" :disabled="isSaving">
              {{ isSaving ? '保存中...' : '修改密码' }}
            </button>
          </form>
        </div>

        <!-- 账号安全 -->
        <div v-if="currentTab === 'security'" class="tab-content-x">
          <h3 class="section-title-x">账号安全</h3>
          <div class="security-items-x">
            <div class="security-item-x">
              <div class="item-info-x">
                <h4>登录密码</h4>
                <p>定期修改密码可以保护账号安全</p>
              </div>
              <button class="change-btn-x" @click="currentTab = 'password'">修改</button>
            </div>
            <div class="security-item-x">
              <div class="item-info-x">
                <h4>绑定手机</h4>
                <p>{{ userInfo.phone || '未绑定手机号' }}</p>
              </div>
              <button class="bind-btn-x" @click="handleBindPhone">
                {{ userInfo.phone ? '修改' : '绑定' }}
              </button>
            </div>
            <div class="security-item-x">
              <div class="item-info-x">
                <h4>绑定邮箱</h4>
                <p>{{ userInfo.email || '未绑定邮箱' }}</p>
              </div>
              <button class="bind-btn-x" @click="handleChangeEmail">修改</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 头像修改弹窗 -->
    <div class="avatar-modal-x" v-if="showAvatarModal">
      <div class="modal-content-x">
        <div class="modal-header-x">
          <h3>修改头像</h3>
          <button class="close-btn-x" @click="closeAvatarModal">×</button>
        </div>
        <div class="modal-body-x">
          <div class="avatar-preview-x">
            <img :src="avatarPreview || userInfo.avatar || defaultAvatar" alt="头像预览">
          </div>
          <div class="avatar-actions-x">
            <input 
              type="file" 
              ref="fileInput" 
              @change="handleFileSelect" 
              accept="image/jpeg,image/png,image/gif" 
              style="display: none"
            >
            <button class="select-btn-x" @click="$refs.fileInput.click()">
              选择图片
            </button>
          </div>
        </div>
        <div class="modal-footer-x">
          <button class="cancel-btn-x" @click="closeAvatarModal">取消</button>
          <button 
            class="confirm-btn-x" 
            @click="confirmAvatarChange"
            :disabled="!avatarPreview || isUploading"
          >
            {{ isUploading ? '上传中...' : '确认' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProfilePage',
  data() {
    return {
      currentTab: 'basic',
      isSaving: false,
      showCurrentPassword: false,
      showNewPassword: false,
      defaultAvatar: 'https://randomuser.me/api/portraits/lego/1.jpg',
      userInfo: JSON.parse(localStorage.getItem('user') || '{}'),
      tabs: [
        { id: 'basic', name: '基本信息', icon: '👤' },
        { id: 'password', name: '修改密码', icon: '🔒' },
        { id: 'security', name: '账号安全', icon: '🛡️' }
      ],
      basicInfo: {
        username: '',
        email: '',
        bio: ''
      },
      passwordForm: {
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      showAvatarModal: false,
      avatarPreview: null,
      selectedFile: null,
      isUploading: false,
      maxFileSize: 10 * 1024 * 1024, // 10MB
      allowedTypes: ['image/jpeg', 'image/png', 'image/gif'],
      maxImageSize: 800 // 最大图片尺寸
    }
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await fetch('http://127.0.0.1:8080/api/user/info', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        const result = await response.json();
        console.log('后端返回：', result);
        if (result.code === 200 && result.data) {
          // 保存旧的手机号状态
          const hadPhone = !!this.userInfo.phone;
          // 更新用户信息
          this.userInfo = result.data;
          this.basicInfo.username = result.data.username || '';
          this.basicInfo.email = result.data.email || '';
          this.basicInfo.bio = result.data.bio || '';
          localStorage.setItem('user', JSON.stringify(result.data));
          // 返回手机号状态变化
          return {
            hadPhone,
            hasPhone: !!result.data.phone
          };
        } else {
          alert(result.message || '获取用户信息失败');
          return null;
        }
      } catch (error) {
        alert('获取用户信息失败，请稍后重试');
        return null;
      }
    },
    openAvatarModal() {
      this.showAvatarModal = true;
      this.avatarPreview = null;
      this.selectedFile = null;
    },
    closeAvatarModal() {
      this.showAvatarModal = false;
      this.avatarPreview = null;
      this.selectedFile = null;
      // 清理预览 URL
      if (this.avatarPreview) {
        URL.revokeObjectURL(this.avatarPreview);
      }
    },
    async handleFileSelect(event) {
      const file = event.target.files[0];
      if (!file) return;

      // 检查文件类型
      if (!this.allowedTypes.includes(file.type)) {
        alert('只支持 JPG、PNG、GIF 格式的图片');
        return;
      }

      // 检查文件大小
      if (file.size > this.maxFileSize) {
        alert('图片大小不能超过 10MB');
        return;
      }

      try {
        // 压缩图片
        const compressedFile = await this.compressImage(file);
        this.selectedFile = compressedFile;
        this.avatarPreview = URL.createObjectURL(compressedFile);
      } catch (error) {
        console.error('图片处理失败:', error);
        alert('图片处理失败，请重试');
      }
    },
    async compressImage(file) {
      return new Promise((resolve) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (event) => {
          const img = new Image();
          img.src = event.target.result;
          img.onload = () => {
            const canvas = document.createElement('canvas');
            let width = img.width;
            let height = img.height;
            
            // 如果图片尺寸超过 800px，等比例缩小
            if (width > height && width > this.maxImageSize) {
              height = Math.round((height * this.maxImageSize) / width);
              width = this.maxImageSize;
            } else if (height > this.maxImageSize) {
              width = Math.round((width * this.maxImageSize) / height);
              height = this.maxImageSize;
            }

            canvas.width = width;
            canvas.height = height;
            const ctx = canvas.getContext('2d');
            ctx.drawImage(img, 0, 0, width, height);
            
            // 转换为 Blob
            canvas.toBlob((blob) => {
              resolve(new File([blob], file.name, {
                type: file.type,
                lastModified: Date.now()
              }));
            }, file.type, 0.8); // 0.8 是压缩质量
          };
        };
      });
    },
    async confirmAvatarChange() {
      if (!this.selectedFile) {
        alert('请先选择图片');
        return;
      }

      console.log('开始上传头像...');
      this.isUploading = true;

      try {
        const formData = new FormData();
        formData.append('avatar', this.selectedFile);

        // 检查 token
        const token = localStorage.getItem('token');
        console.log('当前token:', token); // 添加日志

        if (!token) {
          throw new Error('未登录或token已失效');
        }

        // 添加日志，检查文件信息
        console.log('准备上传的文件:', this.selectedFile);
        console.log('文件类型:', this.selectedFile.type);
        console.log('文件大小:', this.selectedFile.size);

        // 修改请求头，确保正确设置
        const response = await fetch('http://127.0.0.1:8080/api/user/avatar', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}` // 确保这里正确设置了 token
          },
          body: formData
        });

        console.log('请求已发送，等待响应...');
        console.log('服务器响应状态:', response.status);
        const result = await response.json();
        console.log('服务器返回数据:', result);

        if (result.code === 200) {
          // 更新头像 URL
          this.userInfo.avatar = result.data.avatarUrl;
          localStorage.setItem('user', JSON.stringify(this.userInfo));
          alert('头像更新成功');
          this.closeAvatarModal();
        } else {
          throw new Error(result.message || '上传失败');
        }
      } catch (error) {
        console.error('上传头像失败:', error);
        // 更详细的错误提示
        if (error.message) {
          alert(`上传失败: ${error.message}`);
        } else {
          alert('上传失败，请检查网络连接或稍后重试');
        }
      } finally {
        this.isUploading = false;
      }
    },
    async updateBasicInfo() {
      this.isSaving = true
      try {
        const response = await fetch('http://127.0.0.1:8080/api/user/profile', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          },
          body: JSON.stringify(this.basicInfo)
        })

        if (response.ok) {
          alert('保存成功')
        } else {
          const data = await response.json()
          alert(data.message || '保存失败')
        }
      } catch (error) {
        console.error('更新信息失败:', error)
        alert('保存失败，请稍后重试')
      } finally {
        this.isSaving = false
      }
    },
    async updatePassword() {
      if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
        alert('两次输入的新密码不一致')
        return
      }

      this.isSaving = true
      try {
        const response = await fetch('http://127.0.0.1:8080/api/user/password', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          },
          body: JSON.stringify({
            currentPassword: this.passwordForm.currentPassword,
            newPassword: this.passwordForm.newPassword
          })
        })

        const result = await response.json()
        
        if (result.code === 200) {
          alert(result.message || '密码修改成功')
          localStorage.removeItem('token')
          localStorage.removeItem('user')
          this.$router.push('/login')
        } else {
          alert(result.message || '密码修改失败')
        }
      } catch (error) {
        console.error('修改密码失败:', error)
        alert('修改密码失败，请稍后重试')
      } finally {
        this.isSaving = false
      }
    },
    async handleChangeEmail() {
      const newEmail = prompt('请输入新的邮箱地址', this.userInfo.email || '');
      if (!newEmail) return;
      // 简单邮箱格式校验
      if (!/^[\w.-]+@[\w.-]+\.\w+$/.test(newEmail)) {
        alert('邮箱格式不正确');
        return;
      }
      this.isSaving = true;
      try {
        const response = await fetch('http://127.0.0.1:8080/api/user/email', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          },
          body: JSON.stringify({ email: newEmail })
        });
        const result = await response.json();
        if (result.code === 200) {
          alert(result.message || '邮箱修改成功');
          this.userInfo.email = newEmail;
          localStorage.setItem('user', JSON.stringify(this.userInfo));
        } else {
          alert(result.message || '邮箱修改失败');
        }
      } catch (error) {
        alert('修改邮箱失败，请稍后重试');
      } finally {
        this.isSaving = false;
      }
    },
    async handleBindPhone() {
      const newPhone = prompt('请输入要绑定的新手机号', this.userInfo.phone || '');
      if (!newPhone) return;
      // 简单手机号格式校验（中国大陆手机号）
      if (!/^1[3-9]\d{9}$/.test(newPhone)) {
        alert('手机号格式不正确');
        return;
      }
      this.isSaving = true;
      try {
        const response = await fetch('http://127.0.0.1:8080/api/user/phone', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          },
          body: JSON.stringify({ phone: newPhone })
        });
        const result = await response.json();
        if (result.code === 200) {
          // 获取手机号状态变化
          const phoneStatus = await this.fetchUserInfo();
          if (phoneStatus) {
            const message = phoneStatus.hadPhone ? '手机号修改成功' : '手机号绑定成功';
            alert(message);
          }
        } else {
          alert(result.message || '操作失败');
        }
      } catch (error) {
        alert('操作失败，请稍后重试');
      } finally {
        this.isSaving = false;
      }
    },
    // 添加返回首页方法
    goToHome() {
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
.profile-page-x {
  min-height: 100vh;
  background: #f7f9fa;
  padding: 20px;
}

.profile-container-x {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  gap: 24px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  overflow: hidden;
}

.profile-sidebar-x {
  width: 280px;
  background: #f8f9fa;
  padding: 24px;
  border-right: 1px solid #e6ecf0;
}

.user-info-x {
  text-align: center;
  margin-bottom: 24px;
}

.avatar-container-x {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto 16px;
  border-radius: 50%;
  overflow: hidden;
}

.avatar-x {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: filter 0.3s ease;
}

.avatar-container-x:hover .avatar-x {
  filter: brightness(0.8);
}

.upload-btn-x {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  border: none;
  padding: 8px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background 0.3s ease;
  opacity: 0;
}

.avatar-container-x:hover .upload-btn-x {
  opacity: 1;
}

.upload-btn-x:disabled {
  background: rgba(0, 0, 0, 0.4);
  cursor: not-allowed;
}

.username-x {
  font-size: 1.4rem;
  font-weight: 700;
  color: #14171a;
  margin-bottom: 4px;
}

.role-x {
  color: #657786;
  font-size: 0.9rem;
}

.profile-nav-x {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.nav-item-x {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  border: none;
  background: none;
  border-radius: 8px;
  color: #657786;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}

.nav-item-x:hover {
  background: #e8f5fd;
  color: #1da1f2;
}

.nav-item-x.active {
  background: #e8f5fd;
  color: #1da1f2;
  font-weight: 600;
}

.profile-content-x {
  flex: 1;
  padding: 32px;
}

.section-title-x {
  font-size: 1.4rem;
  font-weight: 700;
  color: #14171a;
  margin-bottom: 24px;
}

.form-x {
  max-width: 500px;
}

.form-group-x {
  margin-bottom: 20px;
}

.form-group-x label {
  display: block;
  margin-bottom: 8px;
  color: #14171a;
  font-weight: 600;
}

.form-group-x input,
.form-group-x textarea {
  width: 100%;
  padding: 12px;
  border: 1.5px solid #e6ecf0;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s;
}

.form-group-x input:focus,
.form-group-x textarea:focus {
  border-color: #1da1f2;
  outline: none;
}

.form-group-x input:disabled {
  background: #f7f9fa;
  cursor: not-allowed;
}

.toggle-password-x {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #657786;
  cursor: pointer;
  font-size: 1.1rem;
}

.save-btn-x {
  background: #1da1f2;
  color: #fff;
  border: none;
  border-radius: 999px;
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s;
}

.save-btn-x:hover {
  background: #0a7bbf;
}

.save-btn-x:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.security-items-x {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.security-item-x {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: #f7f9fa;
  border-radius: 8px;
}

.item-info-x {
  flex: 1;
}

.item-info-x h4 {
  font-size: 1.1rem;
  color: #14171a;
  margin-bottom: 4px;
}

.item-info-x p {
  color: #657786;
  font-size: 0.9rem;
}

.change-btn-x,
.bind-btn-x {
  background: none;
  border: 1.5px solid #1da1f2;
  color: #1da1f2;
  padding: 8px 16px;
  border-radius: 20px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s;
}

.change-btn-x:hover,
.bind-btn-x:hover {
  background: #e8f5fd;
}

@media (max-width: 768px) {
  .profile-container-x {
    flex-direction: column;
  }

  .profile-sidebar-x {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid #e6ecf0;
  }

  .profile-content-x {
    padding: 20px;
  }
}

/* 添加返回按钮样式 */
.back-to-home-x {
  max-width: 1200px;
  margin: 0 auto 20px;
  padding: 0 20px;
}

.back-btn-x {
  background: none;
  border: none;
  color: #1da1f2;
  font-size: 1rem;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 20px;
  transition: background 0.2s;
}

.back-btn-x:hover {
  background: #e8f5fd;
}

/* 头像修改弹窗样式 */
.avatar-modal-x {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content-x {
  background: #fff;
  border-radius: 12px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.modal-header-x {
  padding: 16px 24px;
  border-bottom: 1px solid #e6ecf0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header-x h3 {
  margin: 0;
  font-size: 1.2rem;
  color: #14171a;
}

.close-btn-x {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #657786;
  cursor: pointer;
  padding: 4px;
}

.modal-body-x {
  padding: 24px;
  text-align: center;
}

.avatar-preview-x {
  width: 200px;
  height: 200px;
  margin: 0 auto 20px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #e6ecf0;
}

.avatar-preview-x img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-actions-x {
  margin-bottom: 20px;
}

.select-btn-x {
  background: #1da1f2;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 8px 24px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
}

.select-btn-x:hover {
  background: #0a7bbf;
}

.modal-footer-x {
  padding: 16px 24px;
  border-top: 1px solid #e6ecf0;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

.cancel-btn-x {
  background: none;
  border: 1px solid #e6ecf0;
  color: #14171a;
  padding: 8px 24px;
  border-radius: 20px;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.2s;
}

.cancel-btn-x:hover {
  background: #f7f9fa;
}

.confirm-btn-x {
  background: #1da1f2;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 8px 24px;
  font-size: 1rem;
  cursor: pointer;
  transition: background 0.2s;
}

.confirm-btn-x:hover {
  background: #0a7bbf;
}

.confirm-btn-x:disabled {
  background: #a8d4f2;
  cursor: not-allowed;
}
</style> 