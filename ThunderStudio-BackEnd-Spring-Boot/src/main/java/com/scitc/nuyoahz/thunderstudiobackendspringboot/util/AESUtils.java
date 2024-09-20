package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import io.netty.util.CharsetUtil;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * AES加解密工具
 *
 * @author Nuyoahz
 * @date 2024/1/11 14:01
 */
public class AESUtils {
    private final AES aes;

    public AESUtils(String key, String iv) {
        byte[] aesKey = key.getBytes(UTF_8);
        byte[] aesIv = iv.getBytes(UTF_8);
        aes = new AES(Mode.CBC, Padding.ZeroPadding, aesKey, aesIv);
    }

    /**
     * 加密密码
     *
     * @param encryptData 待加密的数据
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/11 14:01
     */
    public String encrypt(String encryptData) {
        return aes.encryptBase64(encryptData).trim();
    }

    /**
     * 解密密码
     *
     * @param decryptData 待解密的数据
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/11 14:02
     */
    public String decrypt(String decryptData) {
        return aes.decryptStr(decryptData);
    }
}