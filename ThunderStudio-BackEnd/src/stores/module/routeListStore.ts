import { defineStore } from 'pinia'
import router from '@/router'
import type { routerListReq } from '@/util/typeUtils'

// 路由信息
export const useRouterListStore = defineStore('RouterList', {
  state: () => ({
    routerList: [] as routerListReq[],
    // 异步路由标志
    asyncRouterMark: false
  }),
  actions: {
    // 移除路由
    removeRouterList() {
      this.$reset()
      router.replace('/').then()
      router.addRoute({
        path: '/home',
        name: 'Home',
        component: () => import('@/views/HomeView.vue')
      })
    }
  },
  persist: {
    // 修改存储中使用的键名称，默认为当前 Store的 id
    // key: 'public-data',
    // 修改为 sessionStorage，默认为 localStorage
    storage: window.localStorage,
    // 部分持久化状态的点符号路径数组，[]意味着没有状态被持久化(默认为undefined，持久化整个状态)
    paths: ['routerList', 'asyncRouterMark']
  }
})
