package com.xzg.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author xzg
 */
public class JsonUtils {
//    String json = JsonUtils.deserializer(map);
//    BaseUser user = JsonUtils.serializable(json, BaseUser.class);
    public static String deserializer(Object object){
        return JSONObject.toJSONString(object);
    }

    public static <T> T serializable( String json,Class<T> clazz){
           return  JSONObject.parseObject(json,clazz);
    }
}
