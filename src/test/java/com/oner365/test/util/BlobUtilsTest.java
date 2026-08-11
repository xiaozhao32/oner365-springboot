package com.oner365.test.util;

import java.sql.Blob;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.oner365.data.commons.util.BlobUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
class BlobUtilsTest extends BaseUtilsTest {

    @Test
    void test() {
        try {
            byte[] data = { 1, 2, 3, 4, 5 };
            Blob blob = new BlobUtils(data);
            long result = blob.length();
            logger.info("length: {}", result);
            Assertions.assertEquals(5L, result);
        }
        catch (Exception e) {
            logger.error("Blob error", e);
        }
    }

}
