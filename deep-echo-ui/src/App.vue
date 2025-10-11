<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
:root {
  --primary-color: #1da1f2;
  --secondary-color: #0a7bbf;
  --text-color: #14171a;
  --light-text: #657786;
  --border-color: #e1e8ed;
  --background-color: #ffffff;
  --hover-color: #f5f8fa;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
  color: var(--text-color);
  line-height: 1.5;
  background-color: var(--background-color);
}

button {
  cursor: pointer;
  border: none;
  background: none;
  font-family: inherit;
}

input {
  font-family: inherit;
  border: none;
  outline: none;
  background: none;
}

a {
  color: var(--primary-color);
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style> 