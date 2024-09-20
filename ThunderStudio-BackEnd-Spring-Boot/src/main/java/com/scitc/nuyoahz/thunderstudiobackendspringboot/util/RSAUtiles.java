package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * RSA加密工具
 *
 * @author Nuyoahz
 * @date 2024/1/9 12:23
 */
public class RSAUtiles {
    private static final RSA RSA = new RSA();

    /**
     * 获取私钥
     *
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/09 12:29
     */
    public static String getPrivateKey() {
        return RSA.getPrivateKeyBase64();
    }

    /**
     * 获取公钥
     *
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/09 12:30
     */
    public static String getPublicKey() {
        return RSA.getPublicKeyBase64();
    }

    /**
     * 解密
     *
     * @param decryptData 待解密的数据
     * @return byte[]
     * @author Nuyoahz
     * @date 2024/01/09 12:39
     */
    public static String decrypt(String decryptData) {
        return new String(RSA.decrypt(decryptData, KeyType.PrivateKey), CharsetUtil.CHARSET_UTF_8);
    }
}