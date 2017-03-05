package com.android.administrator.simpleplayer.util;

import android.content.ContentUris;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2016/2/1.
 */
public class PlayStatusObserver extends ContentObserver{

    private Handler handler;
    public PlayStatusObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        // Log.d("zhou", "PlayStatusObserver--onChange--27--Uri:"+uri);
        long playIndex = ContentUris.parseId(uri);
        Message message = Message.obtain();
        message.what = 1;
        message.arg1 = (int) playIndex;
        handler.sendMessage(message);
    }
}
