import { createRouter, createWebHistory } from "vue-router";
import TriAura from "@/pages/TriAura.vue";
import AuthSwitcher from "@/pages/auth/AuthSwitcher.vue";
import { useAuth } from "@/auth/useAuth";

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    // ✅ 统一入口
    { path: "/auth", component: AuthSwitcher, meta: { requiresAuth: false, hideNav: true } },
    // ✅ 兼容旧地址：/login /register 都导到 /auth
    { path: "/login", redirect: { path: "/auth", query: { tab: "login" } } },
    {
      path: "/register",
      redirect: { path: "/auth", query: { tab: "register" } },
    },

    // 首页需要登录
    { path: "/", component: TriAura, meta: { requiresAuth: true } },

    // 其它路径都导到 /auth
    { path: "/:pathMatch(.*)*", redirect: "/auth" },
  ],
});

router.beforeEach(async (to) => {
  if (!to.meta.requiresAuth) return true;

  const { state, fetchMe } = useAuth();

  if (state.loaded && state.me) return true;

  if (!state.loaded) {
    try {
      await fetchMe();
      if (state.me) return true;
    } catch (e) {
      // ignore
    }
  }

  return {
    path: "/auth",
    query: { tab: "login", redirect: to.fullPath },
  };
});
