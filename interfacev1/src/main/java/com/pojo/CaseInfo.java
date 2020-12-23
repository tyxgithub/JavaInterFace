package com.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * Author:   tyx
 * Date:     2020/11/3 14:20
 * Description: 用例文件pojo,注意private 后面的属性参数不能大写
 */
@Data
public class CaseInfo {
    //    用例编号	模块名称	用例描述	请求方式
//    请求地址	请求参数	参数类型	期望结果
//    实际结果	sql	断言结果	其他
    @Excel(name = "用例编号")
    private int caseid;
    @Excel(name = "模块名称")
    private String module;
    @Excel(name = "用例标题")
    private String description;
    @Excel(name = "请求方式")
    private String method;
    @Excel(name = "请求地址")
    private String url;
    @Excel(name = "请求参数")
    private String params;
    @Excel(name = "参数类型")
    private String contentType;
    @Excel(name = "期望结果")
    private String expectedResult;
    @Excel(name = "实际结果")
    private String actualResult;
    @Excel(name = "sql")
    private String sql;
    @Excel(name = "断言结果")
    private String assertResult;
    @Excel(name = "sql结果")
    private String sqlResult;
    @Excel(name = "其他")
    private String other;
}
 
