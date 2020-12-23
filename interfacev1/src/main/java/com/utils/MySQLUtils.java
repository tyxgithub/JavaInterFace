package com.utils;

//import com.mysql.jdbc.Connection;

import java.sql.Connection;

import com.pojo.SD_CFG_LABEL;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author ：tyx
 * @date ：Created in 2020/9/22 19:47
 * @description：数据库操作工具类
 */
public class MySQLUtils {
    public static void main(String[] args) throws Exception {
        //DBUtils
        //新增
//        String sql="insert into member(reg_name,pwd,mobile_phone,type,leave_amount,reg_time) VALUES('海贼王','25D55AD283AA400AF464C76D713C07AD','1888888888',1,10,NOW())";
        //修改
//        String sql="update member set type=1 where mobile_phone='18888888888'";
//        update(sql);
        //查询数据
//        mapHandler();
//        beanHandler();
//        beanListHandler();
//        String sql = "select * from member a where a.mobile_phone='15713685668'";
//        System.out.println(getSingleResult(sql));
        String sql = "select * from sd_cfg_label where NAME_='风险等级bysystest'";
        MySQLUtils.beanListHandler(new SD_CFG_LABEL(), sql);
    }

    //查询一列数据
    public static Object getSingleResult(String sql) {
        //如果sql为空，直接返回null
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        ;
        Object result = null;
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getMysqlConnect();
            ScalarHandler scalarHandler = new ScalarHandler();
            result = runner.query(conn, sql, scalarHandler);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //无论是否发生异常都会执行，一般用来释放资源
            JDBCUtils.close(conn);
        }
        return result;
    }

    //    public static void beanListHandler() throws SQLException {
//        QueryRunner runner = new QueryRunner();
//        Connection conn = JDBCUtils.getConnect();
//        BeanListHandler<Member> beanHandler = new BeanListHandler<>(Member.class);
//        String sql = "select * from member a where a.mobile_phone='18888888888' limit 5";
//        List<Member> result = runner.query(conn, sql, beanHandler);
//        for (Member member : result) {
//            System.out.println(member);
//        }
//    }
    public static void beanListHandler(Object object, String sql) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getMysqlConnect();
        BeanListHandler<?> beanHandler = new BeanListHandler<>(object.getClass());
        List<?> result = runner.query(conn, sql, beanHandler);
        for (Object resultObject : result) {
            System.out.println(resultObject);
        }
    }


    public static void beanHandler(Object object) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getMysqlConnect();
        BeanHandler<? extends Object> beanHandler = new BeanHandler<>((Class) object.getClass());
        String sql = "select * from member a where a.mobile_phone='18888888888'";
        Object result = runner.query(conn, sql, beanHandler);
        System.out.println(result);
    }

    //查询一条数据，数据格式体为{key=value,....}
    public static void mapHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getMysqlConnect();
        MapHandler handler = new MapHandler();
        String sql = "select * from member a where a.mobile_phone='18888888888'";
        Map<String, Object> query = runner.query(conn, sql, handler);
        System.out.println(query);
    }

    public static void update(String sql) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = JDBCUtils.getMysqlConnect();
        int update = runner.update(conn, sql);
        System.out.println(update);
        JDBCUtils.close(conn);
    }
}
