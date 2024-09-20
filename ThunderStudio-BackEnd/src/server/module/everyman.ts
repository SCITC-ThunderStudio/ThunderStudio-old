import axios from '@/server'
import { usePublicStore } from '@/stores'
import AesUtils from '@/util/aesUtils'
import type { ClockInDataReq, WeeklyDataReq, weeklyDataReqForm } from '@/util/typeUtils'
import { ElMessage } from 'element-plus'

// 请求前缀
const URL_PREFIX: string = '/everyMan/'

/**
 * 上传打卡记录
 * @param pictureFile 图片文件
 * @param number 做题数
 * @param logs 日志
 */
export const uploadClockIn = async (pictureFile: File, number: number, logs: string) => {
  let resStatus: boolean = false
  await axios
    .post(
      URL_PREFIX + 'uploadClockIn',
      { pictureFile, number, logs },
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
          Encrypt: '0'
        }
      }
    )
    .then((res) => {
      if (res.state === 650) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('打卡记录提交失败:' + err.message)
    })
  return resStatus
}

/**
 * 更新打卡记录
 * @param recordsId 打卡记录id
 * @param pictureFile 图片文件
 * @param number 做题数
 * @param logs 日志
 */
export const updateClockIn = async (
  recordsId: number,
  pictureFile: File,
  number: number,
  logs: string
) => {
  let resStatus: boolean = false
  await axios
    .put(
      URL_PREFIX + 'updateClockIn',
      { recordsId, pictureFile, number, logs },
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
          Encrypt: '0'
        }
      }
    )
    .then((res) => {
      if (res.state === 654) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('打卡记录更新失败' + err.message)
    })
  return resStatus
}

/**
 * 删除打卡记录
 * @param recordsId 打卡记录id
 */
export const deleteClockIn = async (recordsId: number) => {
  let resStatus: boolean = false
  await axios
    .delete(URL_PREFIX + 'deleteClockIn', {
      params: {
        recordsId
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 652) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('打卡记录删除失败' + err.message)
    })
  return resStatus
}

/**
 * 获取个人打卡记录
 * @param currentPage 当前页码
 * @param pageSize 指定展示多少条
 */
export const getClockInList = async (currentPage: number, pageSize: number) => {
  let resData: ClockInDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get<ClockInDataReq>(URL_PREFIX + 'getClockInList', {
      params: {
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
    .then(async (res) => {
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
 * 上传周报文件
 * @param weeklyFile 周报文件
 */
export const uploadWeeklyFile = async (weeklyFile: File) => {
  let resStatus: boolean = false
  await axios
    .post(
      URL_PREFIX + 'uploadWeeklyFile',
      { weeklyFile },
      {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
          Encrypt: '0'
        }
      }
    )
    .then((res) => {
      if (res.state === 640) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('周报上传失败:' + err.message)
    })
  return resStatus
}

/**
 * 上传周报内容
 * @param weeklyDataForm 周报表单
 */
export const uploadWeeklyContent = async (weeklyDataForm: weeklyDataReqForm) => {
  let resStatus: boolean = false
  await axios
    .post(URL_PREFIX + 'uploadWeeklyContent', weeklyDataForm, {
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 640) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('周报上传失败:' + err.message)
    })
  return resStatus
}

/**
 * 删除周报
 * @param weeklyId
 */
export const deleteWeekly = async (weeklyId: number) => {
  let resStatus: boolean = false
  await axios
    .delete(URL_PREFIX + 'deleteWeekly', {
      params: {
        weeklyId
      },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 642) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('周报删除失败:' + err.message)
    })
  return resStatus
}

/**
 * 获取个人周报记录
 * @param currentPage 当前页码
 * @param pageSize 指定展示多少条
 */
export const getWeeklyList = async (currentPage: number, pageSize: number) => {
  let resData: WeeklyDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get(URL_PREFIX + 'getWeeklyList', {
      params: {
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
        resData = res.data as WeeklyDataReq
        const decryptingData = AesUtils.getAESUtils().decrypt(resData.tableData as string)
        resData.tableData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取个人周报记录失败:' + err.message)
    })
  return resData
}
