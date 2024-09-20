import axios from '@/server'
import { usePublicStore } from '@/stores'
import AesUtils from '@/util/aesUtils'
import { handleLoginOut } from '@/util/commonUtils'
import type {
  announcementDataReq,
  LoginReqForm,
  overViewDataReq,
  rankTableDataReq,
  UpdatePasswordReqFormF
} from '@/util/typeUtils'
import { ElMessage } from 'element-plus'

// 请求前缀
const URL_PREFIX: string = '/public/'

/**
 * 获取RSA公钥
 */
export const getRSAPublicKey = async () => {
  let resData: string = null
  await axios
    .get<string>(URL_PREFIX + 'getRSAPublicKey')
    .then((res) => {
      if (res.state === 200) {
        console.debug('RSA公钥:', res.data)
        resData = res.data as string
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取RSA公匙失败:' + err.message)
    })
  return resData
}

/**
 * 登陆请求
 */
export const login = async (params: LoginReqForm) => {
  let resStatus: boolean = false
  const publicStore = usePublicStore()
  await axios
    .post<string>(URL_PREFIX + 'login', AesUtils.getAESUtils().encrypt(params), {
      headers: {
        'Content-Type': 'application/json',
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: 1
      }
    })
    .then((res) => {
      if (res.state === 600) {
        // 储存sessionId
        window.localStorage.setItem(import.meta.env.VITE_SESSION_ID, res.data as string)
        console.log('会话ID为:', window.localStorage.getItem(import.meta.env.VITE_SESSION_ID))
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('登陆失败:' + err.message)
    })
  return resStatus
}

/**
 * 获取验证码
 */
export const getCaptcha = (studentId: number, email: string) => {
  axios
    .get(URL_PREFIX + 'getCaptcha', {
      params: {
        studentId: studentId,
        email: email
      }
    })
    .then((res) => {
      if (res.state === 622) {
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取验证码失败:' + err.message)
    })
}

/**
 * 修改密码（未登录）
 */
export const updatePasswordF = async (params: UpdatePasswordReqFormF) => {
  let resStatus: boolean = false
  const publicStore = usePublicStore()
  await axios
    .put(URL_PREFIX + 'updatePasswordF', AesUtils.getAESUtils().encrypt(params), {
      headers: {
        'Content-Type': 'application/json',
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: 1
      }
    })
    .then((res) => {
      if (res.state === 610) {
        ElMessage.success(res.message)
        resStatus = true
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('修改密码失败:' + err.message)
    })
  return resStatus
}

/**
 * 退出登陆
 */
export const loginOut = () => {
  axios
    .get(URL_PREFIX + 'loginOut', {
      headers: {
        Encrypt: 0,
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID)
      }
    })
    .then((res) => {
      if (res.state === 601) {
        ElMessage.success(res.message)
      } else {
        ElMessage.warning(res.message)
      }
      handleLoginOut()
    })
    .catch((err) => {
      ElMessage.error('登出失败:' + err.message)
      handleLoginOut()
    })
}

/**
 * 获取人员概况
 */
export const getOverview = async () => {
  let resData: overViewDataReq = null
  await axios
    .get<overViewDataReq>(URL_PREFIX + 'getOverview')
    .then((res) => {
      resData = res.data
    })
    .catch((err) => {
      ElMessage.error('获取人员概况失败:', err)
    })
  return resData
}

/**
 * 获取排名
 */
export const getRank = async () => {
  let resData: Array<rankTableDataReq> = null
  await axios
    .get<Array<rankTableDataReq>>(URL_PREFIX + 'getRank')
    .then((res) => {
      resData = res.data
    })
    .catch((err) => {
      ElMessage.error('获取人员排名失败:' + err.messager)
    })
  return resData
}

/**
 * 获取公告
 */
export const getAnnouncement = async () => {
  let resData: Array<announcementDataReq> = null
  await axios
    .get<Array<announcementDataReq>>(URL_PREFIX + 'getAnnouncement')
    .then((res) => {
      if (res.state === 200) {
        resData = res.data
      }
    })
    .catch((err) => {
      ElMessage.error('获取公告信息失败:' + err.message)
    })
  return resData
}
