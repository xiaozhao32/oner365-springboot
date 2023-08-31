package com.oner365.test.service.common;

import java.util.stream.IntStream;

import jakarta.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oner365.common.sequence.sequence.RangeSequence;
import com.oner365.common.sequence.sequence.SnowflakeSequence;
import com.oner365.test.service.BaseServiceTest;

/**
 * 单元测试 -生成主键策略
 *
 * @author zhaoyong
 */
@SpringBootTest
class SequenceTest extends BaseServiceTest {

  @Resource
  private SnowflakeSequence snowflakeSequence;

  @Resource
  private RangeSequence rangeSequence;

  /**
   * 雪花算法
   */
  @Test
  void snowflakeTest() {
    IntStream.range(0, 10).mapToObj(i -> snowflakeSequence.nextValue()).forEach(value -> {
      Assertions.assertNotNull(value);
      logger.info("snowflake value: {}", value);
    });
  }

  /**
   * redis主键生成
   */
  @Test
  void rangeTest() {
    IntStream.range(0, 10).mapToObj(i -> rangeSequence.nextValue()).forEach(value -> {
      Assertions.assertNotNull(value);
      logger.info("redis range value: {}", value);
    });

  }
}
