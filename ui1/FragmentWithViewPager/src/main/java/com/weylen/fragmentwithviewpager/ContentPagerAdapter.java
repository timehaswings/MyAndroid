package com.weylen.fragmentwithviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class ContentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;
    private int viewId;
    private FragmentManager fm;
    public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, int containerId) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fm = fm;
        this.viewId = containerId;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    public static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    public void clearCache() {
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0; i < getCount(); i++){
            String name = ContentPagerAdapter.makeFragmentName(viewId, i);
            Fragment fragment = fm.findFragmentByTag(name);
            if (fragment != null){
                ft.remove(fragment);
            }
        }
        ft.commit();
    }
}
