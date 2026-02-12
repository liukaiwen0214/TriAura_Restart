<script setup lang="ts">
import { onMounted } from 'vue'
import { useAuth } from "../auth/useAuth";
import { useRouter } from 'vue-router'

const router = useRouter()
const { state, fetchMe, logout } = useAuth()

/**
 * 进入首页时再拉一次 me（保险）
 */
onMounted(async () => {
  if (!state.loaded) {
    try { await fetchMe() } catch {}
  }
})

async function doLogout() {
  await logout()
  router.replace('/login')
}
</script>

<template>
  <div class="page">
    <h1>Home</h1>

    <div class="card">
      <div class="row">
        <span class="label">当前用户</span>
        <span class="value">{{ state.me?.email || state.me?.phone || state.me?.id }}</span>
      </div>
      <div class="row">
        <span class="label">角色</span>
        <span class="value">{{ state.me?.role }}</span>
      </div>

      <button @click="doLogout">退出登录</button>
    </div>
  </div>
</template>

<style scoped>
.page { padding: 24px; }
.card { max-width: 420px; border: 1px solid #eee; border-radius: 14px; padding: 16px; display: grid; gap: 12px; }
.row { display: flex; justify-content: space-between; gap: 12px; }
.label { color: #666; }
button { padding: 10px 12px; border: none; border-radius: 10px; cursor: pointer; }
</style>