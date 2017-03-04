package com.android.administrator.simpleplayer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.administrator.simpleplayer.adapter.ContentPagerAdapter;
import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.fragment.SingeMusicPlayFragment;
import com.android.administrator.simpleplayer.util.MediaObserver;
import com.android.administrator.simpleplayer.util.MusicScannerUtil;
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

        }
    };

    // 点击播放暂停按钮的监听器
    private View.OnClickListener playPauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContentResolver().registerContentObserver(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, true, observer);

        playAlbumView = (ImageView) findViewById(R.id.playAlbum);
        playTitleView = (TextView) findViewById(R.id.playTitle);
        playArtistView = (TextView) findViewById(R.id.playArtist);
        playProgressBar = (ProgressBar) findViewById(R.id.playProgressBar);

        // 播放暂停
        findViewById(R.id.playPauseBox).setOnClickListener(playPauseClick);
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

        initPlayControllerInfo(null, 0);
    }

    private boolean isSavePlayStatus;// 记录是否保存了播放的状态
    // 初始化播放控制器的信息
    private void initPlayControllerInfo(List<MusicEntity> data, int index){
        //TODO 当记录了播放的信息 要记得修改 目前先显示第一首歌曲的信息
        if(data == null || index < 0 || data.size() == 0){
            return;
        }
        MusicEntity entity = data.get(index);
        playTitleView.setText(entity.getName());
        playArtistView.setText(entity.getArtist());
        //TODO 设置专辑图片
    }

    public void onFragmentLoadData(List<MusicEntity> data){
        if(!isSavePlayStatus){
            initPlayControllerInfo(data, 0);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}

