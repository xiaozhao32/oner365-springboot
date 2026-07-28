package com.oner365.data.commons.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生产级安全随机数工具类 结合 SecureRandom 和多种安全特性
 * 
 * @author zhaoyong
 * 
 */
public class SecureRandomGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SecureRandomGenerator.class);

    // 用于生成有序ID的原子计数器
    private static final AtomicLong COUNTER = new AtomicLong(0);

    // 机器ID（用于分布式场景）
    private static final long WORKER_ID;

    static {
        // 从系统属性或环境变量获取机器ID
        long workerId = Long.getLong("worker.id", 1);
        String workerIdEnv = System.getenv("WORKER_ID");
        if (workerIdEnv != null) {
            try {
                workerId = Long.parseLong(workerIdEnv);
            } catch (NumberFormatException e) {
                logger.warn("Invalid WORKER_ID env: {}", workerIdEnv);
            }
        }
        WORKER_ID = workerId & 0x3FFL; // 10位机器ID
        logger.info("Worker ID initialized: {}", WORKER_ID);
    }

    private SecureRandomGenerator() {
        super();
    }
    
    // 静态内部类持有单例（JVM 保证线程安全）
    private static class SecureRandomSingleton {
        private static final SecureRandom INSTANCE;
        
        static {
            SecureRandom random = null;
            try {
                // 优先使用 SHA1PRNG
                random = SecureRandom.getInstance("SHA1PRNG");
                // 强制初始化（从系统熵源获取种子）
                random.nextBytes(new byte[1]);
                logger.info("SecureRandom initialized with SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                try {
                    // 降级到 NativePRNG
                    random = SecureRandom.getInstance("NativePRNG");
                    random.nextBytes(new byte[1]);
                    logger.info("SecureRandom initialized with NativePRNG");
                } catch (NoSuchAlgorithmException ex) {
                    try {
                        // 最终降级到默认
                        random = new SecureRandom();
                        random.nextBytes(new byte[1]);
                        logger.info("SecureRandom initialized with default");
                    } catch (Exception ex2) {
                        logger.error("Failed to initialize SecureRandom", ex2);
                        random = new SecureRandom();
                    }
                }
            }
            INSTANCE = random;
        }
    }
    
    /**
     * 获取 SecureRandom 实例
     */
    private static SecureRandom getSecureRandom() {
        return SecureRandomSingleton.INSTANCE;
    }

    // ============ 核心方法 ============

    /**
     * 生成安全的随机整数（0 到 bound-1）
     */
    public static int randomInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        }
        return getSecureRandom().nextInt(bound);
    }

    /**
     * 生成安全的随机整数（指定范围）
     */
    public static int randomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }
        return min + getSecureRandom().nextInt(max - min);
    }

    /**
     * 生成安全的随机长整数
     */
    public static long randomLong() {
        return getSecureRandom().nextLong();
    }

    /**
     * 生成安全的随机长整数（指定范围）
     */
    public static long randomLong(long min, long max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }
        return min + (long) (getSecureRandom().nextDouble() * (max - min));
    }

    /**
     * 生成安全的随机布尔值
     */
    public static boolean randomBoolean() {
        return getSecureRandom().nextBoolean();
    }

    /**
     * 生成安全的随机字节数组
     */
    public static byte[] randomBytes(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        byte[] bytes = new byte[length];
        getSecureRandom().nextBytes(bytes);
        return bytes;
    }

    /**
     * 生成安全的随机字符串（Base64编码）
     */
    public static String randomBase64String(int byteLength) {
        byte[] bytes = randomBytes(byteLength);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * 生成安全的随机数字字符串（验证码）
     */
    public static String randomNumericString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(randomInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成安全的随机字母数字字符串
     */
    public static String randomAlphanumeric(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(randomInt(chars.length())));
        }
        return sb.toString();
    }

    // ============ 高级功能 ============

    /**
     * 生成安全的会话ID（基于时间 + 随机数 + 机器ID） 格式：时间戳(40位) + 机器ID(10位) + 计数器(14位)
     */
    public static String generateSessionId() {
        long timestamp = Instant.now().toEpochMilli();
        long counter = COUNTER.incrementAndGet() & 0x3FFFL; // 14位计数器
        long random = randomLong() & 0xFFFFL; // 16位随机数

        long id = (timestamp << 40) | (WORKER_ID << 30) | (counter << 16) | random;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(longToBytes(id));
    }

    /**
     * 生成密码盐值（安全）
     */
    public static String generateSalt() {
        return randomBase64String(32);
    }

    /**
     * 生成安全的令牌（Token）
     */
    public static String generateToken() {
        // 使用多种熵源组合
        byte[] randomBytes = randomBytes(32);
        long timestamp = System.currentTimeMillis();
        int pid = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();

        // 组合字节数组
        byte[] combined = new byte[40];
        System.arraycopy(randomBytes, 0, combined, 0, 32);
        byte[] timestampBytes = longToBytes(timestamp);
        System.arraycopy(timestampBytes, 0, combined, 32, 8);

        // 加上进程ID的哈希
        byte[] pidBytes = intToBytes(pid);
        System.arraycopy(pidBytes, 0, combined, 36, 4);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);
    }

    /**
     * 生成安全的API密钥
     */
    public static String generateApiKey() {
        // 前缀 + 随机部分
        return "ak_" + randomBase64String(24);
    }

    // ============ 辅助方法 ============

    private static byte[] longToBytes(long value) {
        byte[] bytes = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (value & 0xFFL);
            value >>= 8;
        }
        return bytes;
    }

    private static byte[] intToBytes(int value) {
        byte[] bytes = new byte[4];
        for (int i = 3; i >= 0; i--) {
            bytes[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }

}
