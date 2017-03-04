package com.weylen.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // SQLite
    // 1.SQLiteOpenHelper
    // 2.SQLiteDatabase
    // Q1：数据库什么时候被创建？当调用getReadableDatabase或者getWritableDatabase就会创建数据库
    // Q2：数据库的版本怎么更新？
    // Q3：數據庫在什麽位置？ data/data/packageName/databases/....

    // 1.events表
    // 2.新增一張詳細表 details
    // 3.在events表裏面新增加了一個字段type

    public static final String EVENTS_TABLE_SQL = "CREATE TABLE events(id integer primary key autoincrement," +
            "title varchar(20), description varchar(100), date datetime, money double(10,2));";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 创建数据库的按钮监听器
     * @param view
     */
    public void createDatabase(View view){
        // 创建辅助类对象
        try{
            MyHelper myHelper = new MyHelper(this, 1);
            SQLiteDatabase database = myHelper.getWritableDatabase();
            database.close();
            myHelper.close();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "数据库已经创建好", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 更新数据库的版本号
     * @param view
     */
    public void updateDatabaseVersion(View view){
        // 创建辅助类对象时传递不同的版本号即可。
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase database = myHelper.getReadableDatabase();
        // 获取数据库的版本
        int version = database.getVersion();
        database.close();
        myHelper.close();
    }

    /**
     * 創建表
     * @param view
     */
    public void createTable(View view){
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase database = myHelper.getReadableDatabase();
        database.execSQL(EVENTS_TABLE_SQL);
        database.close();
        myHelper.close();
    }

    /**
     * 新增數據
     * @param view
     */
    public void insertData(View view){
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        // 第一种添加数据的方式：标准的SQL操作
        db.execSQL("insert into events values(null, '点外卖', '中午在教室点了黄焖鸡', '2016-04-14 12:20:01', '20.01', '吃')");
        // 第二种做法：预处理操作模式
        String insertSQL = "insert into events values(null, ?, ?, ?, ?, ?)";
        db.execSQL(insertSQL, new Object[]{"矿泉水", "", "2016-04-14 13:01:01","2.00", "喝"});
        // 第三种做法：SQLite自带做法
        // 1.表名 2. 3.添加的数据
        // 做法和Map一致
        ContentValues values = new ContentValues();
        // // 封装数据 1. 列名 2.值
        values.put("title", "坐公交");
        values.put("description", "日常坐车");
        values.put("date", "2016-04-14 08:20:21");
        values.put("money", 2.00);
        values.put("type", "行");
        // 插入如果成功 则返回的是新插入这条数据的long的id值,如果id = -1则说明插入失败
        long id = db.insert("events", null, values);
        Log.d("zhou", "insertData: 新插入的数据的id:"+id);
        db.close();
        myHelper.close();
    }

    /**
     * 删除
     * @param view
     */
    public void deleteData(View view){
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //1.表名 2.条件 3.条件对应的值
        db.delete("events", "id = ?", new String[]{String.valueOf(4)});
        db.delete("events", "id = 5", null);
        db.execSQL("delete from events where id = 6");
        db.execSQL("delete from events where id = ?", new Object[]{6});
        db.close();
        myHelper.close();
    }

    /**
     * 更新数据
     * @param view
     */
    public void updateData(View view){
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        // 1.表名 2.更新的数据 ContentValues 3.条件 4.条件对应的值
        ContentValues values = new ContentValues();
        values.put("id", 4);
        db.update("events", values, "id = ?", new String[]{String.valueOf(7)});
        db.execSQL("update events set id = 5 where id = 8");
        db.execSQL("update events set id = 6 where id = ?", new Object[]{9});
        db.close();
        myHelper.close();
    }

    /**
     * 查询数据
     * @param view
     */
    public void queryData(View view){
        MyHelper myHelper = new MyHelper(this, 1);
        SQLiteDatabase db = myHelper.getWritableDatabase();
        // 1.表名 2.查询的字段 3.条件 4.条件对应的值 5.分组 6.分组的条件 7.排序
        Cursor cursor = db.query("events", null, null, null, null, null, null);
        while(cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Log.d("zhou", "queryData: id->"+id+",title->"+title+",description->"+description
                +",date->"+date+",money->"+money+",type->"+type);
        }
        cursor.close();
        db.close();
        myHelper.close();
    }
}
