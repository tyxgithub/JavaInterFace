import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.utils.Constants;
import com.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:   tyx
 * Date:     2020/12/21 16:30
 * Description:
 */
public class TestEvent {
    public static void main(String[] args) {
        System.out.println(getChannelId("新建二级场景"));
        System.out.println(getChannelId("新增一级场景"));
//        System.out.println(getEventId("二级场景事件"));
    }

    public static String getEventId(String eventName) {
        String id = null;
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet(Constants.HOST + "/ss/sdm/rs/sd/config/event/listEventPage?channelId=scene02&keyword=&start=1&limit=10", defaultHeaders);
        Map map = JSONObject.parseObject(reponse, Map.class);
        JSONArray objects = JSONObject.parseArray(JSON.toJSONString(map.get("data")));
        for (Object object : objects) {
            Map<String, String> map1 = JSONObject.parseObject(object.toString(), Map.class);
            if (map1.get("name").equals(eventName)) {
                id = map1.get("id");
            }
        }
        return id;
    }

    public static String getChannelId(String name) {
        String id = null;
        Map<String, String> dataMap = new HashMap<>();
        Map<String, Object> defaultHeaders = HttpUtils.getDefaultHeaders();
        String reponse = HttpUtils.doGet("http://10.100.1.129:7150/ss/sdm/rs/sd/config/channel/list?name=&start=&limit=&isTree=1", defaultHeaders);
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

