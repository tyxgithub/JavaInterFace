package com.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.CaseInfo;
import com.utils.Constants;
import com.utils.ExcelUtils;
import com.utils.HttpUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:   tyx
 * Date:     2020/12/21 16:16
 * Description: 数据接入模块
 */
public class DataAccess extends BaseCase {
    @Test(dataProvider = "getDatas")
    public void test_data_access(CaseInfo caseInfo) {
        if(caseInfo.getCaseid()==5){
            String params=replaceData(caseInfo.getParams(),getChannelId("新建二级场景"));
            caseInfo.setParams(params);
        }
        if(caseInfo.getCaseid()==6){
            String params=replaceData(caseInfo.getParams(),getChannelId("新增一级场景"));
            caseInfo.setParams(params);
        }
        if(caseInfo.getCaseid()==4){
            String params=replaceData(caseInfo.getParams(),getEventId("二级场景事件"));
            caseInfo.setParams(params);
            System.out.println(caseInfo);
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
    @DataProvider
    public Object[] getDatas() {
        return ExcelUtils.readCaseInfo(this.setStartSheetIndex, this.setSheetNum);
    }
    //获取事件id
    public static String getEventId(String eventName){
        String id=null;
        Map<String,String> dataMap=new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet(Constants.HOST+"/ss/sdm/rs/sd/config/event/listEventPage?channelId=scene02&keyword=&start=1&limit=10",defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray objects = JSONObject.parseArray(JSON.toJSONString(map.get("data")));
        for (Object object : objects) {
            Map<String,String> map1 = JSONObject.parseObject(object.toString(), Map.class);
            if(map1.get("name").equals(eventName)){
                id=map1.get("id");
            }
        }
        return id;
    }
    //获取渠道id
    public static String getChannelId(String name) {
        String id = null;
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet(Constants.HOST+"/ss/sdm/rs/sd/config/channel/list?name=&start=&limit=&isTree=1", defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray datas = JSONObject.parseArray(map.get("data").toString());
        for (Object data : datas) {
            Map<String, String> map1 = JSONObject.parseObject(data.toString(), Map.class);
            if(map1.get("name").equals(name)){
                id=map1.get("id");
            }
            if (map1.containsKey("childChannelList")) {
                Object childChannelList = JSONObject.parseArray(JSON.toJSONString(map1.get("childChannelList"))).get(0);
                Map<String, String> map2 = JSONObject.parseObject(childChannelList.toString(), Map.class);
                if (map2.get("name").equals(name)) {
                    id = map2.get("id");
                }
            }
        }
        return id;
    }
}

 
