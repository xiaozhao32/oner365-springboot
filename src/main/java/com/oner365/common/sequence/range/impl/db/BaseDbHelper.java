package com.oner365.common.sequence.range.impl.db;

import com.oner365.common.sequence.exception.SeqException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.sql.DataSource;

/**
 * sequence db helper
 *
 * @author zhaoyong
 */
abstract class BaseDbHelper {

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS #tableName(id bigint(20) NOT NULL AUTO_INCREMENT,value bigint(20) NOT NULL,name varchar(32) NOT NULL,gmt_create DATETIME NOT NULL,gmt_modified DATETIME NOT NULL,PRIMARY KEY (`id`),UNIQUE uk_name (`name`))";

    private static final String SQL_INSERT_RANGE = "INSERT IGNORE INTO #tableName(name,value,gmt_create,gmt_modified) VALUE(?,?,?,?)";

    private static final String SQL_UPDATE_RANGE = "UPDATE #tableName SET value=?,gmt_modified=? WHERE name=? AND value=?";

    private static final String SQL_SELECT_RANGE = "SELECT value FROM #tableName WHERE name=?";

    static void createTable(DataSource dataSource, String tableName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(SQL_CREATE_TABLE.replace("#tableName", tableName));
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    private static void insertRange(DataSource dataSource, String tableName, String name, long stepStart) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT_RANGE.replace("#tableName", tableName));
            stmt.setString(1, name);
            stmt.setLong(2, stepStart);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    static boolean updateRange(DataSource dataSource, String tableName, Long newValue, Long oldValue, String name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_RANGE.replace("#tableName", tableName));
            stmt.setLong(1, newValue);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setString(3, name);
            stmt.setLong(4, oldValue);
            int affectedRows = stmt.executeUpdate();
            return (affectedRows > 0);
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    static Long selectRange(DataSource dataSource, String tableName, String name, long stepStart) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_RANGE.replace("#tableName", tableName));
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                insertRange(dataSource, tableName, name, stepStart);
                return null;
            }
            long oldValue = rs.getLong(1);
            if (oldValue < 0L) {
                String msg = "Sequence value cannot be less than zero, value = " + oldValue
                        + ", please check table sequence" + tableName;
                throw new SeqException(msg);
            }
            if (oldValue > 9223372036754775807L) {
                String msg = "Sequence value overflow, value = " + oldValue + ", please check table sequence"
                        + tableName;
                throw new SeqException(msg);
            }
            return oldValue;
        } catch (SQLException e) {
            throw new SeqException(e);
        } finally {
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Throwable ignored) {

            }
        }
    }
}
