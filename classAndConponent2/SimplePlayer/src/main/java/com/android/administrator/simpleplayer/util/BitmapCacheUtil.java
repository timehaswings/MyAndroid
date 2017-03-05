package com.android.administrator.simpleplayer.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2016/2/2.
 */
public class BitmapCacheUtil {

    private BitmapCacheUtil(){}

    private static BitmapCacheUtil instance;

    public static BitmapCacheUtil getInstance(){
        if(instance == null){
            instance = new BitmapCacheUtil();
        }
        return instance;
    }

    private static final int MAX_SIZE = 2 * 1024 * 1024;
    // 1.参数表示最大能够保存图片的容量 单位是byte
    private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(MAX_SIZE){
        @Override // 获取每一个对象的大小
        protected int sizeOf(String key, Bitmap value) {
            int count = value.getByteCount();
            return count;
        }
    };

    public void saveBitmap(String name, Bitmap bitmap){
        cache.put(name, bitmap);
    }

    public Bitmap getBitmap(String name){
        return cache.get(name);
    }
}
