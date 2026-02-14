<template>
  <AuthLayout
    :subtitle="
      tab === 'login'
        ? '欢迎回来，继续你的 TriAura 之旅'
        : '创建账号，开始你的 TriAura 系统'
    "
  >
    <!-- ✅ Toast 必须渲染在页面里（不要只在 main.ts 注册组件名） -->
    <div class="switcherWrap">
      <Transition name="fade-slide" mode="out-in">
        <LoginForm
          v-if="tab === 'login'"
          key="login"
          @go-register="setTab('register')"
        />

        <RegisterForm v-else key="register" @go-login="setTab('login')" />
      </Transition>
    </div>
    <template #footer>
      <div class="foot">
        登录/注册即表示同意
        <a class="link" href="javascript:void(0)">用户协议</a>
        与
        <a class="link" href="javascript:void(0)">隐私政策</a>
      </div>
    </template>
  </AuthLayout>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import AuthLayout from "@/components/AuthLayout.vue";
import LoginForm from "./LoginForm.vue";
import RegisterForm from "./RegisterForm.vue";

const route = useRoute();
const router = useRouter();

const tab = computed<"login" | "register">(() => {
  const q = String(route.query.tab || "login").toLowerCase();
  return q === "register" ? "register" : "login";
});

function setTab(t: "login" | "register") {
  router.replace({ path: "/auth", query: { ...route.query, tab: t } });
}
</script>

<style scoped>
.sep {
  color: #cbd5e1;
}

.link {
  color: #0f172a;
  text-decoration: none;
}
.link:hover {
  text-decoration: underline;
}

.foot {
  justify-content: center;
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  align-items: center;
  text-align: center;
  justify-items: center;
}

.mockWrap {
  display: grid;
  gap: 10px;
  padding: 4px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.92);
}
.mockRow {
  display: flex;
  align-items: center;
  gap: 10px;
  opacity: 0.95;
}
.dot {
  width: 8px;
  height: 8px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.75);
}
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition:
    opacity 0.22s ease,
    transform 0.22s ease;
  will-change: opacity, transform;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(14px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

.fade-slide-enter-to,
.fade-slide-leave-from {
  opacity: 1;
  transform: translateY(0);
}
.switcherWrap {
  width: 100%;
}
</style>
