package com.weylen.customproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhou on 2016/4/18.
 */
public class MyHelper extends SQLiteOpenHelper{

    public static final int VERSION = 1;

    public static final String DB_NAME = "people_db";

    public static final String CREATE_PERSON_INFO = "CREATE TABLE " + MyProvider.TABLE_NAME + "(" +
            "id integer primary key autoincrement," +
            "name varchar(20)," +
            "age integer(3)," +
            "address varchar(50));";

    public MyHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERSON_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
