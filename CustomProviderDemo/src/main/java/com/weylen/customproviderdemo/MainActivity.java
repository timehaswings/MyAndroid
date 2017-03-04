package com.weylen.customproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // SharedPreference Files SQLite ContentProvider NetWork
    // 自定义提供器 就是将本程序的数据提供给其他程序使用。
    // 通过Uri(要获取数据的地址)获取联系人的数据
    // 操作步骤：
    // 1. 继承ContentProvider
    // 2. 配置ContentProvider

    // content表示标识符，只要标识符不是content则说明它不是一个Content Uri
    // authority 表示要调用哪一个ContentProvider, 在系统里面存在很多的ContentProvider，比如说：联系人，音视频，图片。。
    // 每一个ContentProvider都对应着唯一的authority
    // Uri content://authority/path/3

    // ContentUris.withAppendedId 在指定的Uri后面追加id
    // ContentUris.parseId() 解析Uri后面的id值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ContactsContract.Data.CONTENT_URI;

        ContentResolver resolver = getContentResolver();// 获取ContentResolver
        Uri contentUri = Uri.parse("content://com.weylen.customproviderdemo/person_info");
//        ContentValues values = new ContentValues();
//        values.put("name", "张三");
//        values.put("age", 22);
//        values.put("address", "四川成都");
//        Uri newlyUri = resolver.insert(contentUri, values);
//        Log.d("zhou", "onCreate: newlyUri:"+newlyUri);
//
//        values.clear();
//        values.put("name", "李四");
//        values.put("age", 33);
//        values.put("address", "四川雅安");
//        newlyUri = resolver.insert(contentUri, values);
//        Log.d("zhou", "onCreate: newlyUri:"+newlyUri);
//
//        values.clear();
//        values.put("name", "王五");
//        values.put("age", 44);
//        values.put("address", "四川宜宾");
//        newlyUri = resolver.insert(contentUri, values);
//        Log.d("zhou", "onCreate: newlyUri:"+newlyUri);

        Cursor cursor = resolver.query(contentUri, null, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            Log.d("zhou", "onCreate: name:"+name+",age:"+age+",address:"+address);
        }
        cursor.close();
    }
}
