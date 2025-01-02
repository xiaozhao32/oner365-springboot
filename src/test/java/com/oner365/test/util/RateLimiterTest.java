package com.oner365.test.util;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 工具类测试
 *
 * @author zhaoyong
 */
class RateLimiterTest extends BaseUtilsTest {

  private static final int CORE_POOL_SIZE = 5;
  private static final int MAXIMUM_POOL_SIZE = 10;
  private static final int CAPACITY = 10;
  private static final long KEEP_ALIVE_TIME = 0L;
  private static final String THREAD_NAME_FORMAT = "demo-pool-%d";

  private static final int TOKEN_NUMBER = 10;

  @Test
  void test() {
    // 创建线程
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_FORMAT).build();
    ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(CAPACITY), namedThreadFactory,
        new ThreadPoolExecutor.AbortPolicy());

    // 每秒产生 10 个令牌（每 100 ms 产生一个）
    RateLimiter rt = RateLimiter.create(TOKEN_NUMBER);
    Assertions.assertNotNull(rt);
    IntStream.rangeClosed(0, TOKEN_NUMBER).<Runnable>mapToObj(i -> () -> {
      double d = rt.acquire();
      logger.info("Thread:{}", Thread.currentThread().getName());
      logger.info("正常执行方法，ts:{} - {}", Instant.now(), d);
    }).forEach(executorService::execute);
    executorService.shutdown();
  }

}
