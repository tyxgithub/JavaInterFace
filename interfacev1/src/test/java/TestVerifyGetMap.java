import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utils.HttpUtils;

import java.awt.*;
import java.util.*;

/**
 * Author:   tyx
 * Date:     2020/12/21 10:33
 * Description:
 */
public class TestVerifyGetMap {
    public static void main(String[] args) {
        Map<String,String> dataMap=new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet("http://10.100.1.129:7150/ss/sdm/rs/sd/config/label/listOrderByCode?labelType=VERIFY&labelName=&start=1&limit=250000", defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray datas = JSONObject.parseArray(map.get("data").toString());
        for(Object data:datas){
            Map<String,String> map1 = JSONObject.parseObject(data.toString(), Map.class);
            dataMap.put(map1.get("code").toString()+"-"+map1.get("sceneName").toString(),map1.get("id"));
        }
        System.out.println(dataMap);
        System.out.println(dataMap.size());
    }
}
 
