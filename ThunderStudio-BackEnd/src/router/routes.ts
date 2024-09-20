import type { RouteRecordRaw } from 'vue-router'

// 公共界面
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'Login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('@/views/HomeView.vue')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404View.vue')
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404'
  }
]

export default routes
