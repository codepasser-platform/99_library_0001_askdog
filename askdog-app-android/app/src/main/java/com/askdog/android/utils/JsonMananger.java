package com.askdog.android.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * [JSON解析管理类]
 **/
public class JsonMananger {

    private static final String tag = JsonMananger.class.getSimpleName();

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T jsonToBean(String json, Class<T> cls) throws Exception {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }


    /**
     * 将bean对象转化成json字符串
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String beanToJson(Object obj) throws Exception {
        Gson gson = new Gson();
        String result = gson.toJson(obj);
        return result;
    }
    /**
     * 把一个json数组串转换成实体数组
     * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}]
     * @param cls e.g. Person.class
     * @return Object[]
     */
    public static Object[] getDtoArrFromJsonArrStr(String jsonArrStr, Class cls) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jsonArr = parser.parse(jsonArrStr).getAsJsonArray();
        Object[] objArr = new Object[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            objArr[i] = gson.fromJson(jsonArr.get(i), cls);
        }
        return objArr;
    }

}
