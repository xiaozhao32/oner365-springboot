package com.oner365.test.service.common;

import com.oner365.common.sequence.sequence.RangeSequence;
import com.oner365.common.sequence.sequence.SnowflakeSequence;
import com.oner365.test.service.BaseServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

/**
 * 单元测试 -生成主键策略
 *
 * @author zhaoyong
 */
@SpringBootTest
public class SequenceTest extends BaseServiceTest {

    @Autowired
    private SnowflakeSequence snowflakeSequence;

    @Autowired
    private RangeSequence rangeSequence;

    /**
     * 雪花算法
     */
    @Test
    public void snowflakeTest() {
        IntStream.range(0, 10).mapToObj(i -> snowflakeSequence.nextValue()).forEach(value -> LOGGER.info("snowflake value: {}", value));
    }

    /**
     * redis主键生成
     */
    @Test
    public void rangeTest() {
        IntStream.range(0, 10).mapToObj(i -> rangeSequence.nextValue()).forEach(value -> LOGGER.info("redis range value: {}", value));

    }
}
