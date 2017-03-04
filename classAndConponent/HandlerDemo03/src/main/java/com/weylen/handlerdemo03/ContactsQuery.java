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
public class ContactsQuery {

    public void query(Context context, Handler handler){
        new QueryThread(context, handler).start();
    }

    public class QueryThread extends Thread{

        private List<String> contactsNameList;
        private Context context;
        private Handler handler;
        public QueryThread(Context context, Handler handler){
            this.context = context;
            this.handler = handler;
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
            // 发送消息
            Message message = Message.obtain();
            message.obj = contactsNameList; // 封装数据
            // 传递数据
            if (handler != null){
                handler.sendMessage(message);
            }
        }
    }
}
