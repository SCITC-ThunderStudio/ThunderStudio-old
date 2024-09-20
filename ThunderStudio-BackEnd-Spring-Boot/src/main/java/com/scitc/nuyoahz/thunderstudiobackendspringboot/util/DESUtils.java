package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import io.netty.util.CharsetUtil;

/**
 * DES加密工具
 *
 * @author Nuyoahz
 * @date 2024/04/08 14:55
 */
public class DESUtils {
    /**
     * 密钥
     */
    private static final byte[] KEY = "qGyhxTnVnMHD80x9rgaHUsJyHhK1AYL7".getBytes();

    /**
     * DES实例
     */
    private static final SymmetricCrypto DES = new SymmetricCrypto(SymmetricAlgorithm.DESede, KEY);

    /**
     * 加密
     *
     * @param encryptData 待加密数据
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/04/08 14:57
     */
    public static String encrypt(String encryptData) {
        return DES.encryptBase64(encryptData, CharsetUtil.UTF_8);
    }

    /**
     * 解密
     *
     * @param decryptData 待解密数据
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/04/08 14:59
     */
    public static String decrypt(String decryptData) {
        return DES.decryptStr(decryptData, CharsetUtil.UTF_8);
    }
}