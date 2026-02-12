import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import { useAuth } from '@/auth/useAuth'

/**
 * meta.requiresAuth = true 表示需要登录
 */
export const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/login', component: Login, meta: { requiresAuth: false } },
        { path: '/register', component: Register, meta: { requiresAuth: false } },
        
        // 首页需要登录
        { path: '/', component: Home, meta: { requiresAuth: true } },
    ],
})

/**
 * 全局前置守卫：
 * - 访问需要登录的页面：先确保 me 已加载
 * - 未登录：跳转到 /login，并带上 redirect 参数
 */
router.beforeEach(async (to) => {
    if (!to.meta.requiresAuth) return true
    
    const { state, fetchMe } = useAuth()
    
    // 已经加载并且有用户，直接放行
    if (state.loaded && state.me) return true
    
    // 未加载就拉一次 /me
    if (!state.loaded) {
        try {
            await fetchMe()
            if (state.me) return true
        } catch (e) {
            // 忽略，下面统一跳登录
        }
    }
    
    // 未登录：跳转登录页
    return {
        path: '/login',
        query: { redirect: to.fullPath },
    }
})