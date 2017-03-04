package com.android.administrator.simpleplayer;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.administrator.simpleplayer.adapter.ContentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    //
    private RadioGroup titleGroup;
    private ViewPager contentPager;
    private ContentPagerAdapter adapter;
    private int[] navIds = {R.id.title_nav_singemusic, R.id.title_nav_artist, R.id.title_nav_album, R.id.title_nav_dir};
    private RadioButton navButton01, navButton02, navButton03, navButton04;
    private int chooseId = R.id.title_nav_singemusic;

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleGroup = (RadioGroup) findViewById(R.id.titleGroup);
        titleGroup.setOnCheckedChangeListener(checkedChangeListener);

        contentPager = (ViewPager) findViewById(R.id.container_pager);
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
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            titleGroup.check(navIds[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
