package com.weylen.customproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhou on 2016/4/18.
 */
public class MyProvider extends ContentProvider{

    public static final String AUTHORITY = "com.weylen.customproviderdemo";

    private static final Uri AUTHORITY_URI = Uri.parse("content://"+AUTHORITY);

    public static final Uri CONTENT_URI = AUTHORITY_URI;

    public static final String TABLE_NAME = "person_info";

    /**
     * UriMatcher是用来检测Uri的类型
     */
    private static UriMatcher uriMatcher;
    private MyHelper myHelper;

    /**
     * 静态块 进行初始化操作
     * 在类加载时执行并且仅执行一次
     */
    static{
        Log.d("zhou", "static initializer: 执行静态块");
        // 1.参数：表示当没有任何相匹配的Uri返回的值
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // *用来匹配任意的文本 ＃号用来匹配任意的数字。 先添加先检测
        // 参数：1.authority 2.追加的路径 3.返回的值
        // content://com.weylen.customproviderdemo/person_info
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, 1);// 添加相匹配的Uri
        // content://com.weylen.customproviderdemo/person_info/1
        uriMatcher.addURI(AUTHORITY, TABLE_NAME+"/#", 2);// 添加相匹配的Uri
        // content://com.weylen.customproviderdemo/person_info/as
        uriMatcher.addURI(AUTHORITY, TABLE_NAME+"/*", 3);// 添加相匹配的Uri
    }

    @Override // 创建Provider时调用 要创建成功必须返回true
    public boolean onCreate() {
//        //*********for test**********
//        int code1 = uriMatcher.match(Uri.parse("111224eqedad"));
//        int code2 = uriMatcher.match(Uri.parse("content://"+AUTHORITY+"/person_info"));
//        int code3 = uriMatcher.match(Uri.parse("content://"+AUTHORITY+"/person_info/123"));
//        int code4 = uriMatcher.match(Uri.parse("content://"+AUTHORITY+"/person_info/dnoa"));
//        Log.d("zhou", "onCreate: code1:"+code1+",code2:"+code2+",code3:"+code3+",code4:"+code4);
        return true;
    }

    @Nullable
    @Override // 查询数据
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MyHelper myHelper = new MyHelper(getContext());
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String type = getType(uri);
        if (type.equals("root")){
            return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override // 根据Uri获取不同的类型 这些类型都是自定义的
    public String getType(Uri uri) {
        int code = uriMatcher.match(uri);
        String type;
        switch (code){
            case 1:
                type = "root";
                break;
            case 2:
                type = "number";
                break;
            case 3:
            case -1:
            default:type = "default";
                break;
        }
        return type;
    }

    @Nullable
    @Override // 增加数据
    public Uri insert(Uri uri, ContentValues values) {
        MyHelper myHelper = new MyHelper(getContext());
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String type = getType(uri);
        if (type.equals("root")){
            // 返回的long型值表示的新增加数据的id
            long id = db.insert(TABLE_NAME, null, values);
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override // 删除数据
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override // 更新数据
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
