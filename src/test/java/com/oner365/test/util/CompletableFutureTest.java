package com.oner365.test.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class CompletableFutureTest extends BaseUtilsTest {

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAXIMUM_POOL_SIZE = 10;

    private static final int CAPACITY = 10;

    private static final long KEEP_ALIVE_TIME = 0L;

    private static final String THREAD_NAME_FORMAT = "demo-pool-%d";

    @Test
    void test() {
        // 创建线程
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_FORMAT).build();
        ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(CAPACITY), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        completableFutureTest(executorService);

        executorService.shutdown();
    }

    /**
     * completableFuture
     * @param executorService service
     */
    private void completableFutureTest(ExecutorService executorService) {
        long start = System.currentTimeMillis();
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            logger.info("ThreadA:{}", Thread.currentThread().getName());
            return "商品详情";
        }, executorService);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            logger.info("ThreadB:{}", Thread.currentThread().getName());
            return "卖家信息";
        }, executorService);

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureA, futureB);
        allFuture.join();

        logger.info("Join:{},{}", futureA.join(), futureB.join());
        logger.info("总耗时: {}", (System.currentTimeMillis() - start));
    }

}
