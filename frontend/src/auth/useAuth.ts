import { reactive } from "vue";
import { get, post } from "@/api/http";

/**
 * 当前登录用户结构（对应后端 /auth/me 返回）
 */
export interface Me {
  id: string;
  username?: string | null;
  displayName?: string | null;
  email?: string | null;
  phone?: string | null;
  role?: string;
  status?: string;
  lastLoginAt?: string | null;
}

/**
 * 全局单例状态：一个项目里共用
 */
const state = reactive<{
  loaded: boolean; // 是否请求过 /me
  me: Me | null; // 当前用户
}>({
  loaded: false,
  me: null,
});

/**
 * 登录态管理
 */
export function useAuth() {
  /**
   * 拉取当前用户信息：
   * - 成功：state.me 有值
   * - 未登录：会被 http 拦截器 reject（code=10004）
   */
  async function fetchMe() {
    try {
      const me = await get<Me>("/api/v1/auth/me");
      state.me = me;
      state.loaded = true;
      return me;
    } catch (e) {
      state.me = null;
      state.loaded = true;
      throw e;
    }
  }

  /**
   * 密码登录：成功后 cookie 写入，接着 fetchMe 刷新用户信息
   */
  async function loginByPassword(account: string, password: string) {
    await post("/api/v1/auth/login/password", { account, password });
    await fetchMe();
  }

  /**
   * 验证码登录：成功后 cookie 写入，接着 fetchMe
   */
  async function loginByCode(
    channel: "EMAIL" | "PHONE",
    target: string,
    code: string,
  ) {
    await post("/api/v1/auth/login/code", { channel, target, code });
    await fetchMe();
  }

  /**
   * 退出登录：撤销会话 + 清 cookie
   */
  async function logout() {
    try {
      await post("/api/v1/auth/logout");
    } finally {
      state.me = null;
      state.loaded = true;
    }
  }

  return { state, fetchMe, loginByPassword, loginByCode, logout };
}
