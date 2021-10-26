package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.oner365.util.DateUtil;

/**
 * 工具类测试
 * 
 * @author zhaoyong
 *
 */
class DateUtilsTest extends BaseUtilsTest {

    @Test
    void dialectDateTest() {
        List<String> result = DateUtil.getDialectDate("2021-05-10", "2021-05-21");
        assertEquals(12, result.size());
    }
    
    @Test
    void localDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        // localDateTime -> date
        Date date = DateUtil.localDateTimeToDate(localDateTime);
        LOGGER.info("date: {}", date);
        // date -> localDateTime
        LocalDateTime result = DateUtil.dateToLocalDateTime(date);
        assertEquals(localDateTime, result);
    }

}
