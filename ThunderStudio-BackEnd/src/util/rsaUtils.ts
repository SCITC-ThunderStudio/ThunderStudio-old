import JSEncrypt from 'jsencrypt'

export default class RsaUtils {
  /**
   * 使用RSA加密算法对数据进行加密。
   *
   * @param key RSA公钥
   * @param data 要加密的数据
   * @returns 加密后的字符串
   */
  public static encrypt(key: string, data: string): string | false {
    // 创建加密实例
    const encrypt = new JSEncrypt()

    // 设置公钥
    try {
      encrypt.setPublicKey(key)
    } catch (error) {
      throw new Error('设置公钥失败')
    }

    // 加密数据
    try {
      return encrypt.encrypt(data)
    } catch (error) {
      throw new Error('加密数据失败')
    }
  }
}
