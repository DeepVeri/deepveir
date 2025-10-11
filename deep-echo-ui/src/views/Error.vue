<template>
  <div class="error">
    <h1>出错了！</h1>
    <p>抱歉，页面出现了一些问题。</p>
    <button @click="$router.push('/')">返回首页</button>
  </div>
</template>

<script>
export default {
  name: 'Error'
}
</script>

<style scoped>
.error {
  padding: 20px;
  text-align: center;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #1da1f2;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

button:hover {
  background-color: #0a7bbf;
}
</style> 