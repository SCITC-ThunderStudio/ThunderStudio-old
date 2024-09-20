import axios from '@/server'
import { ElMessage } from 'element-plus'
import { usePublicStore, useUserInfoStore } from '@/stores'
import AesUtils from '@/util/aesUtils'
import { useRouterListStore } from '@/stores/module/routeListStore'
import type { PersonnelStateReqForm, routerListReq } from '@/util/typeUtils'
import { addRouterList } from '@/router/permission'

// 请求前缀
const URL_PREFIX: string = '/user/'

/**
 * 获取用户信息
 */
export const getUserInfo = async () => {
  let resStatus = false
  const publicStore = usePublicStore()
  const userInfoStore = useUserInfoStore()
  await axios
    .get<string>(URL_PREFIX + 'getUserInfo', {
      headers: {
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: 0,
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID)
      }
    })
    .then((res) => {
      if (res.state === 630) {
        const useInfo = AesUtils.getAESUtils().decrypt(res.data as string)
        // 设置用户信息
        userInfoStore.setUserInfo(useInfo)
        resStatus = true
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取用户信息失败:' + err.message)
    })
  return resStatus
}

/**
 * 获取路由
 */
export const getRouter = async () => {
  let resStatus: boolean = false
  const routerListStore = useRouterListStore()
  await axios
    .get<Array<routerListReq>>(URL_PREFIX + 'getRouter', {
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: 0
      }
    })
    .then((res) => {
      if (res.state === 200) {
        routerListStore.routerList = res.data
        console.debug('路由数据', routerListStore.routerList)
        routerListStore.asyncRouterMark = true
        resStatus = true
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取路由失败:' + err.message)
    })
  return resStatus
}

/**
 * 更新人员状态
 * @param personnelStateForm 人员状态表单
 */
export const updatePersonnelState = async (personnelStateForm: PersonnelStateReqForm) => {
  let resStatus = false
  const publicStore = usePublicStore()
  await axios
    .put(URL_PREFIX + 'updatePersonnelState', AesUtils.getAESUtils().encrypt(personnelStateForm), {
      headers: {
        'Content-Type': 'application/json',
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: 1
      }
    })
    .then((res) => {
      if (res.state === 665) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('人员状态更新失败:' + err.message)
    })
  return resStatus
}
