package com.android.administrator.simpleplayer.service;

import android.net.Uri;

/**
 * Created by Administrator on 2016/2/1.
 */
public class PlayStatus {

    public static final String SERVICE_ACTION = "com.zyh.android.music.playservice";

    public static final String PLAY_INDEX = "play_index";
    public static final String PLAY_ACTION = "play_action";
    public static final String PLAY_ISPLAYING = "play_isplaying";
    public static final int PLAY_LISTCLICK = 1;
    public static final int PLAY_PLAYPAUSE_CLICK = 2;
    public static final int PLAY_NEXTPLAY = 3;

    // 自定义一个状态变化的Observer
    public static final Uri PLAY_STATUS_URI = Uri.parse("content://"+SERVICE_ACTION);

    // 广播的Action
    public static final String ALTER_PLAYCONTROLLER = "com.broad.alter_playcontroller";
}
