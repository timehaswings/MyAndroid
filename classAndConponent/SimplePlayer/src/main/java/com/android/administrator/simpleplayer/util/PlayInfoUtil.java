package com.android.administrator.simpleplayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/2/1.
 */
public class PlayInfoUtil {

    private static final String PLAY_INDEX_FILENAME = "play_index";
    private static final String PLAY_STATUS_FILENAME = "play_status";

    public static void savePlayIndex(Context context, int playIndex){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLAY_INDEX_FILENAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt("PlayIndex", playIndex)
                .commit();
    }

    public static int getPlayIndex(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLAY_INDEX_FILENAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt("PlayIndex", -1);
    }

    public static void savePlayStatus(Context context, boolean isPlaying){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLAY_STATUS_FILENAME, Activity.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean("PlayStatus", isPlaying)
                .commit();
    }

    public static boolean getPlayStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PLAY_STATUS_FILENAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean("PlayStatus", false);
    }
}
