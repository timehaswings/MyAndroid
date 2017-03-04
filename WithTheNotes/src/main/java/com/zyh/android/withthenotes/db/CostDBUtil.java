package com.zyh.android.withthenotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2016/1/15.
 */
public class CostDBUtil {

    /**
     * 获取所有消费
     * 异步查询
     * @return
     */
    public static void getAllCost(final Context context, final CostDBInterface callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                double allCost = 0d;
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getReadableDatabase();
                Cursor cursor = db.query(CostDBColumn.TABLENAME,new String[]{"sum("+CostDBColumn.EVENT_COST+")"},null,null,null,null,null);
                if(cursor != null && cursor.moveToFirst()){
                    allCost = cursor.getDouble(0);
                }
                db.close();
                helper.close();
                String result = formatCost(allCost);
                if(callback != null){
                    callback.onQueryResult(result);
                }
            }
        };
        thread.start();
    }

    public static void deleteCost(final Context context, final int id, final CostDBInterface.OnAddNewCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getReadableDatabase();
                int count = db.delete(CostDBColumn.TABLENAME, CostDBColumn._ID +" = ?", new String[]{String.valueOf(id)});
                db.close();
                helper.close();
                context.getContentResolver().notifyChange(CostDBColumn.CONTENT_URI, null);
                if(callback != null){
                    callback.complete(count >= 1);
                }

            }
        };
        thread.start();
    }

    /**
     * 将消费金额转换只包含两位小数的字符串
     * @param cost
     * @return
     */
    public static String formatCost(double cost){
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(cost);
    }

    public static void deleteAllCost(final Context context, final CostDBInterface.OnDeleteAllCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                // 删除表 在重新创建表
                db.execSQL("drop table if exists "+CostDBColumn.TABLENAME);
                db.execSQL(CostDBHelper.CREATE_COST_TABLE);
                helper.close();
                db.close();
                if(callback != null){
                    callback.complete();
                }
            }
        };
        thread.start();
    }

    public static void addNewCost(final Context context,final CostDBBean info, final CostDBInterface.OnAddNewCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(CostDBColumn.EVENT_NAME, info.getEventName());
                values.put(CostDBColumn.EVENT_COST, info.getEventCost());
                values.put(CostDBColumn.EVENT_DATE, info.getEventDate());
                values.put(CostDBColumn.EVENT_DESC, info.getEventDesc());
                values.put(CostDBColumn.YEAR, info.getYear());
                values.put(CostDBColumn.MONTH, info.getMonth());
                values.put(CostDBColumn.DAY, info.getDay());
                long newRowId = db.insert(CostDBColumn.TABLENAME, null , values);
                helper.close();
                db.close();
                context.getContentResolver().notifyChange(CostDBColumn.CONTENT_URI, null);
                if(callback != null){
                    callback.complete(newRowId != -1);
                }
            }
        };
        thread.start();
    }

    public static void updateCost(final Context context,final CostDBBean info, final CostDBInterface.OnAddNewCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(CostDBColumn.EVENT_NAME, info.getEventName());
                values.put(CostDBColumn.EVENT_COST, info.getEventCost());
                values.put(CostDBColumn.EVENT_DATE, info.getEventDate());
                values.put(CostDBColumn.EVENT_DESC, info.getEventDesc());
                values.put(CostDBColumn.YEAR, info.getYear());
                values.put(CostDBColumn.MONTH, info.getMonth());
                values.put(CostDBColumn.DAY, info.getDay());

                long count = db.update(CostDBColumn.TABLENAME, values, CostDBColumn._ID +" = ?"
                    ,new String[]{String.valueOf(info.getId())});
                helper.close();
                db.close();

                context.getContentResolver().notifyChange(CostDBColumn.CONTENT_URI, null);
                if(callback != null){
                    callback.complete(count >= 1);
                }
            }
        };
        thread.start();
    }

    public static void queryCostYear(final Context context, final CostDBInterface.OnQueryCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                List<CostDBBean> data = new ArrayList<>();

                Cursor yearCursor = db.query(CostDBColumn.TABLENAME, new String[]{CostDBColumn._ID,CostDBColumn.YEAR}, null ,null, CostDBColumn.YEAR, null, null);
                while(yearCursor.moveToNext()){
                    CostDBBean bean = new CostDBBean();
                    bean.setId(yearCursor.getInt(yearCursor.getColumnIndex(CostDBColumn._ID)));

                    String year = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.YEAR));
                    bean.setEventName(year+" 年");

                    bean.setYear(year);

                    Cursor costCursor = db.query(CostDBColumn.TABLENAME, new String[]{"sum("+CostDBColumn.EVENT_COST+")"},
                            CostDBColumn.YEAR + " = ?",new String[]{year}, null, null, null);
                    if(costCursor != null && costCursor.moveToFirst()){
                        double cost = costCursor.getDouble(0);
                        bean.setEventCost(formatCost(cost));
                    }

                    costCursor.close();

                    data.add(bean);
                }

                yearCursor.close();
                helper.close();
                db.close();

                if(callback != null){
                    callback.complete(data);
                }
            }
        };
        thread.start();
    }

    public static void queryCostMonth(final Context context, final String year, final CostDBInterface.OnQueryCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                List<CostDBBean> data = new ArrayList<>();

                Cursor yearCursor = db.query(CostDBColumn.TABLENAME, new String[]{CostDBColumn._ID,CostDBColumn.MONTH},
                        CostDBColumn.YEAR +" = ?" , new String[]{String.valueOf(year)}, CostDBColumn.MONTH, null, null);
                while(yearCursor.moveToNext()){
                    CostDBBean bean = new CostDBBean();
                    bean.setId(yearCursor.getInt(yearCursor.getColumnIndex(CostDBColumn._ID)));

                    String month = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.MONTH));
                    bean.setEventName((month+1)+" 月");

                    bean.setYear(year);
                    bean.setMonth(month);

                    Cursor costCursor = db.query(CostDBColumn.TABLENAME, new String[]{"sum("+CostDBColumn.EVENT_COST+")"},
                            CostDBColumn.YEAR + " = ? and "+ CostDBColumn.MONTH +" = ?",new String[]{year, month}, null, null, null);
                    if(costCursor != null && costCursor.moveToFirst()){
                        double cost = costCursor.getDouble(0);
                        bean.setEventCost(formatCost(cost));
                    }

                    costCursor.close();

                    data.add(bean);
                }

                yearCursor.close();
                helper.close();
                db.close();

                if(callback != null){
                    callback.complete(data);
                }
            }
        };
        thread.start();
    }

    public static void queryCostYearAll(final Context context, final String year, final CostDBInterface.OnQueryCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                List<CostDBBean> data = new ArrayList<>();

                Cursor yearCursor = db.query(CostDBColumn.TABLENAME, new String[]{CostDBColumn._ID,CostDBColumn.EVENT_DATE,
                        CostDBColumn.EVENT_COST},CostDBColumn.YEAR +" = ?" , new String[]{String.valueOf(year)}, null, null, null);

                while(yearCursor.moveToNext()){
                    CostDBBean bean = new CostDBBean();
                    bean.setId(yearCursor.getInt(yearCursor.getColumnIndex(CostDBColumn._ID)));

                    String date = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_DATE));
                    bean.setEventName(date);

                    double cost = yearCursor.getDouble(yearCursor.getColumnIndex(CostDBColumn.EVENT_COST));
                    bean.setEventCost(formatCost(cost));

                    data.add(bean);
                }

                yearCursor.close();
                helper.close();
                db.close();

                if(callback != null){
                    callback.complete(data);
                }
            }
        };
        thread.start();
    }

    public static void queryCostYearMonthDay(final Context context, final String year, final String month, final CostDBInterface.OnQueryCost callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                List<CostDBBean> data = new ArrayList<>();

                final String[] projections = new String[]{
                        CostDBColumn._ID,
                        CostDBColumn.EVENT_DATE,
                        CostDBColumn.EVENT_NAME,
                        CostDBColumn.EVENT_COST,
                        CostDBColumn.EVENT_DESC
                };

                Cursor yearCursor = db.query(CostDBColumn.TABLENAME, projections,
                        CostDBColumn.YEAR +" = ? and "+ CostDBColumn.MONTH +" = ?",
                        new String[]{String.valueOf(year), String.valueOf(month)}, null, null, null);
                while(yearCursor.moveToNext()){
                    CostDBBean bean = new CostDBBean();
                    bean.setId(yearCursor.getInt(yearCursor.getColumnIndex(CostDBColumn._ID)));

                    String date = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_DATE));
                    String name = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_NAME));
                    bean.setEventName(name+"\n"+date);

                    String desc = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_DESC));
                    bean.setEventDesc(desc);

                    bean.setYear(year);
                    bean.setMonth(month);
                    bean.setEventDate(date);

                    double cost = yearCursor.getDouble(yearCursor.getColumnIndex(CostDBColumn.EVENT_COST));
                    bean.setEventCost(formatCost(cost));

                    data.add(bean);
                }

                yearCursor.close();
                helper.close();
                db.close();

                if(callback != null){
                    callback.complete(data);
                }
            }
        };
        thread.start();
    }

    public static void queryCostUseId(final Context context, final int id, final CostDBInterface.OnQueryCostUseId callback){
        Thread thread = new Thread(){
            @Override
            public void run() {
                CostDBHelper helper = new CostDBHelper(context);
                SQLiteDatabase db = helper.getWritableDatabase();

                final String[] projections = new String[]{
                        CostDBColumn._ID,
                        CostDBColumn.EVENT_DATE,
                        CostDBColumn.EVENT_NAME,
                        CostDBColumn.EVENT_COST,
                        CostDBColumn.EVENT_DESC,
                        CostDBColumn.YEAR,
                        CostDBColumn.MONTH
                };

                Cursor yearCursor = db.query(CostDBColumn.TABLENAME, projections,
                        CostDBColumn._ID +" = ? ",
                        new String[]{String.valueOf(id)}, null, null, null);
                if(yearCursor.moveToFirst()){
                    CostDBBean bean = new CostDBBean();
                    bean.setId(yearCursor.getInt(yearCursor.getColumnIndex(CostDBColumn._ID)));

                    String date = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_DATE));
                    bean.setEventName(yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_NAME)));

                    String desc = yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.EVENT_DESC));
                    bean.setEventDesc(desc);

                    bean.setYear(yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.YEAR)));
                    bean.setMonth(yearCursor.getString(yearCursor.getColumnIndex(CostDBColumn.MONTH)));
                    bean.setEventDate(date);

                    double cost = yearCursor.getDouble(yearCursor.getColumnIndex(CostDBColumn.EVENT_COST));
                    bean.setEventCost(formatCost(cost));

                    if(callback != null){
                        callback.complete(bean);
                    }
                }

                yearCursor.close();
                helper.close();
                db.close();
            }
        };
        thread.start();
    }
}
