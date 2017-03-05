package com.android.administrator.simpleplayer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.service.PlayService;
import com.android.administrator.simpleplayer.service.PlayStatus;
import com.android.administrator.simpleplayer.util.AlbumBitmapUtil;
import com.android.administrator.simpleplayer.util.BitmapUtil;
import com.android.administrator.simpleplayer.util.MusicScannerUtil;
import com.android.administrator.simpleplayer.util.PlayInfoUtil;
import com.android.administrator.simpleplayer.util.PlayListUtil;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private RelativeLayout playRotationLayout;
    private ImageView playNeedle, playAlbumImage;
    private TextView titleNameView, titleArtistView, playTimeView, allTimeView;
    private SeekBar seekBar;
    private List<MusicEntity> playListData;
    private int playIndex;
    private CheckBox playPauseBox;
    private PlayBroadReceiver receiver;
    private boolean isPlaying = true;
    private boolean isTouchSeekBar;

    private SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                playTimeView.setText(MusicScannerUtil.formatDuration(String.valueOf(progress)));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isTouchSeekBar = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isTouchSeekBar = false;
            int progress = seekBar.getProgress();
            Intent intent = new Intent(PlayActivity.this, PlayService.class);
            intent.putExtra(PlayStatus.PLAY_ACTION, PlayStatus.PLAY_SEEK);
            intent.putExtra(PlayStatus.PLAY_CURRENT_TIME, progress);
            startService(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_layout);

        titleNameView = (TextView) findViewById(R.id.title_name);
        titleArtistView = (TextView) findViewById(R.id.title_artist);
        playListData = PlayListUtil.getPlayList(this);
        playIndex = PlayInfoUtil.getPlayIndex(this);
        if(playListData != null && playIndex >=0 && playIndex < playListData.size()){
            MusicEntity musicEntity = playListData.get(playIndex);
            titleNameView.setText(musicEntity.getName());
            titleArtistView.setText(musicEntity.getArtist());
        }

        playPauseBox = (CheckBox) findViewById(R.id.playPauseBox);
        playPauseBox.setOnClickListener(playPauseClick);

        playRotationLayout = (RelativeLayout) findViewById(R.id.playLayout);
        playNeedle = (ImageView) findViewById(R.id.playNeedle);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Log.d("zhou", "PlayActivity--onCreate--62--"+displayMetrics.widthPixels / 2);
        playNeedle.setX(displayMetrics.widthPixels / 2 - 20);

        playAlbumImage = (ImageView) findViewById(R.id.playAlbumImage);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(changeListener);
        playTimeView = (TextView) findViewById(R.id.playTime);
        allTimeView = (TextView) findViewById(R.id.allTime);

        initAllTime(playIndex);
    }

    // 初始化总的时间
    private void initAllTime(int playIndex){
        if(playListData != null && playIndex >=0 && playIndex < playListData.size()){
            allTimeView.setText(playListData.get(playIndex).getDuration());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initAlbumImage(playIndex);
    }

    private void initAlbumImage(int playIndex){
        if(playListData != null && playIndex >=0 && playIndex < playListData.size()){
            MusicEntity musicEntity = playListData.get(playIndex);
            Bitmap bitmap = AlbumBitmapUtil.getAlbumImage(this, musicEntity.getAlbumId(),
                    playRotationLayout.getWidth() -20, playRotationLayout.getHeight() -20,false);
            bitmap = BitmapUtil.toRoundBitmap(bitmap);
            playAlbumImage.setImageBitmap(bitmap);
        }
    }

    private void initPlayNeedle(){
        boolean isPlaying = PlayInfoUtil.getPlayStatus(this);
        playPauseBox.setChecked(!isPlaying);
        if(isPlaying == false){
            playNeedle.setPivotX(0);
            playNeedle.setPivotY(0);
            playNeedle.setRotation(-15);
        }else{
            playLayoutRotation();
        }
        this.isPlaying = isPlaying;
    }

    private void playNeedleShunshizhenRotation(){
        playNeedle.setPivotX(0);
        playNeedle.setPivotY(0);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.play_needle_rotate_shunshizhen);
        animator.setTarget(playNeedle);
        animator.start();
    }

    private void playNeedleNishizhenRotation(){
        playNeedle.setPivotX(0);
        playNeedle.setPivotY(0);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.play_needle_rotate_nishizhen);
        animator.setTarget(playNeedle);
        animator.start();
    }

    private ValueAnimator rotationAnimator;
    private void playLayoutRotation(){
        float degrees = playRotationLayout.getRotation();
        rotationAnimator = ValueAnimator.ofFloat(degrees, 360*20);
        rotationAnimator.setDuration(20000*20);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                playRotationLayout.setRotation(value);
            }
        });
        rotationAnimator.start();
    }

    // 点击播放暂停按钮的监听器
    private View.OnClickListener playPauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent service = new Intent(PlayActivity.this, PlayService.class);
            service.putExtra(PlayStatus.PLAY_ACTION, PlayStatus.PLAY_PLAYPAUSE_CLICK);
            startService(service);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(receiver == null){
            receiver = new PlayBroadReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(PlayStatus.ALTER_PLAYCONTROLLER);
            filter.addAction(PlayStatus.PLAY_TIME_UPDATE);
            registerReceiver(receiver, filter);
        }
        initPlayNeedle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    // 修改播放信息
    private void alterPlayController(int playIndex, boolean isPlaying){
        // 如果是播放状态则显示暂停图标
        playPauseBox.setChecked(!isPlaying);
        this.playIndex = playIndex;
        if(this.isPlaying != isPlaying){
            if(isPlaying){
                if(rotationAnimator == null){
                    playLayoutRotation();
                }
                playNeedleShunshizhenRotation();
            }else{
                if(rotationAnimator != null){
                    rotationAnimator.cancel();
                    rotationAnimator = null;
                }
                playNeedleNishizhenRotation();
            }
            this.isPlaying = isPlaying;
        }
    }

    private void initCurrentTime(int currentTime, int allTime){
        if(!isTouchSeekBar){
            seekBar.setMax(allTime);
            seekBar.setProgress(currentTime);
            playTimeView.setText(MusicScannerUtil.formatDuration(String.valueOf(currentTime)));
        }
    }

    private class PlayBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(PlayStatus.ALTER_PLAYCONTROLLER)){
                int playIndex = intent.getIntExtra(PlayStatus.PLAY_INDEX, -2);
                boolean isPlaying = intent.getBooleanExtra(PlayStatus.PLAY_ISPLAYING, false);
                alterPlayController(playIndex, isPlaying);
            }else if(action.equals(PlayStatus.PLAY_TIME_UPDATE)){
                int currentTime = intent.getIntExtra(PlayStatus.PLAY_CURRENT_TIME, -1);
                int allTime = intent.getIntExtra(PlayStatus.PLAY_ALLTIME, -1);
                initCurrentTime(currentTime, allTime);
            }
        }
    }
}
