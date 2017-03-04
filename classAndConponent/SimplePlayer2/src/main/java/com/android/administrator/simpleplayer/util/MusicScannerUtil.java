package com.android.administrator.simpleplayer.util;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.entity.MusicEntityByArtist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MusicScannerUtil {

    private static final Uri mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String[] projections = new String[]{
            MediaStore.Audio.Media.DATA, // 音频路径
            MediaStore.Audio.Media.DURATION, // 毫秒数的时间
            MediaStore.Audio.Media.TITLE, // 歌曲名字
            MediaStore.Audio.Media.ARTIST, // 歌手名
            MediaStore.Audio.Media.ALBUM, // 专辑名
            MediaStore.Audio.Media.ALBUM_ID, // 专辑id
            MediaStore.Audio.Media._ID // id

    };

    public interface OnScanCompletedListener{
        void onCompleted(List<MusicEntity> data);
    }

    public interface OnScanByArtistCompletedListener{
        void onCompleted(List<MusicEntityByArtist> data);
    }

    public static void startScan(Context context, final OnScanCompletedListener callback){
        CursorLoader loader = new CursorLoader(context, mediaUri, projections, MediaStore.Audio.Media.DURATION +" > ?",
                new String[]{String.valueOf(60 * 1000)}, null);

        loader.registerListener(100, new Loader.OnLoadCompleteListener<Cursor>() {
            @Override
            public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
                List<MusicEntity> list = new ArrayList<MusicEntity>();
                while(data != null && data.moveToNext()){
                    MusicEntity entity = new MusicEntity();
                    entity.setId(data.getString(data.getColumnIndex(MediaStore.Audio.Media._ID)));
                    entity.setPath(data.getString(data.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    String duration = data.getString(data.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    entity.setDuration(formatDuration(duration));
                    entity.setArtist(data.getString(data.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    entity.setAlbum(data.getString(data.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    entity.setName(data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    entity.setAlbumId(data.getString(data.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    list.add(entity);
                }
                if(callback != null){
                    callback.onCompleted(list);
                }
            }
        });
        loader.startLoading();
    }

    public static String formatDuration(String duration){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.CHINA);
        return format.format(new Date(Long.parseLong(duration)));
    }

    public static void scanMusicByArtist(Context context, OnScanByArtistCompletedListener callback){
        new ScanMusciByArtistThread(context, callback).start();
    }

    private static class ScanMusciByArtistThread extends Thread{

        private Context context;
        private OnScanByArtistCompletedListener callback;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(callback != null){
                    callback.onCompleted((List<MusicEntityByArtist>) msg.obj);
                }
            }
        };

        ScanMusciByArtistThread(Context context, OnScanByArtistCompletedListener callback){
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void run() {
            String[] artistProjections = new String[]{
                    MediaStore.Audio.Media.ARTIST // 歌手名
            };
            Cursor cursor = context.getContentResolver().query(mediaUri, artistProjections, MediaStore.Audio.Media.DURATION +" > ?",
                    new String[]{String.valueOf(60 * 1000)}, null);
            List<MusicEntityByArtist> result = new ArrayList<>();

            while(cursor != null && cursor.moveToNext()){
                MusicEntityByArtist musicEntityByArtist = new MusicEntityByArtist();

                String artistName = cursor.getString(0);
                Cursor artistCursor = context.getContentResolver().query(mediaUri, projections, MediaStore.Audio.Media.ARTIST +" = ?",
                        new String[]{artistName}, null);
                List<MusicEntity> list = new ArrayList<MusicEntity>();
                while(artistCursor != null && artistCursor.moveToNext()){
                    MusicEntity entity = new MusicEntity();
                    entity.setId(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    entity.setPath(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    String duration = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    entity.setDuration(formatDuration(duration));
                    entity.setArtist(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    entity.setAlbum(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    entity.setName(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    entity.setAlbumId(artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    list.add(entity);
                }

                musicEntityByArtist.setArtist(artistName);
                musicEntityByArtist.setCount(list.size());
                musicEntityByArtist.setData(list);
                result.add(musicEntityByArtist);
            }

            Message message = Message.obtain();
            message.obj = result;
            handler.sendMessage(message);
        }
    }
}
