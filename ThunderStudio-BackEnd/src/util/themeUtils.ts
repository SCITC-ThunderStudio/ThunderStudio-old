import { usePublicStore } from '@/stores'

// 媒体查询深色模式
const darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)')

// 切换至浅色模式
export const switchToLightMode = (): void => {
  document.documentElement.classList.remove('dark')
  document.documentElement.dataset.theme = 'light'
}

// 切换至深色模式
export const switchToDarkMode = (): void => {
  document.documentElement.classList.add('dark')
  document.documentElement.dataset.theme = 'dark'
}

// 操作系统是否正处于深色模式
export const isDarkModeInSystem = (): void => {
  const publicStore = usePublicStore()
  if (publicStore.darkMode.userDarkMode) {
    publicStore.darkMode.isDarkMode ? switchToDarkMode() : switchToLightMode()
  } else {
    publicStore.darkMode.isDarkMode = darkModeQuery.matches
    publicStore.darkMode.isDarkMode ? switchToDarkMode() : switchToLightMode()
  }
}

// 监听系统深色模式变化
export const watchDarkMode = (): void => {
  const publicStore = usePublicStore()
  darkModeQuery.addEventListener('change', (e) => {
    publicStore.darkMode.isDarkMode = e.matches
    publicStore.darkMode.isDarkMode ? switchToDarkMode() : switchToLightMode()
  })
}

// 移除监听系统深色模式变化
export const unWatchDarkMode = (): void => {
  darkModeQuery.removeEventListener('change', () => {})
}
