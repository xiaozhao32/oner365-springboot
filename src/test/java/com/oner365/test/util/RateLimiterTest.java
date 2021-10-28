package com.oner365.test.util;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class RateLimiterTest {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 10;
    private static final int CAPACITY = 1024;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final String THREAD_NAME_FORMAT = "demo-pool-%d";

    private static final int TOKEN_NUMBER = 10;

    @Test
    void test() {
    	Assertions.assertEquals("RateLimiterTest", RateLimiterTest.class.getSimpleName());
    }

    public static void main(String[] args) {
        // 创建线程
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_FORMAT).build();
        ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(CAPACITY), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        rateLimiterTest(executorService);
        executorService.shutdown();
    }

    /**
     * 每秒产生 10 个令牌（每 100 ms 产生一个）
     *
     * @param executorService service
     */
    private static void rateLimiterTest(ExecutorService executorService) {
        RateLimiter rt = RateLimiter.create(TOKEN_NUMBER);
        for (int i = 0; i <= TOKEN_NUMBER; i++) {
            executorService.execute(() -> {
                double d = rt.acquire();
                System.out.println("Thread:" + Thread.currentThread().getName());
                System.out.println("正常执行方法，ts:" + Instant.now() + " -- " + d);
            });
        }
    }
}
