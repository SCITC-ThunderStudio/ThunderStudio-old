import CryptoJS from 'crypto-js'
import { generateRandomString } from '@/util/commonUtils'

export default class AesUtils {
  /**
   * AES实例
   * @private
   */
  private static _aesUtils: null | AesUtils = null

  /**
   * AES密钥
   * @private
   */
  private readonly _key: string = ''

  /**
   * AES偏移量
   * @private
   */
  private readonly _iv: string = ''

  /**
   * AES密钥WordArray
   * @private
   */
  private readonly keyUtf8: any

  /**
   * AES偏移量WordArray
   * @private
   */
  private readonly ivUtf8: any

  constructor() {
    console.debug('AES创建实例')
    this._key = generateRandomString(32)
    this._iv = generateRandomString(16)
    this.keyUtf8 = CryptoJS.enc.Utf8.parse(this._key)
    this.ivUtf8 = CryptoJS.enc.Utf8.parse(this._iv)
  }

  /**
   * 获取AES密钥
   */
  get key(): string {
    return this._key
  }

  /**
   * 获取AES偏移量
   */
  get iv(): string {
    return this._iv
  }

  /**
   * 获取AES实例
   * @returns AES实例
   */
  public static getAESUtils = () => {
    if (AesUtils._aesUtils === null) {
      AesUtils._aesUtils = new AesUtils()
      return AesUtils._aesUtils
    }
    return AesUtils._aesUtils
  }

  /**
   * 加密
   *
   * @param encryptedData 待加密的数据
   * @returns Base64编码的加密字符串
   */
  public encrypt(encryptedData: any): any {
    let encrypted

    // 将对象格式的数据转成JSON字符串
    if (typeof encryptedData === 'object') {
      encryptedData = JSON.stringify(encryptedData)
    }

    try {
      encrypted = CryptoJS.AES.encrypt(encryptedData, this.keyUtf8, {
        iv: this.ivUtf8,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
      })
    } catch (error) {
      throw new Error('数据加密失败')
    }

    return encrypted.ciphertext.toString(CryptoJS.enc.Base64)
  }

  /**
   * 解密
   *
   * @param decryptingData 待解密的数据
   * @returns 解密后的数据
   */
  public decrypt(decryptingData: string): any {
    let decrypted

    // 解析Base64编码的解密数据
    const wordArray = CryptoJS.enc.Base64.parse(decryptingData)
    const ciphertext = CryptoJS.enc.Base64.stringify(wordArray)

    try {
      // 解密数据
      decrypted = CryptoJS.AES.decrypt(ciphertext, this.keyUtf8, {
        iv: this.ivUtf8,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
      })
    } catch (error) {
      throw new Error('数据解密失败')
    }

    return decrypted.toString(CryptoJS.enc.Utf8)
  }
}
