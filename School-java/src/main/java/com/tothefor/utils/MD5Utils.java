package com.tothefor.utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.util.DigestUtils;

/**
 * MD5加密工具类
 * @Author Steven
 * @Date 2022/08/31 13:28
 
 */


public class MD5Utils {
    public static void main(String[] args) {
//        System.out.println(getMD5("123456"));8
        String s = "ba bai";
        System.out.println(DigestUtils.md5DigestAsHex(s.getBytes()));
    }

    public static String getMD5(String password){

        /**
         * 加密处理第一次
         */
        String pwd = "abc123";
        pwd+=password;
        pwd+="def123";
        String s = DigestUtils.md5DigestAsHex(pwd.getBytes());

        /**
         * 加密处理第二次
         */
        String Dpwd = "ghi123";
        Dpwd+=s;
        Dpwd+= "jkl123";
        String ss = DigestUtils.md5DigestAsHex(Dpwd.getBytes());
        return ss;
    }
}
