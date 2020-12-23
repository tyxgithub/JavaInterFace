package com.utils;

import com.cases.BaseCase;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Author:   tyx
 * Date:     2020/11/5 16:19
 * Description: 常量类
 */
public class Constants {
    public static Logger logger = Logger.getLogger(Constants.class);

    public static final String EXCEL_PATH =getExcelPath();
    public static final String CASEINFO_CLASS_NAME = "com.pojo.CaseInfo";
    public static final String MYSQL_URL = "jdbc:mysql://10.100.1.150:3306/at_rule7110mysql1014?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    public static final String MYSQL_USER = "root";
    public static final String MYSQL_PASSWORD = "root";

    public static final String ORACLE_URL = "jdbc:oracle:thin:@//10.100.1.90:1521/orclpdb";
    public static final String ORACLE_USER = "at_rule7110";
    public static final String ORACLE_PASSWORD = "bangsun";
    public static final int WRITE_BACK_CELL_NUM = 8;
    public static final int ASSERT_RESULT=11;
    public static final String HOST = "http://10.100.1.129:7150";

    public static void main(String[] args) throws IOException {
        System.out.println(Constants.getExcelPath());
        System.out.println(Constants.findFiles(System.getProperty("user.dir"), "case.xlsx"));
    }
    public static String getExcelPath(){
        String path=Constants.findFiles(System.getProperty("user.dir"),"case.xlsx").toString();
        return path;
    }
    public static String getLogPath(){
        String path=Constants.findFiles(System.getProperty("user.dir"),"info.log").toString();
        return path;
    }
    /**
     * 递归查找文件
     * @param baseDirName  查找的文件夹路径
     * @param targetFileName  需要查找的文件名
     */
    public static File findFiles(String baseDirName, String targetFileName) {

        File file = null;
        File baseDir = new File(baseDirName);       // 创建一个File对象
        if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在
            logger.info("文件查找失败：" + baseDirName + "不是一个目录！");
        }
        String tempName = null;
        //判断目录是否存在
        File tempFile;
        File[] files = baseDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            tempFile = files[i];
            if(tempFile.isDirectory()){
                file = findFiles(tempFile.getAbsolutePath(), targetFileName);
                if (file != null) {
                    return  file;
                }
            }else if(tempFile.isFile()){
                tempName = tempFile.getName();
                if(tempName.equals(targetFileName)){
                    return tempFile.getAbsoluteFile();
                }
            }
        }
        return file;
    }
}
 
