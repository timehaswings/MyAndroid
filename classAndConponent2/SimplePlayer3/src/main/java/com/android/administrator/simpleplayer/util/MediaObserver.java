package com.android.administrator.simpleplayer.util;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2016/2/1.
 */
public class MediaObserver extends ContentObserver{

    private Handler handler;
    public MediaObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Message message = Message.obtain();
        message.what = 100;
        handler.sendMessage(message);
    }
}
