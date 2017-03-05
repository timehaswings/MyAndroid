package com.android.administrator.simpleplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

/**
 * Created by Administrator on 2016/2/1.
 */
public class AlbumBitmapUtil {

    // 获取保存专辑图片的路径
    private static String getAlbumArt(Context context, String albumId) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[] { "album_art" };
        Cursor cur = context.getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + albumId),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }

    public static Bitmap getAlbumImage(Context context, String albumId, int dstWidth, int dstHeight, boolean isCache){
        Bitmap bitmap = null;
        if(isCache){
            bitmap = BitmapCacheUtil.getInstance().getBitmap(albumId);
            if(bitmap != null){
                return bitmap;
            }
        }

        String albumArt = getAlbumArt(context, albumId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 表示现在直接图片的大小，此时返回的Bitmap对象是为null的
        BitmapFactory.decodeFile(albumArt, options);
        int widthRatio = options.outWidth / dstWidth;
        int heightRatio = options.outHeight / dstHeight;
        if(widthRatio >= 2 && heightRatio >= 2){
            options.inSampleSize = Math.min(widthRatio, heightRatio);
        }
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(albumArt, options);

        Matrix matrix = new Matrix();
        float sWidth = ((float)(dstWidth)) / bitmap.getWidth();
        float sHeight= ((float)(dstHeight)) / bitmap.getHeight();
        if(sWidth >=1 && sHeight >=1){
            BitmapCacheUtil.getInstance().saveBitmap(albumId, bitmap);
            return bitmap;
        }
        float ss = Math.min(sWidth, sHeight);
        matrix.setScale(ss, ss);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        BitmapCacheUtil.getInstance().saveBitmap(albumId, bitmap);

        return bitmap;
    }
}
