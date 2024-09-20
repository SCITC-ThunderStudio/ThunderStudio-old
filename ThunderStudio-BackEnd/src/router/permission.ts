import router from '@/router/index'
import { getRouter } from '@/server/module/user'
import { useRouterListStore } from '@/stores'
import { ElMessageBox } from 'element-plus'

// 白名单
const whiteList = ['Login', '404']

router.beforeEach(async (to) => {
  const routeListStore = useRouterListStore()
  // 是否在白名单内
  if (whiteList.indexOf(`${String(to.name)}`) !== -1) {
    console.log('白名单放行')
    return true
  } else {
    // 判断用户是否登录
    if (window.localStorage.getItem(import.meta.env.VITE_SESSION_ID) === null) {
      ElMessageBox.alert('身份验证过期请重新登陆', '警告', {
        confirmButtonText: '确认',
        showClose: false,
        callback: () => {
          router.push('/').then()
        }
      }).then()
    } else {
      // 是否获取了路由
      if (routeListStore.asyncRouterMark) {
        // 跳转的路由是否存在
        if (!router.hasRoute(to.name as string)) {
          await addRouterList()
          return to.fullPath
        } else {
          return true
        }
      } else {
        getRouter().then((res) => {
          if (res) {
            return to.fullPath
          } else {
            router.push('/').then()
          }
        })
      }
    }
  }
})

/**
 * 初始化路由
 */
export const initRouter = async () => {
  const routeListStore = useRouterListStore()
  // 如果本地已经获取过则不需要再向服务器获取
  if (routeListStore.asyncRouterMark) {
    await addRouterList()
    // 本地已经有sessionId则从服务器获取路由表
  } else if (window.localStorage.getItem(import.meta.env.VITE_SESSION_ID) !== null) {
    await getRouter().then((res) => {
      if (res) {
        addRouterList()
      }
    })
  }
}

/**
 * 添加动态路由
 */
export const addRouterList = async () => {
  const routeListStore = useRouterListStore()
  const modules = import.meta.glob('@/views/*.vue')
  routeListStore.routerList.forEach((element: any) => {
    router.addRoute('Home', {
      path: `${element.path}`,
      name: element.name,
      component: modules[`/src/views/${element.name}.vue`],
      meta: element.meta
    })
  })
}
