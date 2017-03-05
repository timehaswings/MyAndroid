package com.android.administrator.simpleplayer.util;

import android.content.Context;
import android.util.Log;

import com.android.administrator.simpleplayer.entity.MusicEntity;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/2/1.
 * 通过Files的方式保存数据
 */
public class PlayListUtil {

    private static final String PLAY_LIST_FILENAME = "play_list.file";
    public static void savePlayList(Context context, List<MusicEntity> data){
        try {
            OutputStream outputStream = context.openFileOutput(PLAY_LIST_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.d("zhou", "PlayListUtil--savePlayList--保存歌曲列表失败："+e.getMessage());
        }
    }

    public static List<MusicEntity> getPlayList(Context context){
        try {
            InputStream inputStream = context.openFileInput(PLAY_LIST_FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            List<MusicEntity> data = (List<MusicEntity>) objectInputStream.readObject();
            return data;
        } catch (Exception e) {
            Log.d("zhou", "PlayListUtil--getPlayList--获取歌曲列表失败："+e.getMessage());
        }
        return null;
    }
}
