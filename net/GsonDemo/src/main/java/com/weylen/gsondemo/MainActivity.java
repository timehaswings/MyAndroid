package com.weylen.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // GSON google对于解析JSON数据的一种封装

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toJSONObject();
    }

    private void parseJSONObject(){
        String json = "{'name':'张三','age':22,'address':'四川成都'}";
        Gson gson = new Gson();
//        HashMap map = gson.fromJson(json, HashMap.class);
//        Log.d("zhou", "parseJSONObject: map:"+map);
        Person person = gson.fromJson(json, Person.class);
        String name = person.getName();
        Log.d("zhou", "parseJSONObject: name:"+name);
        Log.d("zhou", "parseJSONObject: person:"+person);
    }

    private void parseJSONArray(){
        String json = "[{'name':'张三','age':22,'address':'四川成都'},{'name':'李四','age':33,'address':'四川雅安'}]";
        Gson gson = new Gson();
//        gson.fromJson(json, new TypeToken<List<Person>>(){}.getType());
        List<Person> data = gson.fromJson(json, new MyToken<List<Person>>().getType());
        Log.d("zhou", "parseJSONArray: data:"+data);
    }

    private void parse(){
        String json = "[1;2,{'name'='张三';'age':22,'address':'四川成都'}]";
        JsonReader jsonReader = new JsonReader(new StringReader(json));
        jsonReader.setLenient(true);// 设置忽略一些规定
        // 开始解析
        try {
            jsonReader.beginArray();
            int num1 = jsonReader.nextInt();
            int num2 = jsonReader.nextInt();
            Log.d("zhou", "parse: num1:"+num1+",num2:"+num2);
            jsonReader.beginObject();
            while(jsonReader.hasNext()){
                String name = jsonReader.nextName();
                String value = jsonReader.nextString();
                Log.d("zhou", "parse: name:"+name+",value-->"+value);
            }
            jsonReader.endObject();
            jsonReader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void test(){
        String json = "{'content':[{'name':'张三','age':22,'address':'四川成都'},{'name':'李四','age':33,'address':'四川雅安'}],'status':true}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getBoolean("status")){
                String content = jsonObject.getString("content");
                Gson gson = new Gson();
                List<HashMap<String, Object>> data = gson.fromJson(content, new TypeToken<List<HashMap<String, Object>>>(){}.getType());
                Log.d("zhou", "test: data:"+data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void toJSONObject(){
        Person person = new Person();
        person.setName("王五");
        person.setAddress("北京北京");
        person.setAge(22);
        Gson gson = new Gson();
        String json = gson.toJson(person);
        Log.d("zhou", "toJSONObject: json:"+json);
    }

    private class MyToken<T> extends TypeToken<T>{
        public MyToken() {
            super();
        }
    }
}
