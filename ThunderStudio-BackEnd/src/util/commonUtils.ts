import axios, { getRSAPublicKey } from '@/server'
import { usePublicStore, useRouterListStore, useUserInfoStore } from '@/stores'
import { ElMessage } from 'element-plus'
import RsaUtils from '@/util/rsaUtils'
import AesUtils from '@/util/aesUtils'
import { unWatchWindowSize } from '@/util/windowSizeUtils'

/**
 * 获取指定长度的随机字符串
 *
 * @param length 字符串长度
 * @returns 生成随机字符串
 */
export const generateRandomString = (length: number): string => {
  let result = ''
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * characters.length))
  }
  return result
}

/**
 * 处理登出
 */
export const handleLoginOut = (): void => {
  useUserInfoStore().$reset()
  useRouterListStore().removeRouterList()
  window.localStorage.removeItem(import.meta.env.VITE_SESSION_ID)
}

/**
 * 随机生成UUID
 */
export const randomUUID = () => {
  let uuidValue = '',
    k,
    randomValue
  for (k = 0; k < 32; k++) {
    randomValue = (Math.random() * 16) | 0

    if (k == 8 || k == 12 || k == 16 || k == 20) {
      uuidValue += '-'
    }
    uuidValue += (k == 12 ? 4 : k == 16 ? (randomValue & 3) | 8 : randomValue).toString(16)
  }
  return uuidValue.toUpperCase()
}

/**
 * URL转File对象
 * @param pictureUrl 图片链接
 */
export const pictureUrlToFileObject = async (pictureUrl: string) => {
  let resData: File = null
  const fileType: string = pictureUrl.slice(pictureUrl.lastIndexOf('.') + 1, pictureUrl.length)
  const type: string = fileType === 'jpg' ? 'image/jpeg' : 'image/png'
  await axios
    .get(pictureUrl, { responseType: 'arraybuffer' })
    .then((res) => {
      const blob = new Blob([res as any], {
        type: type
      })
      resData = new File([blob], randomUUID + '.' + fileType, {
        type: type
      })
    })
    .catch((err) => {
      ElMessage.error('将图像URL转换为文件对象时出错:' + err.message)
    })
  return resData
}

/**
 * 初始化密钥
 */
export const initKey = async () => {
  const publicStore = usePublicStore()
  const aes = AesUtils.getAESUtils()
  await getRSAPublicKey().then((res) => {
    if (res !== null) {
      console.debug('密钥初始化')
      publicStore.rsaPublicKey = res
      publicStore.aesKey_RSA = RsaUtils.encrypt(publicStore.rsaPublicKey, aes.key) as string
      publicStore.aesIv_RSA = RsaUtils.encrypt(publicStore.rsaPublicKey, aes.iv) as string
    }
  })
}
