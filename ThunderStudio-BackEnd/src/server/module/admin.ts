import axios from '@/server'
import { usePublicStore } from '@/stores'
import AesUtils from '@/util/aesUtils'
import type {
  ClockInRecordDataReq,
  clockInTableFDataReq,
  EvaluateWeeklyForm,
  PersonnelDataReq,
  questionQuantityDistributionDataReq,
  UpdateUserInfoReqFormAdmin,
  weeklyRecordDataReq,
  weeklyTableFDataReq
} from '@/util/typeUtils'
import axiosCustom from 'axios'
import { ElMessage } from 'element-plus'

// 请求前缀
const URL_PREFIX: string = '/admin'

/**
 * 获取未打卡人员名单
 *
 * @param option 选项,0今日,1昨日
 */
export const getClockInListF = async (option: number) => {
  let resData: Array<clockInTableFDataReq> = null
  const publicStore = usePublicStore()
  await axios
    .get<string>(`${URL_PREFIX}/getClockInListF`, {
      params: {
        option
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        const decryptingData = AesUtils.getAESUtils().decrypt(res.data)
        resData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取未打卡人员名单失败:' + err.message)
    })
  return resData
}

/**
 * 获取题量分布
 */
export const getQuestionQuantityDistribution = async () => {
  let resData: questionQuantityDistributionDataReq = null
  await axios
    .get<questionQuantityDistributionDataReq>(`${URL_PREFIX}/getQuestionQuantityDistribution`, {
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取题量分布失败:' + err.message)
    })
  return resData
}

/**
 * 获取打卡记录
 *
 *  @param option 选项,0今日,1全部
 * @param currentPage 当前页码
 * @param pageSize 每页记录数
 * @returns
 */
export const getClockInRecord = async (option: number, currentPage: number, pageSize: number) => {
  let resData: ClockInRecordDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get<ClockInRecordDataReq>(`${URL_PREFIX}/getClockInRecord`, {
      params: {
        option,
        currentPage,
        pageSize
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data
        const decryptingData = AesUtils.getAESUtils().decrypt(resData.tableData as string)
        resData.tableData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取打卡记录失败:' + err.message)
    })
  return resData
}

/**
 * 获取未提交周报人员名单
 *
 * @param option 选项,0本周,1上周
 */
export const getWeeklyListF = async (option: number) => {
  let resData: Array<weeklyTableFDataReq> = null
  const publicStore = usePublicStore()
  await axios
    .get<string>(`${URL_PREFIX}/getWeeklyListF`, {
      params: {
        option
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        const decryptingData = AesUtils.getAESUtils().decrypt(res.data)
        resData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取未提交周报人员名单失败:' + err.message)
    })
  return resData
}

/**
 * 上传训练营名单
 * @param TrainingList 训练营名单文件
 */
export const uploadTrainingListFile = async (TrainingList: File) => {
  let resStatus: boolean = false
  await axios
    .post(
      `${URL_PREFIX}/uploadTrainingListFile`,
      { TrainingList },
      {
        headers: {
          'Content-Type': 'multipart/form-data;charset=UTF-8',
          'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
          Encrypt: '0'
        }
      }
    )
    .then((res) => {
      if (res.state === 635) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('上传训练营名单失败:' + err.message)
    })
  return resStatus
}

/**
 * 获取周报记录
 *
 *  @param option 选项,0本周,1全部
 * @param currentPage 当前页码
 * @param pageSize 每页记录数
 * @returns
 */
export const getWeeklyRecord = async (option: number, currentPage: number, pageSize: number) => {
  let resData: weeklyRecordDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get<weeklyRecordDataReq>(`${URL_PREFIX}/getWeeklyRecord`, {
      params: {
        option,
        currentPage,
        pageSize
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data
        const decryptingData = AesUtils.getAESUtils().decrypt(resData.tableData as string)
        resData.tableData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取周报记录失败:' + err.message)
    })
  return resData
}

/**
 * 评价周报
 * @param evaluateWeeklyForm 评价周报表单
 */
export const evaluateWeekly = async (evaluateWeeklyForm: EvaluateWeeklyForm) => {
  let resStatus: boolean = false
  await axios
    .patch(`${URL_PREFIX}/evaluateWeekly`, evaluateWeeklyForm, {
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 625) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('周报评价失败' + err.message)
    })
  return resStatus
}

/**
 * 下载训练营名单模版
 */
export const downloadTrainingListFile = async () => {
  axiosCustom({
    url: `/api/${URL_PREFIX}/downloadTrainingListFile`,
    method: 'get',
    responseType: 'blob'
  })
    .then((res) => {
      const blob = new Blob([res.data], { type: res.headers['content-type'] })
      // 提取文件名
      const fileName = res.headers['content-disposition'].match(/fileName=(.*)/)[1]
      // 创建新的URL并指向File对象或者Blob对象的地址
      const blobURL = window.URL.createObjectURL(blob)
      // 创建a标签，用于跳转至下载链接
      const tempLink = document.createElement('a')
      tempLink.style.display = 'none'
      tempLink.href = blobURL
      const contentDisposition =
        res.headers['content-disposition'] || `attachment;fileName=${fileName}`
      tempLink.setAttribute('download', decodeURI(contentDisposition.split(';')[1].split('=')[1]))
      // 兼容：某些浏览器不支持HTML5的download属性
      if (typeof tempLink.download === 'undefined') {
        tempLink.setAttribute('target', '_blank')
      }
      // 挂载a标签
      document.body.appendChild(tempLink)
      tempLink.click()
      document.body.removeChild(tempLink)
      // 释放blob URL地址
      window.URL.revokeObjectURL(blobURL)
    })
    .catch((err) => {
      ElMessage.error('训练营名单模版文件下载失败:' + err.message)
    })
}

/**
 * 获取人员名单
 * @param pageSize 每页记录数
 * @param pageNumber 页码
 */
export const getPersonnelList = async (pageSize: number, pageNumber: number) => {
  let resData: PersonnelDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get<PersonnelDataReq>(`${URL_PREFIX}/getPersonnelList`, {
      params: {
        pageSize,
        pageNumber
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data
        const decryptingData = AesUtils.getAESUtils().decrypt(resData.tableData as string)
        resData.tableData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取人员名单失败:' + err.message)
    })
  return resData
}

export const deleteUser = async (uid: number) => {
  let resStatus: boolean = false
  await axios
    .delete(URL_PREFIX + '/deleteUser', {
      params: {
        uid
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        ElMessage.success('人员删除成功')
        resStatus = true
      } else {
        ElMessage.warning(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('人员删除失败' + err.message)
    })
  return resStatus
}

export const updateUserInfo = async (userInfo: UpdateUserInfoReqFormAdmin) => {
  let resStatus: boolean = false
  await axios
    .put(URL_PREFIX + '/updateUserInfo', userInfo, {
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        ElMessage.success('用户信息更新成功')
        resStatus = true
      } else {
        ElMessage.warning(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('用户信息更新失败' + err.message)
    })
  return resStatus
}
