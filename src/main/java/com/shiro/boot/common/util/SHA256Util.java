package com.shiro.boot.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Sha-256加密工具
 * @author jzl
 * @date 2020/3/16 11:36
 */
public class SHA256Util {
    /**
     * 私有构造器
     */
    public SHA256Util() {
    }

    /**
     * 加密算法
     */
    public static final String HASH_ALGORITHM_NAME = "SHA-256";

    /**
     * 循环次数
     */
    public static final int HASH_ITERATIONS = 15;

    /**
     * 执行加密
     * @param password
     * @param salt
     * @return
     */
    public static String sha256(String password, String salt){
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }
}
