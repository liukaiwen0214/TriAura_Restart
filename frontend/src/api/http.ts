import axios from "axios";
import type { ApiResponse } from "./types";

/**
 * axios 实例：走 Vite 代理（/api -> http://localhost:8080）
 * Cookie 会自动带上，不需要你手动塞 token
 */
export const http = axios.create({
  timeout: 15000,
});

/**
 * 响应拦截：
 * - HTTP 200 ≠ 业务成功
 * - 业务成功看 code===0
 * - 业务失败直接 reject，把 ApiResponse 当 error 抛出去
 */
http.interceptors.response.use(
  (resp) => {
    const body = resp.data as ApiResponse;
    if (body && typeof body.code === "number" && body.code !== 200) {
      return Promise.reject(body);
    }
    return resp;
  },
  (error) => Promise.reject(error),
);

/**
 * 便捷方法：只返回 data
 */
export async function get<T = any>(url: string) {
  const resp = await http.get<ApiResponse<T>>(url);
  return resp.data.data;
}

export async function post<T = any>(url: string, data?: any) {
  const resp = await http.post<ApiResponse<T>>(url, data);
  return resp.data.data;
}
