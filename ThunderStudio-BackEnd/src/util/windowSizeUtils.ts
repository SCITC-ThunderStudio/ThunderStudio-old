import { usePublicStore } from '@/stores'

const windowSize = (): void => {
  const publicStore = usePublicStore()
  publicStore.handleWindowSize(window.innerWidth, window.innerHeight)
}

const initWindowSize = (): void => {
  const publicStore = usePublicStore()
  publicStore.windowWidth = window.innerWidth
  publicStore.windowHeight = window.innerHeight
  publicStore.windowWidth <= 768
    ? (publicStore.isCollapse = true)
    : (publicStore.isCollapse = false)
}

// 监听浏览器窗口大小变化
export const watchWindowSize = (): void => {
  initWindowSize()
  window.addEventListener('resize', windowSize)
}

// 移除监听浏览器窗口大小变化
export const unWatchWindowSize = (): void => {
  window.removeEventListener('resize', windowSize)
}
