package com.scitc.nuyoahz.thunderstudiobackendspringboot.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;

/**
 * 电子邮件工具
 *
 * @author Nuyoahz
 * @date 2024/1/11 15:37
 */
public class EmailUtils {
    /**
     * 设置邮件模版
     *
     * @param verificationCode 验证码
     * @return java.lang.String
     * @author Nuyoahz
     * @date 2024/01/11 15:38
     */
    public static String setEmailTemplate(String verificationCode) {
        return """
                <meta charset="utf-8">
                    <table width="100%">
                        <tr>
                            <td style="width: 100%;">
                                <center>
                                    <table class="content-wrap" style="margin: 0px auto; width: 600px;">
                                        <tr>
                                            <td
                                                style="margin: 0px auto; overflow: hidden; padding: 0px; border: 1px solid rgb(238, 238, 238);">
                                                <!---->
                                                <div tindex="1" style="margin: 0px auto; max-width: 600px;">
                                                    <table align="center" border="0" cellpadding="0" cellspacing="0"
                                                        style="background-image: url(&quot;http://47.109.23.86:8085/picture/CB93E57D-CAE2-443D-9CD9-4DD62A307D21.jpg&quot;); background-size: cover; background-position: 1% 50%; background-repeat: no-repeat no-repeat;">
                                                        <tbody>
                                                            <tr>
                                                                <td
                                                                    style="direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;">
                                                                    <table width="100%" border="0" cellpadding="0" cellspacing="0"
                                                                        style="vertical-align: top;">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td class="twoColumn column1"
                                                                                    style="width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;">
                                                                                    <div class="full"
                                                                                        style="margin: 0px auto; max-width: 600px;">
                                                                                        <table align="center" border="0" cellpadding="0"
                                                                                            cellspacing="0" class="fullTable"
                                                                                            style="width: 300px;">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <td class="fullTd"
                                                                                                        style="direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;">
                                                                                                        <div
                                                                                                            style="display: inline-block; vertical-align: top; width: 100%;">
                                                                                                            <table border="0"
                                                                                                                cellpadding="0"
                                                                                                                cellspacing="0" width="100%"
                                                                                                                style="vertical-align: top;">
                                                                                                                <tr>
                                                                                                                    <td
                                                                                                                        style="font-size: 0px; word-break: break-word; width: 260px; text-align: left; padding: 26px 20px;">
                                                                                                                        <div>
                                                                                                                            <img height="auto"
                                                                                                                                alt=""
                                                                                                                                width="82"
                                                                                                                                src="http://47.109.23.86:8085/picture/134A0C50-9D76-4438-BFA9-FFED1E1C4B19.png"
                                                                                                                                style="box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;">
                                                                                                                        </div>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                    </div>
                                                                                </td>
                                                                                <td class="twoColumn column2"
                                                                                    style="width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 15px;">
                                                                                    <div class="full"
                                                                                        style="margin: 0px auto; max-width: 600px;">
                                                                                        <table align="center" border="0" cellpadding="0"
                                                                                            cellspacing="0" class="fullTable"
                                                                                            style="width: 300px;">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <td class="fullTd"
                                                                                                        style="direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-size: 100px auto; background-position: 10% 50%; background-repeat: no-repeat no-repeat;">
                                                                                                        <table border="0" cellpadding="0"
                                                                                                            cellspacing="0" width="100%"
                                                                                                            style="vertical-align: top;">
                                                                                                            <tr>
                                                                                                                <td align="left"
                                                                                                                    style="font-size: 0px; padding: 26px 20px;">
                                                                                                                    <div class="text"
                                                                                                                        style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 1.5; color: rgb(102, 102, 102); font-size: 15px; font-weight: normal;">
                                                                                                                        <div>
                                                                                                                            <p
                                                                                                                                style="text-align: center; word-break: break-word; line-height: 1.5; font-size: 15px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 35px; color: #ecf0f1;">雷霆工作室</span>
                                                                                                                            </p>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                        </table>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div tindex="2" style="margin: 0px auto; max-width: 600px;">
                                                    <table align="center" border="0" cellpadding="0" cellspacing="0"
                                                        style="background-image: url(&quot;&quot;); background-size: 100px auto; background-position: 1% 50%; background-repeat: no-repeat no-repeat;">
                                                        <tbody>
                                                            <tr>
                                                                <td
                                                                    style="direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;">
                                                                    <table width="100%" border="0" cellpadding="0" cellspacing="0"
                                                                        style="vertical-align: top;">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td class="oneColumn column1"
                                                                                    style="width: 100%; max-width: 100%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;">
                                                                                    <div class="full"
                                                                                        style="margin: 0px auto; max-width: 600px;">
                                                                                        <table align="center" border="0" cellpadding="0"
                                                                                            cellspacing="0" class="fullTable"
                                                                                            style="width: 600px;">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <td class="fullTd"
                                                                                                        style="direction: ltr; width: 600px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-size: 100px auto; background-position: 10% 50%; background-repeat: no-repeat no-repeat;">
                                                                                                        <table border="0" cellpadding="0"
                                                                                                            cellspacing="0" width="100%"
                                                                                                            style="vertical-align: top;">
                                                                                                            <tr>
                                                                                                                <td align="left"
                                                                                                                    style="font-size: 0px; padding: 20px;">
                                                                                                                    <div class="text"
                                                                                                                        style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 1.6; font-size: 14px; font-weight: normal;">
                                                                                                                        <div>
                                                                                                                            <p
                                                                                                                                style="text-align: left; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 20px; font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif; color: #000000;">您好</span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: left; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                &nbsp;</p>
                                                                                                                            <p
                                                                                                                                style="text-align: left; line-height: 1.6; padding-left: 40px; word-break: break-word; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 14px; color: #7e8c8d;"><span
                                                                                                                                        style="color: #000000;">您的验证码为</span>:</span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: center; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="color: #000000; font-size: 36px;"><strong><span
                                                                                                                                            style="text-decoration: underline;">
                    """ + verificationCode + """
                    </span></strong></span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: center; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                &nbsp;</p>
                                                                                                                            <p
                                                                                                                                style="text-align: center; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 14px;">验证码在<span
                                                                                                                                        style="color: #e03e2d;">5分钟</span>内有效，请根据页面提示进行输入</span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: center; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                &nbsp;</p>
                                                                                                                            <p
                                                                                                                                style="text-align: right; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 14px;"><span
                                                                                                                                        style="caret-color: #666666; color: #666666; font-family: 'lucida Grande', Verdana; text-align: right; background-color: #ffffff;">ThunderStudio</span></span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: right; word-break: break-word; line-height: 1.6; font-size: 14px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 14px;">
                                                                                                                                    <a style="outline: none; cursor: pointer; color: rgb(30, 84, 148); font-family: &quot;lucida Grande&quot;, Verdana; text-decoration: underline; font-weight: normal;"
                                                                                                                                        href="http://47.109.23.86:8085"
                                                                                                                                        target="_blank"
                                                                                                                                        rel="noopener">雷霆工作室
                                                                                                                                    </a>
                                                                                                                                </span>
                                                                                                                            </p>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                        </table>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div tindex="3" style="margin: 0px auto; max-width: 600px;">
                                                    <table align="center" border="0" cellpadding="0" cellspacing="0"
                                                        style="background-color: rgb(3, 3, 3); background-image: url(&quot;&quot;); background-size: 100px auto; background-position: 1% 50%; background-repeat: no-repeat no-repeat;">
                                                        <tbody>
                                                            <tr>
                                                                <td
                                                                    style="direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;">
                                                                    <table width="100%" border="0" cellpadding="0" cellspacing="0"
                                                                        style="vertical-align: top;">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td class="oneColumn column1"
                                                                                    style="width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;">
                                                                                    <div class="full"
                                                                                        style="margin: 0px auto; max-width: 600px;">
                                                                                        <table align="center" border="0" cellpadding="0"
                                                                                            cellspacing="0" class="fullTable"
                                                                                            style="width: 300px;">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <td class="fullTd"
                                                                                                        style="direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-size: 100px auto; background-position: 10% 50%; background-repeat: no-repeat no-repeat;">
                                                                                                        <table border="0" cellpadding="0"
                                                                                                            cellspacing="0" width="100%"
                                                                                                            style="vertical-align: top;">
                                                                                                            <tr>
                                                                                                                <td align="left"
                                                                                                                    style="font-size: 0px; padding: 10px 20px 20px;">
                                                                                                                    <div class="text"
                                                                                                                        style="font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 1.2; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;">
                                                                                                                        <div>
                                                                                                                            <p
                                                                                                                                style="text-align: left; word-break: break-word; line-height: 1.2; font-size: 12px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-size: 14px; font-family: 'Microsoft YaHei', 'Helvetica Neue', 'PingFang SC', sans-serif;">此为系统邮件，请勿回复</span>
                                                                                                                            </p>
                                                                                                                            <p
                                                                                                                                style="text-align: left; word-break: break-word; line-height: 1.2; font-size: 12px; margin: 0px;">
                                                                                                                                &nbsp;</p>
                                                                                                                            <p
                                                                                                                                style="text-align: left; word-break: break-word; line-height: 1.2; font-size: 12px; margin: 0px;">
                                                                                                                                <span
                                                                                                                                    style="font-family: 'Microsoft YaHei', 'Helvetica Neue', 'PingFang SC', sans-serif; color: #ecf0f1; font-size: 14px;"><span
                                                                                                                                        style="font-family: Microsoft YaHei, Helvetica Neue, PingFang SC, sans-serif;">技术支持：Nuyoahz</span></span>
                                                                                                                            </p>
                                                                                                                        </div>
                                                                                                                    </div>
                                                                                                                </td>
                                                                                                            </tr>
                                                                                                        </table>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                    </div>
                                                                                </td>
                                                                                <td class="oneColumn column2"
                                                                                    style="width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;">
                                                                                    <div class="full"
                                                                                        style="margin: 0px auto; max-width: 600px;">
                                                                                        <table align="center" border="0" cellpadding="0"
                                                                                            cellspacing="0" class="fullTable"
                                                                                            style="width: 300px;">
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <td class="fullTd"
                                                                                                        style="direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;">
                                                                                                        <div
                                                                                                            style="display: inline-block; vertical-align: top; width: 100%;">
                                                                                                            <table border="0"
                                                                                                                cellpadding="0"
                                                                                                                cellspacing="0" width="100%"
                                                                                                                style="vertical-align: top;">
                                                                                                                <tr>
                                                                                                                    <td
                                                                                                                        style="font-size: 0px; word-break: break-word; width: 280px; text-align: right; padding: 10px 10px 11px;">
                                                                                                                        <div><img
                                                                                                                                height="auto"
                                                                                                                                width="145"
                                                                                                                                src="http://47.109.23.86:8085/picture/3035D438-206B-4C4E-95F6-4C9E912C1FB7.png"
                                                                                                                                style="box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;">
                                                                                                                        </div>
                                                                                                                    </td>
                                                                                                                </tr>
                                                                                                            </table>
                                                                                                        </div>
                                                                                                    </td>
                                                                                                </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                    </div>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                            </td>
                        </tr>
                    </table>
                </meta>
                """;
    }

    /**
     * 发送邮件
     *
     * @param uUid  用户id
     * @param email 邮箱
     * @author Nuyoahz
     * @date 2024/01/11 15:39
     */
    public static void sendEmail(String uUid, String email) {
        // 生成6位随机验证码
        String captcha = RandomUtil.randomString(6);
        // 验证码有效期5分钟
        RedisUtils.setKey(uUid, captcha, 300L);
        MailUtil.send(email, "雷霆工作室", setEmailTemplate(captcha), true);
    }
}
