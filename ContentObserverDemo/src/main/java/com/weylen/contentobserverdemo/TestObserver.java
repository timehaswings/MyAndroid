package com.weylen.contentobserverdemo;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by zhou on 2016/4/18.
 */
public class TestObserver extends ContentObserver{


    private Handler handler;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public TestObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d("zhou", "onChange: 这是第一种");
        sendMessage();
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d("zhou", "onChange: 这是第二种:uri"+uri);
    }

    public void sendMessage(){
        Message message = Message.obtain();
        message.arg1 = 10;
        handler.sendMessage(message);
    }
}
