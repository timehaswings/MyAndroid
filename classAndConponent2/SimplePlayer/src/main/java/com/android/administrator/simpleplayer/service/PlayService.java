package com.android.administrator.simpleplayer.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.util.MediaObserver;
import com.android.administrator.simpleplayer.util.MusicScannerUtil;
import com.android.administrator.simpleplayer.util.PlayInfoUtil;
import com.android.administrator.simpleplayer.util.PlayListUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/2/1.
 */
public class PlayService extends Service{

    private int lastPlayIndex = -1; // 上一次播放下标

    // 1.创建播放对象 2.设置播放来源 3.准备播放 4.开始播放
    private MediaPlayer mediaPlayer;// 播放对象
    private List<MusicEntity> data;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 100){
                MusicScannerUtil.startScan(PlayService.this, new MusicScannerUtil.OnScanCompletedListener() {
                    @Override
                    public void onCompleted(List<MusicEntity> data) {
                        PlayService.this.data = data;
                        PlayListUtil.savePlayList(PlayService.this, data);
                    }
                });
            }
        }
    };

    private MediaObserver observer = new MediaObserver(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, observer);
        // 初始化保存歌曲的下标
        lastPlayIndex = PlayInfoUtil.getPlayIndex(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            // 取得播放命令
            int action = intent.getIntExtra(PlayStatus.PLAY_ACTION, -1);
            switch (action){
                case PlayStatus.PLAY_LISTCLICK:
                    int playIndex = intent.getIntExtra(PlayStatus.PLAY_INDEX, -2);
                    doPlayListClick(playIndex);
                    break;
                case PlayStatus.PLAY_PLAYPAUSE_CLICK:
                    doPlayPauseClick();
                    break;
                case PlayStatus.PLAY_NEXTPLAY:
                    doPlayNext();
                    break;
                case PlayStatus.PLAY_SEEK:
                    int currentTime = intent.getIntExtra(PlayStatus.PLAY_CURRENT_TIME, -1);
                    doPlaySeek(currentTime);
                    break;
            }
        }

        return Service.START_REDELIVER_INTENT;
    }

    private void doPlaySeek(int currentTime){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(currentTime);
        }
    }

    // 处理列表点击事件
    private void doPlayListClick(int playIndex){
        if(playIndex < 0){
            throw new IllegalArgumentException("参数异常，playIndex < 0 : playIndex === "+ playIndex);
        }
        if(lastPlayIndex == -1 || playIndex != lastPlayIndex){ // 表示第一点击
            // 播放
            play(playIndex);
        }else{
            if(mediaPlayer == null){
                play(playIndex);
            }else{
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
                sendAlterController(playIndex); // 发送修改播放控制器的信息广播
            }
        }
    }

    // 执行播放操作
    private void play(final int playIndex){
        ensurePlayData();
        resetPlayer(); // 重置对象
        mediaPlayer = new MediaPlayer();
        try {
            // 设置播放资源
            mediaPlayer.setDataSource(data.get(playIndex).getPath());
            // 同步准备，主要用于准备本地资源
//            mediaPlayer.prepare();
            // 异步准备，主要用于准备网络资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();// 开始播放
                    // 保存播放的下标
                    PlayInfoUtil.savePlayIndex(PlayService.this, playIndex);

                    lastPlayIndex =  playIndex;// 保存播放的下标
                    sendAlterController(playIndex); // 发送修改播放控制器的信息广播
                    new TimeUpdateThread().start();
                }
            });
        } catch (IOException e) {
            Log.d("zhou", "PlayService--play--设置播放资源失败："+e.getMessage());
        }

    }

    // 执行点击播放暂停图标的操作
    private void doPlayPauseClick(){
        if(lastPlayIndex == -1){
            lastPlayIndex = 0;
        }
        if(mediaPlayer == null){
            play(lastPlayIndex);
        }else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }else{
                mediaPlayer.start();
                new TimeUpdateThread().start();
            }
            sendAlterController(lastPlayIndex); // 发送修改播放控制器的信息广播
        }
    }

    private void doPlayNext(){
        ensurePlayData();
        if(lastPlayIndex == -1){
            lastPlayIndex = 0;
        }else if(lastPlayIndex == data.size() - 1){
            lastPlayIndex = 0;
        }else{
            lastPlayIndex++;
        }

        play(lastPlayIndex);
    }

    // 执行暂停
    private void pause(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void resetPlayer(){
        if(mediaPlayer != null){
            mediaPlayer.stop(); // 停止
            mediaPlayer.reset(); // 重置
            mediaPlayer.release(); // 销毁
            mediaPlayer = null;
        }
    }

    private void ensurePlayData(){
        if(data == null){
            data = PlayListUtil.getPlayList(this);
        }
    }

    private void sendAlterController(int playIndex){
        // 保存播放状态
        boolean isPlaying = mediaPlayer != null && mediaPlayer.isPlaying() ? true : false;
        PlayInfoUtil.savePlayStatus(this, isPlaying);

        Intent intent = new Intent(PlayStatus.ALTER_PLAYCONTROLLER);
        // 封装播放的下标
        intent.putExtra(PlayStatus.PLAY_INDEX, playIndex);
        // 封装播放状态
        intent.putExtra(PlayStatus.PLAY_ISPLAYING, isPlaying);
        // 发送广播
        sendBroadcast(intent);
    }

    private void sendTimeUpdate(int currentDuration, int allTime){
        Intent intent = new Intent(PlayStatus.PLAY_TIME_UPDATE);
        intent.putExtra(PlayStatus.PLAY_CURRENT_TIME, currentDuration);
        intent.putExtra(PlayStatus.PLAY_ALLTIME, allTime);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }

    private class TimeUpdateThread extends Thread{
        @Override
        public void run() {
            while(mediaPlayer != null && mediaPlayer.isPlaying()){
                int duration = mediaPlayer.getCurrentPosition();// 获取当前播放的时间
                int allTime = mediaPlayer.getDuration();// 获取音频总的时间
                sendTimeUpdate(duration, allTime);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
