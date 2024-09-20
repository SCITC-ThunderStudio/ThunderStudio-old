import type { userInfo } from '@/util/typeUtils'
import { defineStore } from 'pinia'

// 用户信息
export const useUserInfoStore = defineStore('UserInfo', {
  state: () => ({
    user: {} as userInfo
  }),
  actions: {
    // 设置用户信息
    setUserInfo(userInfo: string) {
      const user: userInfo = JSON.parse(userInfo)
      console.debug('用户信息:', user)
      this.user.uid = user.uid
      this.user.studentClass = user.studentClass
      this.user.name = user.name
      this.user.email = user.email
      this.user.questionNumber = user.questionNumber
      this.user.joinDate = user.joinDate
      this.user.authority = user.authority as string
      this.user.avatarPath = user.avatarPath
      this.user.ranking = user.ranking
    }
  },
  persist: {
    // 修改存储中使用的键名称，默认为当前 Store的 id
    // key: 'user-info-data',
    // 修改为 sessionStorage，默认为 localStorage
    storage: window.localStorage,
    // 部分持久化状态的点符号路径数组，[]意味着没有状态被持久化(默认为undefined，持久化整个状态)
    paths: ['user']
  }
})
