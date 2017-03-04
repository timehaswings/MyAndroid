package com.zyh.android.withthenotes.util;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by Administrator on 2016/1/16 0016.
 */
public class CostContentObserver extends ContentObserver{

    private Handler handler;
    public CostContentObserver(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        handler.sendEmptyMessage(2);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}
