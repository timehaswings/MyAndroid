package com.weylen.mediaplayerdemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhou on 2016/4/22.
 */
public class MediaService extends Service{
    private int lastPosition = -1; // 记录上一次播放的下标

    private MediaPlayer mediaPlayer; // 媒体播放类对象

    private List<MediaBean> mediaData = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        if ("listClick".equals(action)){ // 表示列表点击
            int position = intent.getIntExtra("position", -2);
            doListClick(position);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 处理列表点击操作
     * @param position
     */
    private void doListClick(int position){
        if (position != lastPosition){
            if (position >= 0){
                // 进行新的播放操作
                doPlay(position);
            }
        }else{
            if (mediaPlayer == null){
                doPlay(position);
            }else{
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                    startProgressThread();
                }
            }
        }
        lastPosition = position;
    }

    /**
     * 重置播放
     */
    private void resetPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 执行播放操作
     * @param position
     */
    private void doPlay(int position){
        resetPlayer();
        ensureMediaData();
        mediaPlayer = new MediaPlayer();
        try {
            // 设置播放资源
            mediaPlayer.setDataSource(mediaData.get(position).getData());
            // 准备播放
//            mediaPlayer.prepare(); // 在主线程准备
            mediaPlayer.prepareAsync();// 在异步线程准备
            // 准备完成的监听
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();// 播放
                    startProgressThread();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UpdateProgress progressThread;
    private void startProgressThread(){
        if (progressThread != null && progressThread.isAlive()){
            return ;
        }
        progressThread = new UpdateProgress();
        progressThread.start();
    }

    private void ensureMediaData(){
        if (mediaData == null){
            mediaData = DataInstance.getInstance().getData();
        }
    }

    /**
     * 发送进度广播
     */
    private void sendProgressBroad(int duration, int currentDuration){
        Intent intent = new Intent(MainActivity.PROGRESS_ACTION);
        intent.putExtra("Duration", duration);
        intent.putExtra("CurrentDuration", currentDuration);
        this.sendBroadcast(intent);
    }

    private class UpdateProgress extends Thread{
        @Override
        public void run() {
            while(mediaPlayer != null && mediaPlayer.isPlaying()){
                int duration = mediaPlayer.getDuration(); // 歌曲的总时间
                int currentDuration = mediaPlayer.getCurrentPosition(); // 当前时间
                sendProgressBroad(duration, currentDuration);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
