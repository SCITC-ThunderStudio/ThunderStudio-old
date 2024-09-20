import axios from '@/server'
import { usePublicStore } from '@/stores'
import AesUtils from '@/util/aesUtils'
import type { AnnouncementTableDaraReq, TraineeDataReq } from '@/util/typeUtils'
import { ElMessage } from 'element-plus'

// 请求前缀
const URL_PREFIX: string = '/personInCharge/'

/**
 *
 * @param currentPage 当前页码
 * @param pageSize 指定展示多少条
 * @returns
 */
export const getTrainee = async (currentPage: number, pageSize: number) => {
  let resData: TraineeDataReq = null
  const publicStore = usePublicStore()
  await axios
    .get(URL_PREFIX + 'getTrainee', {
      params: { currentPage, pageSize },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Key: publicStore.aesKey_RSA,
        Iv: publicStore.aesIv_RSA,
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data as TraineeDataReq
        const decryptingData = AesUtils.getAESUtils().decrypt(resData.tableData as string)
        resData.tableData = JSON.parse(decryptingData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取个人负责人员失败:' + err.message)
    })
  return resData
}

export const getAnnouncementAllList = async (currentPage: number, pageSize: number) => {
  let resData: AnnouncementTableDaraReq = null
  await axios
    .get(URL_PREFIX + 'getAnnouncementAllList', {
      params: { currentPage, pageSize },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 200) {
        resData = res.data as AnnouncementTableDaraReq
        console.debug('全部公告列表数据:', resData)
      } else {
        ElMessage.error(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('获取全部公告列表失败:' + err.message)
    })
  return resData
}

export const announcement = async (uid: number, title: string, content: string) => {
  let resStatus: boolean = false
  await axios
    .post(
      URL_PREFIX + 'announcement',
      { uid, title, content },
      {
        headers: {
          'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
          Encrypt: '0'
        }
      }
    )
    .then((res) => {
      if (res.state === 675) {
        resStatus = true
        ElMessage.success(res.message)
      } else {
        ElMessage.warning(res.message)
      }
    })
    .catch((err) => {
      ElMessage.error('发布公告失败', err.message)
    })
  return resStatus
}

export const deleteAnnouncement = async (announcementId: number) => {
  let resStatus: boolean = false
  await axios
    .delete(URL_PREFIX + 'deleteAnnouncement', {
      params: { announcementId },
      headers: {
        'Session-Id': window.localStorage.getItem(import.meta.env.VITE_SESSION_ID),
        Encrypt: '0'
      }
    })
    .then((res) => {
      if (res.state === 678) {
        ElMessage.success(res.message)
        resStatus = true
      } else {
        ElMessage.warning(res.message)
      }
    })
    .catch((res) => {
      ElMessage.error('公告删除失败：', res.message)
    })
  return resStatus
}
