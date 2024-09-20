import { defineStore } from 'pinia'

// 公共信息
export const usePublicStore = defineStore('Public', {
  state: () => ({
    // 暗黑模式
    darkMode: {
      isDarkMode: false,
      userDarkMode: false
    },
    // 是否关闭侧边栏
    isCollapse: false,
    // 验证码时间
    captchaTime: 60,
    // RSA公钥
    rsaPublicKey: '',
    // 加密后的AES密钥
    aesKey_RSA: '',
    // 加密后的AES偏移量
    aesIv_RSA: '',
    // 窗口宽度
    windowWidth: 0,
    // 窗口高度
    windowHeight: 0,
    // 登录错误计数
    loginCount: 2
  }),
  actions: {
    // 处理窗口尺寸变化
    handleWindowSize(windowWidth: number, windowHeight: number) {
      this.windowHeight = windowHeight
      this.windowWidth = windowWidth
    }
  },
  // 数据持久化
  persist: {
    // 持久化方式
    storage: window.sessionStorage,
    paths: [
      'isDarkMode',
      'captchaTime',
      'rsaPublicKey',
      'aesKey_RSA',
      'aesIvRSA',
      'darkMode',
      'loginCount'
    ]
  }
})
