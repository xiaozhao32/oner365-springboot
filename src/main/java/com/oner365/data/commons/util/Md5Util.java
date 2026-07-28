package com.oner365.data.commons.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Md5加密工具类
 *
 * @author zhaoyong
 */
public class Md5Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

    private static final ThreadLocal<Md5Util> LOCAL = new ThreadLocal<>();

    /**
     * Generate constructor
     */
    private Md5Util() {
    }

    public void remove() {
        LOCAL.remove();
    }

    /**
     * 获取单例
     * @return Md5Util
     */
    public static Md5Util getInstance() {
        Md5Util instance = LOCAL.get();
        if (instance == null) {
            instance = new Md5Util();
            LOCAL.set(instance);
        }
        return instance;
    }

    /**
     * md5
     * @param data 字符串
     * @return String
     */
    public String getMd5(String data) {
        String result = null;
        if (data != null) {
            result = getDigest(data, "MD5");
        }
        return result;
    }

    /**
     * md5
     * @param data 字符串
     * @return String
     */
    public String getMd5(byte[] data) {
        String result = null;
        if (data != null) {
            result = getDigest(data, "MD5");
        }
        return result;
    }

    /**
     * sha1
     * @param data 字符串
     * @return String
     */
    public String getSha1(String data) {
        String result = null;
        if (data != null) {
            result = getDigest(data, "SHA1");
        }
        return result;
    }

    private String getDigest(String str, String instance) {
        return getDigest(str.getBytes(), instance);
    }

    private String getDigest(byte[] str, String instance) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(instance);
            byte[] bytes = messageDigest.digest(str);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(Integer.toHexString(bt));
            }
            return stringBuilder.toString();
        }
        catch (Exception e) {
            LOGGER.error("getMd5 error", e);
        }
        return null;
    }

}
