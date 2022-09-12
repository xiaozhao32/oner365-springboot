package com.oner365.test.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.oner365.datasource.util.DataSourceUtil;
import com.oner365.util.DataUtils;

/**
 * 工具类测试
 *
 * @author zhaoyong
 *
 */
public class DataSourceUtilTest extends BaseUtilsTest {

    @Test
    void test() {
        assertTrue(DataUtils.isEmpty(null));
    }

    @Test
    void testDriver() {
        String driverName = "dm.jdbc.driver.DmDriver";
        String url = "jdbc:dm://172.16.80.50";
        String userName = "SYSDBA";
        String password = "SYSDBA";
        String dataPath = "/opt/dmdbms/data";
        boolean isConn = DataSourceUtil.isConnection(driverName, url, userName, password);
        
        String name = "oner365";
        String createTablespace = String.format("create tablespace %s datafile '" + dataPath + "/%s/%s.dbf' size 500",
                name, name, name);
        String createUser = String.format("create user %s identified by \"%s\" default tablespace %s", name, password, name);
        String createGrant = String.format("grant \"DBA\",\"PUBLIC\",\"RESOURCE\" to \"%s\" with admin option;", name);
        logger.info("connection {}, url:{}", isConn, url);
        if (isConn) {

            try (Connection con = DataSourceUtil.getConnection(driverName, url, userName, password)) {
                List<Map<String, String>> createTablespaceResult = DataSourceUtil.execute(con, createTablespace);
                logger.info("result tablespace:{}", createTablespaceResult);
                List<Map<String, String>> createUserResult = DataSourceUtil.execute(con, createUser);
                logger.info("result user:{}", createUserResult);
                List<Map<String, String>> createGrantResult = DataSourceUtil.execute(con, createGrant);
                logger.info("result grant:{}", createGrantResult);
            } catch (SQLException e) {
                logger.error("SQLException:", e);
            }
            
        }
    }

}
