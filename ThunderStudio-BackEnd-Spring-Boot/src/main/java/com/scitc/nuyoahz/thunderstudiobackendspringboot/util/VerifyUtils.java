package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import com.scitc.nuyoahz.thunderstudiobackendspringboot.constant.SystemConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式验证工具
 *
 * @author Nuyoahz
 * @date 2024/1/11 15:24
 */
public class VerifyUtils {
    /**
     * 验证邮箱格式
     *
     * @param email 待验证邮箱
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/01/11
     */
    public static Boolean isEmail(String email) {
        Pattern passwordPattern = Pattern.compile(SystemConstant.EMAIL_FORMAT);
        Matcher matcher = passwordPattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证密码格式
     *
     * @param password 待验证的密码
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/01/12
     */
    public static Boolean isPassword(String password) {
        Pattern passwordPattern = Pattern.compile(SystemConstant.PASSWORD_FORMAT);
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 验证图片格式
     *
     * @param imgType 待验证的图片格式
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/03/18
     */
    public static Boolean isImg(String imgType) {
        return SystemConstant.IMAGE_FORMAT.contains(imgType);
    }

    /**
     * 验证Excel文件格式
     *
     * @param excelType 带验证的Excel文件
     * @return Boolean
     * @author Nuyoahz
     * @date 2024/5/11
     */
    public static Boolean isExcel(String excelType) {
        return SystemConstant.EXCEL_FORMAT.contains(excelType);
    }
}