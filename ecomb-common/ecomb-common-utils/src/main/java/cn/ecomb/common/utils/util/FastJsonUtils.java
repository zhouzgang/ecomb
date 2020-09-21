package cn.ecomb.common.utils.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 * @description:
 * @author: lishijia
 * @create: 2019-10-09 17:45
 **/
public class FastJsonUtils {

    public static String toJSONString(Object object){
        return JSONObject.toJSONString(object,
                WriteNullBooleanAsFalse,
                WriteNullListAsEmpty,
                WriteNullStringAsEmpty);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return JSONObject.parseObject(json, typeOfT);
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        List<T> ts = JSONArray.parseArray(jsonString, clazz);
        return ts;
    }



}