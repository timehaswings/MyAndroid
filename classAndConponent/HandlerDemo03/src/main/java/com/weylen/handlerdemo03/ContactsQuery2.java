package com.weylen.handlerdemo03;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2016/4/19.
 */
public class ContactsQuery2 {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (listener != null){
                listener.onQueryComplete((List<String>) msg.obj);
            }
        }
    };

    /**
     * 查询完成监听器
     */
    public interface OnQueryCompleteListener{
        /**
         * 当有结果返回的时候调用此方法
         * @param data 查询的结果
         */
        void onQueryComplete(List<String> data);
    }

    private OnQueryCompleteListener listener;

    public void query(Context context, OnQueryCompleteListener listener){
        this.listener = listener;
        new QueryThread(context).start();
    }

    public class QueryThread extends Thread{

        private List<String> contactsNameList;
        private Context context;
        public QueryThread(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            contactsNameList = new ArrayList<>();
            // 执行查询操作
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI,
                    new String[]{ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY},
                    null,
                    null,
                    null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    String name = cursor.getString(0);
                    contactsNameList.add(name);
                }
                cursor.close();
            }

            Message message = Message.obtain();
            message.obj = contactsNameList;
            handler.sendMessage(message);
        }
    }
}
