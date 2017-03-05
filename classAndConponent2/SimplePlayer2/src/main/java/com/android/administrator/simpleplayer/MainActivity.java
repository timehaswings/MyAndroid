package com.android.administrator.simpleplayer;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.administrator.simpleplayer.adapter.ContentPagerAdapter;
import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.fragment.SingeMusicPlayFragment;
import com.android.administrator.simpleplayer.service.PlayService;
import com.android.administrator.simpleplayer.service.PlayStatus;
import com.android.administrator.simpleplayer.util.AlbumBitmapUtil;
import com.android.administrator.simpleplayer.util.MediaObserver;
import com.android.administrator.simpleplayer.util.MusicScannerUtil;
import com.android.administrator.simpleplayer.util.PlayInfoUtil;
import com.android.administrator.simpleplayer.util.PlayListUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup titleGroup;
    private ViewPager contentPager;
    private ContentPagerAdapter adapter;
    private int[] navIds = {R.id.title_nav_singemusic, R.id.title_nav_artist, R.id.title_nav_album, R.id.title_nav_dir};
    private RadioButton navButton01, navButton02, navButton03, navButton04;
    private int chooseId = R.id.title_nav_singemusic;
    private ImageView tabSltView, playAlbumView;
    private DisplayMetrics displayMetrics;
    private TextView playTitleView, playArtistView;
    private ProgressBar playProgressBar;
    private PlayBroadReceiver receiver;
    private int lastPlayIndex = -1;
    private CheckBox playPauseBox;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 100){
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(adapter.getFragmentName(R.id.content_pager, 0));
                if(fragment != null){
                    SingeMusicPlayFragment singeMusicPlayFragment =  (SingeMusicPlayFragment)fragment;
                    singeMusicPlayFragment.dataChanged();
                }
            }
        }
    };

    private MediaObserver observer = new MediaObserver(handler);

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

        }
    };

    // 点击下一曲按钮的监听器
    private View.OnClickListener playNextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent service = new Intent(MainActivity.this, PlayService.class);
            service.putExtra(PlayStatus.PLAY_ACTION, PlayStatus.PLAY_NEXTPLAY);
            startService(service);
        }
    };

    // 点击播放暂停按钮的监听器
    private View.OnClickListener playPauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent service = new Intent(MainActivity.this, PlayService.class);
            service.putExtra(PlayStatus.PLAY_ACTION, PlayStatus.PLAY_PLAYPAUSE_CLICK);
            startService(service);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastPlayIndex = PlayInfoUtil.getPlayIndex(this);
        getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, observer);

        playAlbumView = (ImageView) findViewById(R.id.playAlbum);
        playTitleView = (TextView) findViewById(R.id.playTitle);
        playArtistView = (TextView) findViewById(R.id.playArtist);
        playProgressBar = (ProgressBar) findViewById(R.id.playProgressBar);

        // 播放暂停
        playPauseBox = (CheckBox) findViewById(R.id.playPauseBox);
        playPauseBox.setOnClickListener(playPauseClick);
        // 下一曲
        findViewById(R.id.playNext).setOnClickListener(playNextClick);

        titleGroup = (RadioGroup) findViewById(R.id.titleGroup);
        titleGroup.setOnCheckedChangeListener(checkedChangeListener);

        contentPager = (ViewPager) findViewById(R.id.content_pager);
        adapter = new ContentPagerAdapter(getSupportFragmentManager());
        contentPager.setAdapter(adapter);

        contentPager.setOnPageChangeListener(changeListener);

        navButton01 = (RadioButton) findViewById(R.id.title_nav_singemusic);
        navButton02 = (RadioButton) findViewById(R.id.title_nav_artist);
        navButton03 = (RadioButton) findViewById(R.id.title_nav_album);
        navButton04 = (RadioButton) findViewById(R.id.title_nav_dir);

        navButton01.setOnClickListener(navClick);
        navButton02.setOnClickListener(navClick);
        navButton03.setOnClickListener(navClick);
        navButton04.setOnClickListener(navClick);

        tabSltView = (ImageView) findViewById(R.id.tab_slt);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(displayMetrics.widthPixels / 4,
                (int) (2 * displayMetrics.density));
        tabSltView.setLayoutParams(layoutParams);

        initPlayButtonStatus();
    }

    // 初始化播放按钮的状态
    private void initPlayButtonStatus(){
        boolean isPlaying = PlayInfoUtil.getPlayStatus(this);
        playPauseBox.setChecked(!isPlaying);
    }

    // 初始化播放控制器的信息
    private void initPlayControllerInfo(List<MusicEntity> data, int index){
        if(data == null || index < 0 || data.size() == 0){
            return;
        }
        MusicEntity entity = data.get(index);
        playTitleView.setText(entity.getName());
        playArtistView.setText(entity.getArtist());
        // 设置专辑图片
        Bitmap bitmap = AlbumBitmapUtil.getAlbumImage(this, entity.getAlbumId(), 40, 40);
        if(bitmap == null){
            playAlbumView.setImageResource(R.mipmap.icn_nomusic);
        }else{
            playAlbumView.setImageBitmap(bitmap);
        }
    }

    public void onFragmentLoadData(List<MusicEntity> data, int playIndex){
        initPlayControllerInfo(data, playIndex);
    }

    private View.OnClickListener navClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == chooseId){
                return;
            }
            for(int i = 0 ; i < navIds.length; i++){
                if(id == navIds[i]){
                    contentPager.setCurrentItem(i, true);
                    break;
                }
            }
            chooseId = id;
        }
    };

    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override //1. 页面的下标 2.滑动的百分比 3。滑动的像素值
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            float x = displayMetrics.widthPixels / 4 * position + positionOffset * displayMetrics.widthPixels / 4;
            tabSltView.setX(x);
        }

        @Override
        public void onPageSelected(int position) {
            chooseId = navIds[position];
            titleGroup.check(navIds[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(receiver == null){
            receiver = new PlayBroadReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(PlayStatus.ALTER_PLAYCONTROLLER);
            registerReceiver(receiver, filter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }

    // 修改播放信息
    private void alterPlayController(int playIndex, boolean isPlaying){
        // 如果是播放状态则显示暂停图标
        playPauseBox.setChecked(!isPlaying);
        if(playIndex != lastPlayIndex){
            initPlayControllerInfo(PlayListUtil.getPlayList(this), playIndex);
            getContentResolver().notifyChange(ContentUris.withAppendedId(PlayStatus.PLAY_STATUS_URI, playIndex), null);
        }
        lastPlayIndex = playIndex;
    }

    private class PlayBroadReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(PlayStatus.ALTER_PLAYCONTROLLER)){
                int playIndex = intent.getIntExtra(PlayStatus.PLAY_INDEX, -2);
                boolean isPlaying = intent.getBooleanExtra(PlayStatus.PLAY_ISPLAYING, false);
                alterPlayController(playIndex, isPlaying);
            }
        }
    }
}

