package com.utils;

import com.pojo.SD_CFG_LABEL;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:   tyx
 * Date:     2020/11/18 16:53
 * Description: oracle连接工具类
 */
public class OracleUtils {
    public static void main(String[] args) throws SQLException {
//        String sql = "select * from sd_cfg_label ";
        String sql = "select * from sd_cfg_label where NAME_='风险等级bysystest'";
        SD_CFG_LABEL table = new SD_CFG_LABEL();
        System.out.println(queryMap(table, sql));
    }

    private static List<Map<String, Object>> queryMap(Object object, String sql) throws SQLException {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            arrayList.add(field.getName());
        }
        Connection connection = JDBCUtils.getOracleConnection();
        Statement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery(sql);
        List<Map<String, Object>> list = new ArrayList<>();
        while (result.next()) {
            Map<String, Object> reusltMap = new HashMap<>();
            ;
            for (int column = 0; column < result.getFetchSize(); column++) {
                reusltMap.put(arrayList.get(column), result.getObject(column + 1));
            }
            list.add(reusltMap);
        }
        return list;
    }
}
 
