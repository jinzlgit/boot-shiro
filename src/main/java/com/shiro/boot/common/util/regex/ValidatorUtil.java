package com.shiro.boot.common.util.regex;

/**
 * 校验器：
 *      利用正则表达式校验 邮箱、手机号、身份证号码 等；
 *
 * @author jzl
 * @date 2020/3/30 13:41
 */
public class ValidatorUtil {

    /**
     * 正则表达式：验证用户名（不包含中文和特殊字符）
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
    /**
     * 正则表达式：验证密码（不包含特殊字符）
     */
    public static final String REGEX_PASSWORD = "^[0-9a-zA-Z]{6,16}$";
    /**
     * 正则表达式：验证手机号
     * 第一位必为 1，第二位为 3 或 5 或 8
     */
    public static final String REGEX_MOBILE = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$))";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([0-9a-zA-Z]+[-|\\.]?)+[0-9a-zA-Z]@([0-9a-zA-Z]+(-[0-9a-zA-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 正则表达式：验证汉字（1-9个汉字）
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";
    /**
     * 正则表达式：验证 URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证 IP
     */
    public static final String REGEX_IP = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

}
