package com.android.administrator.simpleplayer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.android.administrator.simpleplayer.fragment.SingeMusicPlayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> dataFragment;

    public ContentPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        dataFragment = new ArrayList<>();
        SingeMusicPlayFragment fragment = new SingeMusicPlayFragment();
        dataFragment.add(fragment);

        fragment = new SingeMusicPlayFragment();
        dataFragment.add(fragment);

        fragment = new SingeMusicPlayFragment();
        dataFragment.add(fragment);

        fragment = new SingeMusicPlayFragment();
        dataFragment.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return dataFragment.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
