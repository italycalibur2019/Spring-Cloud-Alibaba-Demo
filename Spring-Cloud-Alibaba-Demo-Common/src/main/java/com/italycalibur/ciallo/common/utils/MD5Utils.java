package com.italycalibur.ciallo.common.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description: MD5工具类
 * @author dhr
 * @date 2025-02-11 11:50:18
 * @version 1.0
 */ 
public class MD5Utils {

    public static final String MD5_SALT = "italycalibur";//加密盐

    /**
     * md5加密，自定义盐
     * @param source 待加密字符串
     * @param salt 盐
     * @return 加密后的字符串
     */
    public static String md5Encode(String source, String salt) {
        return DigestUtils.md5DigestAsHex((source + salt).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * md5加密，默认盐
     * @param source 待加密字符串
     * @return 加密后的字符串
     */
    public static String md5Encode(String source) {
        return md5Encode(source, MD5_SALT);
    }

    /**
     * md5"解密"，自定义盐
     * @param source 待解密字符串
     * @param salt 盐
     * @param encode 待比较字符串
     * @return 解密结果
     */
    public static boolean md5Decode(String source, String salt, String encode) {
        return md5Encode(source, salt).equals(encode);
    }

    /**
     * md5"解密"，默认盐
     * @param source 待解密字符串
     * @param encode 待比较字符串
     * @return 解密结果
     */
    public static boolean md5Decode(String source, String encode) {
        return md5Decode(source, MD5_SALT, encode);
    }

    // 测试加密解密
    public static void main(String[] args) {
        // 查看加密效果
        System.out.println(md5Encode("abc1234."));
        // result: false
        System.out.println(md5Decode("123456", "654321"));
        // result: true
        System.out.println(md5Decode("123456", md5Encode("123456")));
    }
}
