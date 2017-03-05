package com.weylen.fragmentwithviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 2016/4/13.
 */
public class FragmentViewPager extends Fragment{

    public static final String TAG = FragmentViewPager.class.getSimpleName();

    private ContentPagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(NumberFragment.getInstance(1));
        fragmentList.add(NumberFragment.getInstance(2));
        fragmentList.add(NumberFragment.getInstance(3));
        fragmentList.add(NumberFragment.getInstance(4));
        fragmentList.add(NumberFragment.getInstance(5));
        fragmentList.add(NumberFragment.getInstance(6));

        pagerAdapter = new ContentPagerAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, R.id.pager);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (pagerAdapter != null){
            pagerAdapter.clearCache();
        }
    }
}
