package com.weylen.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhou on 2016/4/14.
 */
public class MyHelper extends SQLiteOpenHelper{

    public static final String DEFAULT_DB_NAME = "consumption";

    public static final String DETAILS_TABLE_SQL = "create table details(id integer primary key autoincrement, " +
            "content varchar(200));";

    public static final String NEW_TYPE_SQL = "alter table events add type varchar(10)";

    /**Cursor 游标 光标 结果。和java.sql.ResultSet类似。
     * @param context 上下文
     * @param name 数据库的名字
     * @param factory 构建结构集的工厂对象，默认给null
     * @param version 数据库的版本
     */
    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyHelper(Context context, int version){
        super(context, DEFAULT_DB_NAME, null, version);
    }

    @Override // 指的是数据库的创建，数据库在第一次创建时调用此方法。
    // 数据库若是存在则不会调用此方法。 1.SQLiteDatabase 类似于java.sql.Statement
    public void onCreate(SQLiteDatabase db) {
        Log.d("zhou", "onCreate: 执行了onCreate方法");
        db.execSQL(MainActivity.EVENTS_TABLE_SQL);
        db.execSQL(DETAILS_TABLE_SQL);
        db.execSQL(NEW_TYPE_SQL);
    }

    @Override // 指的是数据库的版本更新时调用。1.SQLiteDatabase数据库操作对象 2.oldVersion 老版本编号 3.newVersion新版本号
    // 版本值应该递增的
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("zhou", "onCreate: 执行了onUpgrade方法");
        switch (oldVersion){
            case 1:
                db.execSQL(DETAILS_TABLE_SQL);
                db.execSQL(NEW_TYPE_SQL);
                break;
            case 2:
                db.execSQL(NEW_TYPE_SQL);
                break;
        }
    }
}
