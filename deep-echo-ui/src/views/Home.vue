<template>
  <div class="home-x-layout">
    <!-- 左侧导航栏 -->
    <aside class="sidebar-x">
      <div class="logo-x">
        <svg class="logo-icon" width="40" height="40" viewBox="0 0 40 40" fill="none">
          <circle cx="20" cy="20" r="16" stroke="#00ffe7" stroke-width="4" fill="none"/>
          <text x="20" y="27" text-anchor="middle" font-size="22" fill="#00ffe7" font-family="Exo 2, Arial" font-weight="bold" style="filter: drop-shadow(0 0 8px #00ffe7);">D</text>
        </svg>
      </div>
      <nav class="nav-x">
        <ul>
          <li :class="{active: navActive==='home'}" @click="navActive='home'">
            <span class="nav-svg">
              <!-- 首页图标 -->
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path d="M3 10.5L12 4L21 10.5V20A1 1 0 0 1 20 21H4A1 1 0 0 1 3 20V10.5Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </span>
            <span class="nav-label">首页</span>
          </li>
          <li :class="{active: navActive==='explore'}" @click="navActive='explore'">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <circle cx="11" cy="11" r="7" stroke="currentColor" stroke-width="2"/>
                <line x1="16.6569" y1="16.6569" x2="21" y2="21" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </span>
            <span class="nav-label">探索</span>
          </li>
          <li :class="{active: navActive==='notifications'}" @click="navActive='notifications'">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path d="M18 16V11C18 7.68629 15.3137 5 12 5C8.68629 5 6 7.68629 6 11V16L4 18V19H20V18L18 16Z" stroke="currentColor" stroke-width="2"/>
                <circle cx="12" cy="21" r="1" fill="currentColor"/>
              </svg>
            </span>
            <span class="nav-label">通知</span>
          </li>
          <li :class="{active: navActive==='messages'}" @click="navActive='messages'">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <rect x="3" y="7" width="18" height="10" rx="2" stroke="currentColor" stroke-width="2"/>
                <path d="M3 7L12 13L21 7" stroke="currentColor" stroke-width="2"/>
              </svg>
            </span>
            <span class="nav-label">私信</span>
          </li>
          <li :class="{active: navActive==='deepecho'}" @click="navActive='deepecho'">
            <span class="nav-svg">
              <!-- Deep Echo图标，这里用D字母LOGO示例 -->
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="2"/>
                <text x="12" y="17" text-anchor="middle" font-size="12" fill="currentColor" font-family="Exo 2, Arial" font-weight="bold">D</text>
              </svg>
            </span>
            <span class="nav-label">Deep Echo</span>
          </li>
          <li :class="{active: navActive==='groups'}" @click="navActive='groups'">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <circle cx="7" cy="10" r="3" stroke="currentColor" stroke-width="2"/>
                <circle cx="17" cy="10" r="3" stroke="currentColor" stroke-width="2"/>
                <path d="M2 20C2 16.6863 7.58172 14 12 14C16.4183 14 22 16.6863 22 20" stroke="currentColor" stroke-width="2"/>
              </svg>
            </span>
            <span class="nav-label">社群</span>
          </li>
          <li :class="{active: navActive==='premium'}" @click="navActive='premium'">
            <span class="nav-svg">
              <!-- Premium图标（可自定义） -->
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path d="M4 20L20 4M4 4L20 20" stroke="currentColor" stroke-width="2"/>
              </svg>
            </span>
            <span class="nav-label">Premium</span>
          </li>
          <li :class="{active: navActive==='profile'}" @click="goToProfile">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <circle cx="12" cy="8" r="4" stroke="currentColor" stroke-width="2"/>
                <path d="M4 20C4 16.6863 7.58172 14 12 14C16.4183 14 20 16.6863 20 20" stroke="currentColor" stroke-width="2"/>
              </svg>
            </span>
            <span class="nav-label">个人资料</span>
          </li>
          <li :class="{active: navActive==='more'}" @click="navActive='more'">
            <span class="nav-svg">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <circle cx="5" cy="12" r="2" fill="currentColor"/>
                <circle cx="12" cy="12" r="2" fill="currentColor"/>
                <circle cx="19" cy="12" r="2" fill="currentColor"/>
              </svg>
            </span>
            <span class="nav-label">更多</span>
          </li>
        </ul>
      </nav>
      <div class="sidebar-user-x" v-if="currentUser">
        <div class="user-card-x">
          <img :src="currentUser.avatar" :alt="currentUser.username" class="user-avatar-x" />
          <div class="user-meta-x">
            <span class="user-nickname-x" :title="currentUser.nickname || currentUser.username">
              {{ currentUser.nickname || currentUser.username }}
            </span>
            <span class="user-id-x" :title="currentUser.username">
              @{{ currentUser.username }}
            </span>
          </div>
          <div class="user-more-x" @click.stop="toggleUserMenu">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <circle cx="5" cy="12" r="2" fill="#00ffe7"/>
              <circle cx="12" cy="12" r="2" fill="#00ffe7"/>
              <circle cx="19" cy="12" r="2" fill="#00ffe7"/>
            </svg>
            <div
              v-if="showUserMenu"
              class="user-menu-x"
              @click.stop
            >
              <div class="user-menu-item-x" @click="handleLogout">退出</div>
            </div>
          </div>
        </div>
      </div>
      <div class="sidebar-user-x" v-else>
        <button @click="goToLogin" class="login-btn-x">登录</button>
      </div>
    </aside>

    <!-- 中间内容流 -->
    <main class="main-content-x">
      <div class="main-tabs-x">
        <button
          :class="{active: mainTab==='recommend'}"
          @click="mainTab='recommend'"
        >为你推荐</button>
        <button
          :class="{active: mainTab==='follow'}"
          @click="mainTab='follow'"
        >关注</button>
      </div>

      <!-- 登录发布框（已登录/未登录） -->
      <div class="post-form-x" v-if="currentUser">
        <textarea 
          v-model="newPost" 
          placeholder="分享你的想法... 支持@和#话题"
          @input="autoResize"
        ></textarea>
        <div class="post-form-tools-x">
          <!-- 图片 -->
          <button class="tool-btn-x" title="图片">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <rect x="3" y="5" width="18" height="14" rx="3" stroke="#1da1f2" stroke-width="2"/>
              <circle cx="8" cy="10" r="2" stroke="#1da1f2" stroke-width="2"/>
              <path d="M21 19l-5-6-4 5-3-4-4 5" stroke="#1da1f2" stroke-width="2"/>
            </svg>
          </button>
          <!-- 动态图片（GIF） -->
          <button class="tool-btn-x" title="GIF">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <rect x="3" y="5" width="18" height="14" rx="3" stroke="#1da1f2" stroke-width="2"/>
              <text x="7" y="17" font-size="8" fill="#1da1f2" font-family="Arial" font-weight="bold">GIF</text>
            </svg>
          </button>
          <!-- 视频 -->
          <button class="tool-btn-x" title="视频">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <rect x="3" y="5" width="18" height="14" rx="3" stroke="#1da1f2" stroke-width="2"/>
              <polygon points="10,9 16,12 10,15" fill="#1da1f2"/>
            </svg>
          </button>
          <!-- 表情 -->
          <button class="tool-btn-x" title="表情">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
              <circle cx="12" cy="12" r="9" stroke="#1da1f2" stroke-width="2"/>
              <circle cx="9" cy="10" r="1" fill="#1da1f2"/>
              <circle cx="15" cy="10" r="1" fill="#1da1f2"/>
              <path d="M9 15c1.5 1 3.5 1 5 0" stroke="#1da1f2" stroke-width="2" stroke-linecap="round"/>
            </svg>
          </button>
        </div>
        <button @click="handlePost" class="post-btn-x">
          <span class="btn-icon">🚀</span>
          <span>发布</span>
        </button>
      </div>
      <div class="post-form-x not-logged-in" v-else>
        <textarea 
          placeholder="请登录后发布内容…"
          disabled
        ></textarea>
        <button @click="goToLogin" class="post-btn-x">登录发布</button>
        <div class="login-tip-x">
          <svg width="20" height="20" style="vertical-align: middle; margin-right: 4px;" viewBox="0 0 24 24" fill="none">
            <circle cx="12" cy="12" r="10" stroke="#ff7675" stroke-width="2"/>
            <path d="M12 8v4" stroke="#ff7675" stroke-width="2" stroke-linecap="round"/>
            <circle cx="12" cy="16" r="1" fill="#ff7675"/>
          </svg>
          登录后即可发布你的AI想法！
        </div>
      </div>

      <div v-if="mainTab==='recommend'">
        <!-- 推荐内容流 -->
        <div class="posts-x">
          <div v-if="loading && currentPage === 1" class="loading-x">
            <div class="loading-spinner"></div>
            <span>加载中...</span>
          </div>
          
          <div v-else>
            <div v-for="post in posts" :key="post.id" class="post-x">
              <div class="post-header-x">
                <img :src="post.avatar" :alt="post.username" class="avatar-x" />
                <div class="post-info-x">
                  <span class="username-x" :title="post.nickname || post.username">
                    {{ post.nickname || post.username }}
                  </span>
                  <span class="user-id-x" :title="post.username">
                    @{{ post.username }}
                  </span>
                  <span class="time-x">
                    {{ formatSmartTime(post.createdAt) }}
                  </span>
                </div>
                <button class="more-btn-x" @click="post.showMore = !post.showMore">•••</button>
                <ul v-if="post.showMore" class="more-menu-x">
                  <li v-if="currentUser && post.userId === currentUser.id" @click="deletePost(post)">删除</li>
                  <li @click="reportPost(post)">举报</li>
                </ul>
              </div>
              
              <p class="post-content-x" v-html="highlightContent(post.content)"></p>
              
              <div v-if="post.imageUrl || post.image_url" class="post-img-x">
                <img :src="post.imageUrl || post.image_url" alt="动态图片" @click="previewImage(post.imageUrl || post.image_url)" />
              </div>
              
              <div class="post-actions-x">
                <button @click="handleComment(post)" class="action-btn-x">
                  <span class="action-icon">
                    <!-- 评论icon -->
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                      <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z" stroke="currentColor" stroke-width="2" fill="none"/>
                    </svg>
                  </span>
                  <span>{{ post.commentCount }}</span>
                </button>
                <button @click="handleShare(post)" class="action-btn-x">
                  <span class="action-icon">
                    <!-- 转发icon -->
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                      <path d="M17 1l4 4-4 4" stroke="currentColor" stroke-width="2" fill="none"/>
                      <path d="M21 5H7a4 4 0 0 0 0 8h1" stroke="currentColor" stroke-width="2" fill="none"/>
                    </svg>
                  </span>
                  <span>{{ post.shareCount }}</span>
                </button>
                <button @click="handleLike(post)" :class="['action-btn-x', { 'liked-x': post.isLiked }]">
                  <span class="action-icon">
                    <!-- 点赞icon -->
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                      <path d="M12 21C12 21 4 13.5 4 8.5A4.5 4.5 0 0 1 8.5 4A4.5 4.5 0 0 1 12 7.5A4.5 4.5 0 0 1 15.5 4A4.5 4.5 0 0 1 20 8.5C20 13.5 12 21 12 21Z" stroke="currentColor" stroke-width="2" fill="none"/>
                    </svg>
                  </span>
                  <span>{{ post.likeCount }}</span>
                </button>
                <button @click="handleHot(post)" class="action-btn-x">
                  <span class="action-icon">
                    <!-- 统计/热度icon -->
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
                      <rect x="3" y="12" width="3" height="6" rx="1.5" fill="none" stroke="currentColor" stroke-width="2"/>
                      <rect x="9" y="8" width="3" height="10" rx="1.5" fill="none" stroke="currentColor" stroke-width="2"/>
                      <rect x="15" y="4" width="3" height="14" rx="1.5" fill="none" stroke="currentColor" stroke-width="2"/>
                    </svg>
                  </span>
                  <span>{{ post.hotCount || 0 }}</span>
                </button>
              </div>
            </div>
            
            <!-- 加载更多按钮 -->
            <div v-if="posts.length < totalPosts" class="load-more-x">
              <button @click="loadMore" :disabled="loading">
                {{ loading ? '加载中...' : '加载更多' }}
              </button>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <!-- 关注内容流 -->
        <div class="posts-x">
          <!-- 关注内容的循环渲染 -->
        </div>
      </div>

      <div v-if="error" class="error-message">
        <div class="error-icon">⚠️</div>
        <div class="error-text">{{ error }}</div>
        <button @click="retryLoad" class="retry-button">
          <span class="retry-icon">🔄</span>
          重试
        </button>
      </div>
    </main>

    <!-- 右侧边栏 -->
    <aside class="rightbar-x">
      <!-- 搜索框 -->
      <div class="search-x search-right-x">
        <input type="text" placeholder="搜索" v-model="searchText" />
      </div>
      <!-- 热门话题 -->
      <div class="trending-x">
        <h3>热门话题</h3>
        <a
          href="https://top.baidu.com/board?tab=realtime"
          target="_blank"
          rel="noopener noreferrer"
          class="baidu-hot-link"
        >
          <span class="topic-title-x">百度热搜榜（点击查看实时热搜）</span>
        </a>
      </div>
      <div class="who-x">
        <h3>推荐关注</h3>
        <div v-for="user in whoToFollow" :key="user.username" class="who-user-x">
          <img :src="user.avatar" :alt="user.username" class="avatar-x" />
          <div class="who-info-x">
            <span class="username-x">{{ user.username }}</span>
            <button class="follow-btn-x" @click="toggleFollow(user)">
              {{ user.followed ? '已关注' : '关注' }}
            </button>
          </div>
        </div>
      </div>
      <div class="discover-x">
        <h3>发现新鲜事</h3>
        <ul>
          <li>🎉 Deep Echo 新增图片动态功能！</li>
          <li>🔥 关注你感兴趣的话题，发现更多精彩内容！</li>
        </ul>
      </div>
    </aside>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Home',
  data() {
    return {
      navActive: 'home',
      mainTab: 'recommend',
      currentUser: null,
      newPost: '',
      newPostImage: null,
      searchText: '',
      posts: [],
      totalPosts: 0,
      currentPage: 1,
      pageSize: 10,
      loading: false,
      error: null,
      trendingTopics: [
        { title: '人工智能', count: '10.5万' },
        { title: '电影推荐', count: '5.2万' },
        { title: '世界杯', count: '8.7万' }
      ],
      whoToFollow: [
        { username: '推荐用户1', avatar: 'https://via.placeholder.com/40', followed: false },
        { username: '推荐用户2', avatar: 'https://via.placeholder.com/40', followed: false }
      ],
      showUserMenu: false
    }
  },
  computed: {
    filteredPosts() {
      if (!this.searchText.trim()) return this.posts
      return this.posts.filter(post => post.content.includes(this.searchText) || post.username.includes(this.searchText))
    }
  },
  created() {
    const user = localStorage.getItem('user')
    if (user) {
      this.currentUser = JSON.parse(user)
    }
  },
  methods: {
    goToLogin() {
      this.$router.push('/login')
    },
    handleLogout() {
      localStorage.removeItem('token');
      localStorage.removeItem('tokenExpireTime');
      localStorage.removeItem('user');
      this.currentUser = null;
      this.showUserMenu = false;
      this.$router.push('/login');
    },
    async handlePost() {
      if (!this.newPost.trim() && !this.newPostImage) {
        alert('请输入内容或上传图片');
        return;
      }

      try {
        // TODO: 实现发布功能
        this.newPost = '';
        this.newPostImage = null;
        await this.fetchPosts();
      } catch (error) {
        console.error('发布失败:', error);
      }
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.newPostImage = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    removeImage() {
      this.newPostImage = null;
    },
    async handleLike(post) {
      if (!this.currentUser) {
        this.goToLogin();
        return;
      }
      // TODO: 实现点赞功能
    },
    async handleComment(post) {
      if (!this.currentUser) {
        this.goToLogin();
        return;
      }
      // TODO: 实现评论功能
    },
    async handleShare(post) {
      // TODO: 实现分享功能
    },
    async deletePost(post) {
      if (!confirm('确定要删除这条动态吗？')) return;
      // TODO: 实现删除功能
    },
    toggleFollow(user) {
      user.followed = !user.followed
    },
    autoResize(event) {
      event.target.style.height = 'auto';
      event.target.style.height = event.target.scrollHeight + 'px';
    },
    highlightContent(content) {
      if (!content) return '';
      return content
        .replace(/@(\w+)/g, '<span class="mention">@$1</span>')
        .replace(/#(\w+)/g, '<span class="hashtag">#$1</span>');
    },
    goToProfile() {
      this.$router.push('/profile');
    },
    async fetchPosts() {
      try {
        this.loading = true;
        this.error = null;  // 清除之前的错误
        
        console.log('Fetching posts, page:', this.currentPage, 'size:', this.pageSize);
        
        const response = await axios.get(`/api/posts`, {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        });
        
        console.log('Response:', response.data);
        
        if (response.data.code === 200) {
          if (this.currentPage === 1) {
            this.posts = response.data.data.posts;
          } else {
            this.posts = [...this.posts, ...response.data.data.posts];
          }
          this.totalPosts = response.data.data.total;
        } else {
          throw new Error(response.data.message || '获取数据失败');
        }
      } catch (error) {
        console.error('获取动态列表失败:', error);
        this.error = error.response?.data?.message || error.message || '加载失败，请刷新页面重试';
        
        // 如果是网络错误，显示更具体的提示
        if (!error.response) {
          this.error = '网络连接失败，请检查网络设置';
        }
      } finally {
        this.loading = false;
      }
    },
    async loadMore() {
      if (this.loading) return;
      if (this.posts.length >= this.totalPosts) return;
      this.currentPage++;
      await this.fetchPosts();
    },
    formatSmartTime(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      const now = new Date();
      const diff = now - date;

      if (diff < 60 * 1000) return '刚刚';
      if (diff < 60 * 60 * 1000) return `${Math.floor(diff / (60 * 1000))}分钟前`;
      if (diff < 24 * 60 * 60 * 1000) return `${Math.floor(diff / (60 * 60 * 1000))}小时前`;
      if (diff < 7 * 24 * 60 * 60 * 1000) return `${Math.floor(diff / (24 * 60 * 60 * 1000))}天前`;

      // 超过7天，显示到天
      const y = date.getFullYear();
      const m = String(date.getMonth() + 1).padStart(2, '0');
      const d = String(date.getDate()).padStart(2, '0');
      return `${y}-${m}-${d}`;
    },
    previewImage(url) {
      // TODO: 实现图片预览功能
    },
    reportPost(post) {
      // TODO: 实现举报功能
    },
    retryLoad() {
      this.error = null;
      this.currentPage = 1;
      this.posts = [];  // 清空现有数据
      this.fetchPosts();
    },
    toggleUserMenu() {
      this.showUserMenu = !this.showUserMenu;
    },
    closeUserMenu(e) {
      if (this.showUserMenu) {
        this.showUserMenu = false;
      }
    },
    handleHot(post) {
      // 这里可以弹窗显示热度详情，或后续扩展
      // alert('热度详情功能开发中');
    }
  },
  mounted() {
    this.fetchPosts()
      .catch(error => {
        console.error('初始化加载失败:', error);
        this.error = '加载失败，请刷新页面重试';
      });
    document.addEventListener('click', this.closeUserMenu);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.closeUserMenu);
  }
}
</script>

<style scoped>
.home-x-layout {
  display: flex;
  min-height: 100vh;
  background: #0a0e17;
  position: relative;
  overflow: hidden;
}

.main-content-x {
  flex: 1;
  max-width: 600px;
  margin: 0 auto;
  padding: 32px 16px 0 16px;
  display: flex;
  flex-direction: column;
  gap: 18px;
  position: relative;
  z-index: 2;
}

.search-x {
  width: 100%;
  margin-bottom: 10px;
}

.search-input-wrapper {
  position: relative;
  width: 100%;
}

.search-icon {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #00ffe7;
  font-size: 1.2em;
  filter: drop-shadow(0 0 4px #1da1f2cc);
}

.search-x input {
  width: 100%;
  padding: 12px 18px 12px 48px;
  border-radius: 12px;
  border: none;
  background: rgba(35, 42, 54, 0.8);
  font-size: 1rem;
  color: #eafaff;
  outline: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.search-x input:focus {
  background: rgba(35, 42, 54, 0.9);
  box-shadow: 0 0 0 2px #00ffe7cc, 0 0 8px #1da1f2cc;
}

.post-form-x {
  background: rgba(16, 20, 30, 0.95);
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0, 255, 231, 0.1);
  padding: 20px;
  margin-bottom: 8px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(35, 42, 54, 0.8);
}

.post-form-x textarea {
  width: 100%;
  min-height: 60px;
  resize: none;
  border: none;
  outline: none;
  font-size: 1.1rem;
  background: rgba(35, 42, 54, 0.8);
  border-radius: 12px;
  padding: 12px;
  margin-bottom: 8px;
  color: #eafaff;
  transition: all 0.3s ease;
}

.post-form-x textarea:focus {
  background: rgba(35, 42, 54, 0.9);
  box-shadow: 0 0 0 2px #00ffe7cc, 0 0 8px #1da1f2cc;
}

.post-form-tools-x {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 4px;
}

.tool-btn-x {
  background: none;
  border: none;
  padding: 4px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}
.tool-btn-x:hover {
  background: rgba(29,161,242,0.08);
}
.tool-btn-x svg {
  display: block;
}

.post-btn-x {
  align-self: flex-end;
  background: linear-gradient(90deg, #1da1f2 0%, #00ffe7 100%);
  color: #fff;
  border: none;
  border-radius: 12px;
  padding: 10px 24px;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  box-shadow: 0 2px 12px rgba(0, 255, 231, 0.2);
}

.post-btn-x:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 24px rgba(0, 255, 231, 0.3);
}

.btn-icon {
  font-size: 1.2em;
  filter: drop-shadow(0 0 4px #1da1f2cc);
}

.posts-x {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.post-x {
  background: rgba(16, 20, 30, 0.95);
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0, 255, 231, 0.1);
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  transition: all 0.3s ease;
  position: relative;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(35, 42, 54, 0.8);
}

.post-x:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 32px rgba(0, 255, 231, 0.15);
}

.post-header-x {
  display: flex;
  align-items: center;
  gap: 12px;
  position: relative;
}

.avatar-x {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #00ffe7;
  box-shadow: 0 0 8px #1da1f2cc;
}

.post-info-x {
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-width: 0;
  flex: 1 1 auto;
  margin-right: 8px;
}

.username-x {
  font-weight: bold;
  color: #00ffe7;
  font-size: 1.15rem;
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 180px;
  display: block;
}

.user-id-x {
  color: #b0eaff;
  font-size: 0.98rem;
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 180px;
  display: block;
}

.time-x {
  color: #7a8fa6;
  font-size: 0.97em;
  margin-top: 2px;
  display: block;
}

.post-content-x {
  color: #eafaff;
  font-size: 1.1rem;
  line-height: 1.5;
  margin: 8px 0;
}

.highlight-topic-x {
  color: #00ffe7;
  font-weight: 600;
}

.highlight-at-x {
  color: #1da1f2;
  font-weight: 600;
}

.post-actions-x {
  display: flex;
  gap: 16px;
  margin-top: 8px;
}

.action-btn-x {
  background: none;
  border: none;
  color: #b0eaff;
  font-size: 1rem;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 12px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 6px;
}

.action-btn-x:hover {
  background: rgba(29, 161, 242, 0.1);
  color: #00ffe7;
  transform: translateY(-2px);
}

.action-btn-x.liked-x {
  color: #00ffe7;
}

.action-icon {
  font-size: 1.2em;
  filter: drop-shadow(0 0 4px #1da1f2cc);
}

.rightbar-x {
  width: 300px;
  padding: 32px 16px 0 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  position: relative;
  z-index: 2;
}

.search-x {
  margin-bottom: 18px;
  width: 100%;
}

.search-right-x {
  margin-bottom: 18px;
  width: 100%;
}

.search-right-x input {
  width: 100%;
  padding: 10px 18px;
  border-radius: 999px;
  border: 1.5px solid #e6ecf0;
  background: #181c24;
  color: #00ffe7;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.2s;
}

.search-right-x input:focus {
  border-color: #00ffe7;
  background: #232a36;
}

.trending-x, .who-x, .discover-x {
  background: rgba(16, 20, 30, 0.95);
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0, 255, 231, 0.1);
  padding: 18px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(35, 42, 54, 0.8);
}

.trending-x h3, .who-x h3, .discover-x h3 {
  color: #00ffe7;
  font-size: 1.1rem;
  font-weight: 700;
  margin-bottom: 12px;
  text-shadow: 0 2px 16px #1da1f2cc;
}

.topic-title-x {
  color: #00ffe7;
  font-weight: 600;
  transition: all 0.3s ease;
}

.topic-title-x:hover {
  color: #1da1f2;
  text-shadow: 0 0 8px #00ffe7cc;
}

.topic-count-x {
  color: #b0eaff;
  font-size: 0.95rem;
}

.who-user-x {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
  padding: 8px;
  border-radius: 12px;
  transition: all 0.3s ease;
}

.who-user-x:hover {
  background: rgba(29, 161, 242, 0.1);
  transform: translateX(4px);
}

.follow-btn-x {
  background: rgba(29, 161, 242, 0.1);
  color: #00ffe7;
  border: none;
  border-radius: 12px;
  padding: 6px 16px;
  font-size: 0.98rem;
  font-weight: 600;
  transition: all 0.3s ease;
  margin-top: 4px;
}

.follow-btn-x:hover {
  background: rgba(29, 161, 242, 0.2);
  transform: translateY(-2px);
}

.discover-x ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.discover-x li {
  padding: 8px 0;
  color: #b0eaff;
  font-size: 0.98rem;
  border-bottom: 1px solid rgba(35, 42, 54, 0.8);
}

.discover-x li:last-child {
  border-bottom: none;
}

@media (max-width: 1100px) {
  .rightbar-x { display: none; }
  .sidebar-x { display: none; }
  .main-content-x { max-width: 100vw; }
}

@media (max-width: 700px) {
  .main-content-x { padding: 16px 2vw 0 2vw; }
  .post-x { padding: 16px; }
  .post-content-x { font-size: 1rem; }
  .ai-illustration { display: none; }
  .main-content-x { padding-top: 32px; }
}

/* 增加未登录发布框的样式 */
.post-form-x.not-logged-in {
  opacity: 0.92;
  filter: grayscale(0.2);
  position: relative;
  box-shadow: 0 2px 12px rgba(255, 118, 117, 0.08);
}

.login-tip-x {
  color: #ff7675;
  font-size: 0.98rem;
  margin-top: 10px;
  text-align: center;
  letter-spacing: 1px;
  display: flex;
  align-items: center;
  justify-content: center;
  user-select: none;
}

.sidebar-x {
  position: fixed;
  left: 0;
  top: 0;
  width: 220px;
  height: 100vh;
  background: #181b22;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  z-index: 100;
  border-right: 1px solid #232a36;
  overflow: hidden;
}

.logo-x {
  margin: 32px auto 24px auto;
  display: flex;
  justify-content: center;
  width: 100%;
}

.logo-icon {
  filter: drop-shadow(0 0 12px #00ffe7) drop-shadow(0 0 24px #00ffe7);
}

.nav-x {
  width: 100%;
  flex: 0 0 auto;
}
.nav-x ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
.nav-x li {
  display: flex;
  align-items: center;
  padding: 12px 24px;
  color: #00ffe7;
  font-size: 18px;
  cursor: pointer;
  border-radius: 24px;
  margin-bottom: 4px;
  transition: background 0.2s, color 0.2s;
}
.nav-x li.active,
.nav-x li:hover {
  background: #232a36;
  color: #fff;
}
.nav-svg {
  margin-right: 16px;
  display: flex;
  align-items: center;
}
.nav-label {
  font-weight: 500;
}

.sidebar-user-x {
  width: 100%;
  display: flex;
  justify-content: center;
  position: absolute;
  left: 0;
  bottom: 32px;
  z-index: 10;
}

.user-card-x {
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 14px;
  padding: 12px 12px;
  border-radius: 16px;
  background: rgba(35, 42, 54, 0.8);
  box-shadow: 0 0 8px #00ffe733;
  margin: 0 0 12px 0;
  position: relative;
}

.user-avatar-x {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #00ffe7;
  box-shadow: 0 0 8px #1da1f2cc;
  flex-shrink: 0;
}

.user-meta-x {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  min-width: 0;
  flex: 1 1 auto;
  margin: 0 12px;
  width: 0;
}

.user-nickname-x {
  font-weight: bold;
  color: #00ffe7;
  font-size: 1.15rem;
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
  display: block;
}

.user-id-x {
  color: #b0eaff;
  font-size: 0.98rem;
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
  display: block;
}

.user-more-x {
  position: relative;
  cursor: pointer;
  padding: 4px;
  border-radius: 50%;
  transition: background 0.2s;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user-more-x:hover {
  background: rgba(0,255,231,0.08);
}

.user-menu-x {
  position: absolute;
  bottom: 36px;
  right: 0;
  background: #181e2a;
  border-radius: 10px;
  box-shadow: 0 4px 24px #00ffe733;
  min-width: 90px;
  z-index: 10;
  padding: 6px 0;
  animation: fadeInUp 0.18s;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(8px);}
  to { opacity: 1; transform: translateY(0);}
}
.user-menu-item-x {
  color: #00ffe7;
  padding: 10px 18px;
  cursor: pointer;
  font-size: 1rem;
  text-align: left;
  border: none;
  background: none;
  transition: background 0.2s;
}
.user-menu-item-x:hover {
  background: rgba(0,255,231,0.08);
  color: #fff;
}

.main-tabs-x {
  display: flex;
  gap: 32px;
  border-bottom: 2px solid #232a36;
  margin-bottom: 18px;
  margin-top: 8px;
  background: transparent;
}

.main-tabs-x button {
  background: none;
  border: none;
  font-size: 1.13rem;
  font-weight: 700;
  color: #b0eaff;
  padding: 16px 0 12px 0;
  cursor: pointer;
  border-bottom: 2.5px solid transparent;
  border-radius: 0;
  transition: color 0.2s, border-color 0.2s;
  font-family: inherit;
  outline: none;
}

.main-tabs-x button.active {
  color: #00ffe7;
  border-bottom: 2.5px solid #00ffe7;
  background: transparent;
  text-shadow: 0 0 8px #00ffe7cc;
}

.loading-x {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  color: #666;
}

.loading-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid #f3f3f3;
  border-top: 2px solid #00ffe7;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-right: 10px;
}

.load-more-x {
  text-align: center;
  padding: 20px;
}

.load-more-x button {
  background: transparent;
  border: 1px solid #00ffe7;
  color: #00ffe7;
  padding: 8px 20px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.load-more-x button:hover {
  background: rgba(0, 255, 231, 0.1);
}

.load-more-x button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 新增动态列表相关样式 */
.posts-x {
  padding: 16px;
}

.loading-x {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  color: #666;
}

.loading-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid #f3f3f3;
  border-top: 2px solid #00ffe7;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-right: 10px;
}

.load-more-x {
  text-align: center;
  padding: 20px;
}

.load-more-x button {
  background: transparent;
  border: 1px solid #00ffe7;
  color: #00ffe7;
  padding: 8px 20px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.load-more-x button:hover {
  background: rgba(0, 255, 231, 0.1);
}

.load-more-x button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.post-x {
  background: #fff;
  border-radius: 12px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.post-header-x {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.post-info-x {
  flex: 1;
  margin-left: 12px;
}

.post-user-info-x {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.username-x {
  font-weight: bold;
  color: #333;
  display: block;
}

.time-x {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
  display: block;
}

.post-content-x {
  margin: 12px 0;
  line-height: 1.6;
  color: #333;
  white-space: pre-wrap;
  word-break: break-word;
}

.post-img-x {
  margin: 12px 0;
}

.post-img-x img {
  max-width: 100%;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s;
}

.post-img-x img:hover {
  transform: scale(1.02);
}

.post-actions-x {
  display: flex;
  gap: 16px;
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #eee;
}

.action-btn-x {
  display: flex;
  align-items: center;
  gap: 4px;
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: all 0.3s;
}

.action-btn-x:hover {
  background: rgba(0, 0, 0, 0.05);
}

.action-btn-x.liked-x {
  color: #ff4757;
}

.action-icon {
  font-size: 18px;
}

.more-btn-x {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: all 0.3s;
}

.more-btn-x:hover {
  background: rgba(0, 0, 0, 0.05);
}

.more-menu-x {
  position: absolute;
  right: 16px;
  top: 40px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 8px 0;
  z-index: 100;
}

.more-menu-x li {
  padding: 8px 16px;
  cursor: pointer;
  transition: all 0.3s;
}

.more-menu-x li:hover {
  background: rgba(0, 0, 0, 0.05);
}

.mention {
  color: #00ffe7;
  font-weight: bold;
}

.hashtag {
  color: #00ffe7;
  font-weight: bold;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 响应式调整 */
@media (max-width: 768px) {
  .posts-x {
    padding: 8px;
  }
  
  .post-x {
    padding: 12px;
  }
  
  .post-actions-x {
    gap: 12px;
  }
}

.error-message {
  text-align: center;
  padding: 20px;
  margin: 16px;
  background: rgba(255, 71, 87, 0.1);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.error-icon {
  font-size: 24px;
}

.error-text {
  color: #ff4757;
  font-size: 14px;
}

.retry-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: #ff4757;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.retry-button:hover {
  background: #ff6b81;
  transform: scale(1.05);
}

.retry-icon {
  font-size: 16px;
}

.action-btn-x.hot-x .action-icon {
  color: #ff9800;
  filter: drop-shadow(0 0 4px #ff9800cc);
}

.baidu-hot-link {
  color: #00ffe7;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.2s;
}

.baidu-hot-link:hover {
  color: #1da1f2;
  text-decoration: underline;
}
</style> 