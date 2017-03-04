package com.weylen.mediaplayerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // MediaPlayer 媒体播放器
    // 1. 所有的播放操作都应该放在后台 Service
    // 2. Activity与Service双向交互 BroadcastReceiver
    // 3. 列表数据歌曲目录  ContentProvider

    private ListView listView;
    private MediaAdapter adapter;
    private MediaReceiver receiver;
    private SeekBar seekBar;

    public static final String PROGRESS_ACTION = "com.android.test.progress.Action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        listView = (ListView) findViewById(R.id.listView);
        // 设置空视图
        listView.setEmptyView(findViewById(R.id.emptyView));

        adapter = new MediaAdapter(this, null);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 启动服务
                Intent intent = new Intent(MainActivity.this, MediaService.class);
                intent.putExtra("action", "listClick");
                intent.putExtra("position", position);
                startService(intent);
            }
        });

        queryMedia();
    }

    private void queryMedia(){
        final String[] projections = new String[]{
                MediaStore.Audio.Media.DATA, // 歌曲的路径
                MediaStore.Audio.Media.DISPLAY_NAME, // 歌曲的文件名字
                MediaStore.Audio.Media.TITLE, // 歌曲的名字
                MediaStore.Audio.Media.ARTIST, // 艺术家
                MediaStore.Audio.Media.ALBUM, // 专辑名
                MediaStore.Audio.Media.SIZE, // 大小
                MediaStore.Audio.Media.DURATION // 歌曲时间 毫秒数
        };
        CursorLoader loader = new CursorLoader(this, // 上下文
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, // 查询的Uri
                projections, // 字段
                MediaStore.Audio.Media.DURATION + " > ?", // 条件
                new String[]{String.valueOf(60 * 1000)}, // 条件对应的值
                null); // 排序
        // 注册结果监听
        loader.registerListener(10, new Loader.OnLoadCompleteListener<Cursor>() {
            @Override
            public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
                doData(data);
            }
        });

        loader.startLoading();
    }

    /**
     * 处理查询结果
     * @param data
     */
    private void doData(Cursor data){
        List<MediaBean> mediaBeanList = new ArrayList<>();
        if (data != null){
            while( data.moveToNext()){
                MediaBean mediaBean = new MediaBean();
                mediaBean.setDisplayName(data.getString(data.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                mediaBean.setTitle(data.getString(data.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                mediaBean.setData(data.getString(data.getColumnIndex(MediaStore.Audio.Media.DATA)));
                mediaBean.setArtist(data.getString(data.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                mediaBean.setAlbum(data.getString(data.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                mediaBean.setSize(data.getString(data.getColumnIndex(MediaStore.Audio.Media.SIZE)));

                int duration = data.getInt(data.getColumnIndex(MediaStore.Audio.Media.DURATION));
                mediaBean.setDuration(formatMediaDuration(duration));
                mediaBeanList.add(mediaBean);
            }

            data.close();
        }

        // 保存数据
        DataInstance.getInstance().setData(mediaBeanList);
        // 刷新列表
        adapter.setData(mediaBeanList);
    }

    private boolean isReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        if (isReceiver == false){
            receiver = new MediaReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(PROGRESS_ACTION);
            registerReceiver(receiver, filter);
            isReceiver = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isReceiver){
            unregisterReceiver(receiver);
            isReceiver = false;
        }
    }

    /**
     * 转换音频时间 mm:ss
     * @param duration
     * @return
     */
    public static String formatMediaDuration(int duration){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss", Locale.CHINA);
        return dateFormat.format(new Date(duration));
    }

    /**
     * 更新进度
     * @param duration
     * @param currentDuration
     */
    private void updateProgress(int duration, int currentDuration){
        seekBar.setMax(duration);
        seekBar.setProgress(currentDuration);
    }

    private class MediaReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (PROGRESS_ACTION.equals(action)){
                int duration = intent.getIntExtra("Duration", -1);
                int currentDuration = intent.getIntExtra("CurrentDuration", -1);
                updateProgress(duration, currentDuration);
            }
        }
    }
}
