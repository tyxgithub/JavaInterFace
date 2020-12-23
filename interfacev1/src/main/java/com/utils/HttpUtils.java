package com.utils;

import com.alibaba.fastjson.JSONObject;
import com.pojo.CaseInfo;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.*;

/**
 * Author:   tyx
 * Date:     2020/11/5 19:57
 * Description: http请求工具类
 */
public class HttpUtils {
    public static Logger logger = Logger.getLogger(HttpUtils.class);

    public static void main(String[] args) {
//        System.out.println(HttpUtils.getDefaultHeaders());
//        String url = String.format("%s%s", Constants.HOST, "/ss/sdm/rs/sd/config/label/listOrderByCode?labelType=VERIFY&labelName=&start=1&limit=25");
//        System.out.println(url);
//        System.out.println(given().headers(HttpUtils.getDefaultHeaders()).get(url).asString());
//        System.out.println(HttpUtils.doGet(url, HttpUtils.getDefaultHeaders()));

    }

    public static String call(CaseInfo caseInfo, Map<String, Object> headers) {
        String url = String.format("%s%s", Constants.HOST, caseInfo.getUrl());
        if (url == null) {
            logger.info("url为空");
            return "url为空";
        }
        String params = caseInfo.getParams();
        if (params == null) {
            logger.info("请求参数为空");
            return "请求参数为空";
        }
        String contentType = caseInfo.getContentType();
        if (contentType == null) {
            logger.info("请求文本类型为空");
            return "请求文本类型为空";
        }
        String method = caseInfo.getMethod();
        if (method == null) {
            logger.info("请求方法为空");
            return "请求方法为空";
        }
        String body = null;
        if ("get".equalsIgnoreCase(method)) {
            body = HttpUtils.doGet(url + params, headers);
        } else if ("post".equalsIgnoreCase(method)) {
            if ("form".equalsIgnoreCase(contentType)) {
                params = HttpUtils.jsonToKeyValues(params);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
            }
            body = HttpUtils.doPost(url, headers, params);
        } else if ("put".equalsIgnoreCase(method)) {
            body = HttpUtils.doPut(url, headers, params);
        } else if ("patch".equalsIgnoreCase(method)) {
            body = HttpUtils.doPatch(url, headers, params);
        } else if ("delete".equalsIgnoreCase(method)) {
            body = HttpUtils.doDelete(url + params, headers);
            logger.info("请求连接："+url+params);
        } else {
            logger.info("请求类型错误或者未定义.");
            return "请求类型错误或者未定义.";
        }
        return body;
    }

    public static int dataCount(CaseInfo caseInfo, Map<String, Object> headers) {
        String url = String.format("%s%s", Constants.HOST, caseInfo.getOther());
        String response = doGet(url, headers);
        Map<String, Object> map = JSONObject.parseObject(response, Map.class);
        return (Integer) map.get("tatal");
    }

    public static String jsonToKeyValues(String json) {
        //json参数转为key1=value1&key2=value2这种form表单的数据
        Map map = JSONObject.parseObject(json, Map.class);
        Set<String> keySets = map.keySet();
        String params = "";
        for (String key : keySets) {
            params += key + "=" + map.get(key) + "&";
        }
        params = params.substring(0, (params.length() - 1));
        return params;
    }

    public static Map<String, Object> getDefaultHeaders() {
        String loginUrl = String.format("%s/ss/portal/j_spring_security_check", Constants.HOST);
        String params = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", "admin", Sha256Util.getSHA256("bangsun"));
        Map<String, Object> headers01 = new HashMap<>();
        Map<String, Object> headers = new HashMap<>();
        headers01.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
        headers01.put("Accept", "application/json, text/plain, */*");
        headers01.put("Content-Type", "application/x-www-form-urlencoded");
        params = HttpUtils.jsonToKeyValues(params);
        Response response = given().headers(headers01).body(params).post(loginUrl);

        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
        headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Cookie", String.format("Language=zh-CN; SESSION=%s", response.cookies().get("SESSION")));
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    public static String doGet(String url, Map<String, Object> headers) {
        return given().headers(headers).get(url).asString();
    }

    public static String doPost(String url, Map<String, Object> headers, String params) {
        try {
            JSONObject.parseObject(params);
        } catch (Exception e) {
            logger.info(e);
            logger.info("错误，fastjson解析错误！");
        }
        return given().headers(headers).body(params).post(url).asString();
    }

    public static String doPut(String url, Map<String, Object> headers, String params) {
        try {
            JSONObject.parseObject(params);
        } catch (Exception e) {
            logger.info(e);
            logger.info("错误，fastjson解析错误！");
        }
        return given().headers(headers).body(params).put(url).asString();
    }

    public static String doPatch(String url, Map<String, Object> headers, String params) {
        try {
            JSONObject.parseObject(params);
        } catch (Exception e) {
            logger.info(e);
            logger.info("错误，fastjson解析错误！");
        }
        return given().headers(headers).body(params).patch(url).asString();
    }

    public static String doDelete(String url, Map<String, Object> headers) {
        return given().headers(headers).delete(url).asString();
    }
}
 
