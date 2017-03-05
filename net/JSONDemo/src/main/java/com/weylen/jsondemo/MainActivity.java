package com.weylen.jsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // 数据封装方式：json xml
    // JSON实际上就是字符串
    // JSONObject {key:value,key:{key:value,key:value}} key是字符串 value的类型多样化
    // JSONArray [1,2,3,{key:value,key:[{},{},]}] 在JSONArray没有key，取元素的值是以下标来取

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonArray();
    }

    private void parseJSONObject(){
        String json = "{'name':'张三','age':22,'address':{'country':'china','province':'四川','city':'成都'}}";
        // org.json
        try {
            JSONObject jsonObject = new JSONObject(json);
            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            JSONObject addressObject = jsonObject.getJSONObject("address");
            String country = addressObject.getString("country");
            String province = addressObject.getString("province");
            String city = addressObject.getString("city");
//            String address = jsonObject.getString("address");
            Log.d("zhou", "parseJSONObject: name:"+name+",age:"+age+",address:"+country+"-"+province+"-"+city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseJSONArray(){
        String json = "[{'name':'张三1','age':22,'address':{'country':'china','province':'四川','city':'成都'}}," +
                "{'name':'张三2','age':22,'address':{'country':'china','province':'四川','city':'成都'}}," +
                "{'name':'张三3','age':22,'address':{'country':'china','province':'四川','city':'成都'}}," +
                "{'name':'张三4','age':22,'address':{'country':'china','province':'四川','city':'成都'}}," +
                "{'name':'张三5','age':22,'address':{'country':'china','province':'四川','city':'成都'}}]";
        try {
            JSONArray jsonArray = new JSONArray(json);
            int size = jsonArray.length(); // 得到数组长度
            for (int i = 0; i < size; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int age = jsonObject.getInt("age");
                JSONObject addressObject = jsonObject.getJSONObject("address");
                String country = addressObject.getString("country");
                String province = addressObject.getString("province");
                String city = addressObject.getString("city");
//            String address = jsonObject.getString("address");
                Log.d("zhou", "parseJSONObject: name:"+name+",age:"+age+",address:"+country+"-"+province+"-"+city);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonObject(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 22);
        map.put("address", "四川成都");

        JSONObject jsonObject = new JSONObject(map);
        Log.d("zhou", "jsonObject1: jsonObject:"+jsonObject);
        jsonObject = new JSONObject();
        try {
            jsonObject.put("address", "四川雅安");
            jsonObject.put("name", "李四");
            jsonObject.put("age", 33);
            Log.d("zhou", "jsonObject2: jsonObject:"+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonArray() {
        ArrayList<Object> data = new ArrayList<>();
        data.add(1);
        data.add("sb");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 22);
        map.put("address", "四川成都");

        data.add(map);

        JSONArray jsonArray = new JSONArray(data);
        Log.d("zhou", "jsonArray1:"+jsonArray);
        jsonArray = new JSONArray();
        jsonArray.put(2);
        jsonArray.put("hello");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "王五");
            jsonObject.put("age", 44);
            jsonObject.put("address", "四川绵阳");
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("zhou", "jsonArray2:"+jsonArray);

    }
}
