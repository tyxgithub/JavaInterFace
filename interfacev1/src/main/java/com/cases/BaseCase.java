package com.cases;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.pojo.CaseInfo;
import com.pojo.WriteBack;
import com.utils.ExcelUtils;
import com.utils.HttpUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.*;

import java.util.Map;
import java.util.Set;

/**
 * Author:   tyx
 * Date:     2020/11/5 18:13
 * Description: 所有用例类的基类
 */
public class BaseCase {
    public static Logger logger = Logger.getLogger(BaseCase.class);
    public static String systemDir = System.getProperty("user.dir");
    public Map<String, Object> headers = null;
    public int setStartSheetIndex;
    public int setSheetNum;

    public void addDataToWriteBack(int rowNum, int cellNum, String content, int sheetIndex) {
        WriteBack wb = new WriteBack(rowNum, cellNum, content, sheetIndex);
        ExcelUtils.writeBackList.add(wb);
    }
    public static String replaceData(String params,String targetMethod){
        params=params.replace("${param}",targetMethod);
        return params;
    }
    @BeforeClass
    public void beforeSuit() {
        headers = HttpUtils.getDefaultHeaders();
    }

    @AfterSuite
    public void afterSuite() {
        ExcelUtils.batchWrite();
        logger.info("Excel数据回写成功.");
    }

    @BeforeClass
    @Parameters({"setStartSheetIndex", "setSheetNum"})
    public void beforeClass(int setStartSheetIndex, int setSheetNum) {
        this.setStartSheetIndex = setStartSheetIndex;
        this.setSheetNum = setSheetNum;
    }

    public Boolean responseAssert(CaseInfo caseInfo, String responseBody) {
        String expectedResult = caseInfo.getExpectedResult();
        Map<String, Object> map = null;
        try {
            map = JSONObject.parseObject(expectedResult, Map.class);
        } catch (Exception e) {
            logger.info(String.format("用例编号:%s ,用例名称:%s ,期望结果fastjson解析失败", caseInfo.getCaseid(), caseInfo.getDescription()));
            logger.info(e);
        }
        Set<String> keySets = map.keySet();
        Boolean assertResult = true;
        int count = 0;
        logger.info(String.format("-----执行用例,用例编号: %s,用例标题: %s-----",caseInfo.getCaseid(),caseInfo.getDescription()));
        for (String actualJsonPath : keySets) {
            count++;
            Object actualValue = JSONPath.read(responseBody, actualJsonPath);
            Object expectValue = map.get(actualJsonPath);
            if (!expectValue.equals(actualValue)) {
                logger.info("字段" + count + ": 断言失败！正则表达式字段：" + actualJsonPath + ",实际值：" + actualValue + "，期望值：" + expectValue);
                assertResult = false;
            }
        }
        if (assertResult) {
            logger.info("-----响应断言成功------");
        }else{
            logger.info("-----响应断言失败!!------");
        }
        return assertResult;
    }
}
 
