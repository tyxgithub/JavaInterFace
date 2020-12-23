package com.utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ：tyx
 * @date ：Created in 2020/9/22 19:32
 * @description：jdbc工具类
 */
public class JDBCUtils {
    public static void main(String[] args) {
        System.out.println(JDBCUtils.getMysqlConnect());
        System.out.println(JDBCUtils.getOracleConnection());
    }

    public static Connection getMysqlConnect() {
        String url = Constants.MYSQL_URL;
        String user = Constants.MYSQL_USER;
        String password = Constants.MYSQL_PASSWORD;
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getOracleConnection() {
        //jdbc:oracle:thin:@//10.100.1.90:1521/orclpdb
        String url = Constants.ORACLE_URL;
        String user = Constants.ORACLE_USER;
        String password = Constants.ORACLE_PASSWORD;
        Connection conn = null;
        try {
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
