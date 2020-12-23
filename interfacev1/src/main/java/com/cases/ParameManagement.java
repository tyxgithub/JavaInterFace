package com.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.CaseInfo;
import com.utils.Constants;
import com.utils.ExcelUtils;
import com.utils.HttpUtils;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:   tyx
 * Date:     2020/11/5 18:09
 * Description: 注册用例类
 */
public class ParameManagement extends BaseCase {
    @Test(dataProvider = "getDatas")
    public void test_para_management(CaseInfo caseInfo) {
        if(caseInfo.getCaseid()==4){
            String params=replaceData(caseInfo.getParams(),getVerifyId("tyxtest","网络金融","VERIFY"));
            caseInfo.setParams(params);
        }
        if(caseInfo.getCaseid()==9){
            String params=replaceData(caseInfo.getParams(),getVerifyId("tyxtest","网络金融","GROUP"));
            caseInfo.setParams(params);
        }
        if(caseInfo.getCaseid()==13){
            String params=replaceData(caseInfo.getParams(),getRiskId("12","网络金融"));
            caseInfo.setParams(params);
        }
        String responseBody = HttpUtils.call(caseInfo, headers);
        Boolean assertResult = responseAssert(caseInfo, responseBody);

        //响应数据回写
        if(assertResult){
            addDataToWriteBack(caseInfo.getCaseid(), Constants.ASSERT_RESULT, "通过", setStartSheetIndex);
        }else {
            addDataToWriteBack(caseInfo.getCaseid(), Constants.ASSERT_RESULT, "失败", setStartSheetIndex);
        }
        //数据回写
        addDataToWriteBack(caseInfo.getCaseid(), Constants.WRITE_BACK_CELL_NUM, responseBody, setStartSheetIndex);
        Assert.assertTrue(assertResult);
    }
    //获取所有风险等级的name,id的map集合
    public static String getRiskId(String code,String sceneName){
        //code:12 sceneName:网络金融
        Map<String,String> dataMap=new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet(Constants.HOST+"/ss/sdm/rs/sd/config/label/list?labelType=RISK_LEVEL&labelName=&start=1&limit=2500",defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray datas = JSONObject.parseArray(map.get("data").toString());
        for(Object data:datas){
            Map<String,String> map1 = JSONObject.parseObject(data.toString(), Map.class);
            dataMap.put(map1.get("code").toString()+"-"+map1.get("sceneName").toString(),map1.get("id"));
        }
        String id=dataMap.get(code+"-"+sceneName);
        return id;
    }
    //获取所有验证策略的name,id的map集合
    public static String getVerifyId(String code,String sceneName,String policy){
        //code:tyxtest sceneName:网络金融
        Map<String,String> dataMap=new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet(String.format(Constants.HOST+"/ss/sdm/rs/sd/config/label/listOrderByCode?labelType=%s&labelName=&start=1&limit=250000",policy), defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray datas = JSONObject.parseArray(map.get("data").toString());
        for(Object data:datas){
            Map<String,String> map1 = JSONObject.parseObject(data.toString(), Map.class);
            dataMap.put(map1.get("code").toString()+"-"+map1.get("sceneName").toString(),map1.get("id"));
        }
        String id=dataMap.get(code+"-"+sceneName);
        return id;
    }
    @DataProvider
    public Object[] getDatas() {
        return ExcelUtils.readCaseInfo(this.setStartSheetIndex, this.setSheetNum);
    }
}
 
