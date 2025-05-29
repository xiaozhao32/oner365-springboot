package com.oner365.test.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.DateUtil;

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
        Assertions.assertEquals(12, result.size());
    }

    @Test
    void localDateTest() {
        LocalDate localDate = LocalDate.now();
        // localDate -> date
        Date date = DateUtil.localDateToDate(localDate);
        logger.info("date: {}", date);
        // date -> localDate
        LocalDate result = DateUtil.dateToLocalDate(date);
        Assertions.assertEquals(localDate, result);
    }

    @Test
    void localDateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        // localDateTime -> date
        Date date = DateUtil.localDateTimeToDate(localDateTime);
        logger.info("date: {}", date);
        // date -> localDateTime
        LocalDateTime result = DateUtil.dateToLocalDateTime(date);
        Assertions.assertEquals(localDateTime, result);
    }

    @Test
    void getDialectWeekTest() {
        List<Map<String, String>> result = DateUtil.getDialectWeek("2021-05-10", "2021-05-21");
        logger.info("list: {}", result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void formatTest() {
        String result = DateUtil.format(DateUtil.getDate(), DateUtil.FULL_TIME_FORMAT);
        logger.info("date: {}", result);
        Assertions.assertNotNull(result);
    }

}
