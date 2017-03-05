package com.weylen.asynctaskdemo;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by zhou on 2016/4/29.
 */
public class CacheUtil {
    private static CacheUtil ourInstance = new CacheUtil();

    public static CacheUtil getInstance() {
        return ourInstance;
    }

    private CacheUtil() {
    }

    private static final int MAX_SIZE = 2 * 1024 * 1024;
    // 1.参数表示最大保存的size，如果保存的文件超过了2M 则会自动优先删除没有使用的对象
    private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(MAX_SIZE){
        @Override // 当保存一个对象时，计算这个对象的大小
        protected int sizeOf(String key, Bitmap value) {
            // value.getByteCount() == value.getRowBytes() * value.getHeight();
            return value == null ? 0 : value.getByteCount();
        }
    };

    public void setBitmap(String key, Bitmap value){
        lruCache.put(key, value);
    }

    public Bitmap getBitmap(String key){
        return lruCache.get(key);
    }
}
