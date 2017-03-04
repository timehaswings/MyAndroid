package com.zyh.android.withthenotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhou on 2016/1/15.
 */
public class CostDBHelper extends SQLiteOpenHelper{

    public static final String CREATE_COST_TABLE = "CREATE TABLE "+CostDBColumn.TABLENAME+"( " +
            CostDBColumn._ID+ " integer primary key autoincrement," +
            CostDBColumn.EVENT_NAME +" varchar(50)," +
            CostDBColumn.EVENT_DESC +" text," +
            CostDBColumn.EVENT_COST + " decimal(20,2),"+
            CostDBColumn.EVENT_DATE +" date," +
            CostDBColumn.YEAR + " text," +
            CostDBColumn.MONTH + " text," +
            CostDBColumn.DAY + " text" +
            ")";

    public CostDBHelper(Context context) {
        super(context, CostDBColumn.DBNAME, null, CostDBColumn.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
