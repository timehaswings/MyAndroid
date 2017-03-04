package com.android.administrator.simpleplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

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

    public static Bitmap getAlbumImage(Context context, String albumId){
        String albumArt = getAlbumArt(context, albumId);
        Bitmap bitmap = BitmapFactory.decodeFile(albumArt);
        if (bitmap != null){
            Log.d("zhou", "AlbumBitmapUtil--getAlbumImage--Bitmap:width:"+bitmap.getWidth()+",height:"+bitmap.getHeight());
        }
        return bitmap;
    }
}
