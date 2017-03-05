package com.weylen.viewpagerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ViewPager

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private int[] rdIds = new int[]{R.id.rb01, R.id.rb02, R.id.rb03};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<TextView> viewList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            TextView textView = new TextView(this);
            viewList.add(textView);
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(viewList));

        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);// 设置当前的页面

//        viewPager.getCurrentItem(); // 获取当前页面的下标
        // 设置页面的滚动监听
        viewPager.setOnPageChangeListener(pageChangeListener);
//        viewPager.addOnPageChangeListener();

        PagerTabStrip strip = (PagerTabStrip) findViewById(R.id.strip);
        strip.setBackgroundColor(Color.CYAN);
        strip.setTabIndicatorColor(Color.RED);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setEnabled(false);
        radioGroup.check(rdIds[(Integer.MAX_VALUE / 2) % rdIds.length]);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override // 页面滚动时 1.页面的下标  2.偏移的百分比 3. 偏移的像素值
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("zhou", "onPageScrolled: positionOffset:"+positionOffset+",positionOffsetPixels:"+positionOffsetPixels);
        }

        @Override // 页面滚动结束选择的页面 1.页面的下标
        public void onPageSelected(int position) {
            Log.d("zhou", "onPageSelected: 页面下标："+position);
            radioGroup.check(rdIds[position % rdIds.length]);
        }

        @Override // 页面滚动时状态改变
        // ViewPager.SCROLL_STATE_SETTLING 自动滚动
        // ViewPager.SCROLL_STATE_DRAGGING 拖动滚动
        // ViewPager.SCROLL_STATE_IDLE 滚动停止
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class MyPagerAdapter extends PagerAdapter{

        private List<TextView> viewList;
        public MyPagerAdapter(List<TextView> viewList){
            this.viewList = viewList;
        }

        @Override // 页面的个数
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override // 判断传入的视图对象是否是object 此方法一般用不到 所以直接返回view == object
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override // 返回的就是页面的标题
        public CharSequence getPageTitle(int position) {
            return "标题"+position;
        }

        @Override // 初始化页面显示的内容 必须重写而且还不能调用父类里面的方法
        public Object instantiateItem(ViewGroup container, int position) {
            Log.d("zhou", "instantiateItem: position-->"+position);
            TextView textView = viewList.get(position % viewList.size());
            textView.setText("页面："+position);
            if (textView.getParent() != null){ // 说明此视图已经被添加了一次
                // 从父控件移除此视图
                ((ViewGroup)textView.getParent()).removeView(textView);
            }
            // 同一个视图只能够被添加一次，而不管是不是同一个ViewGroup
            container.addView(textView); // 将创建的视图对象添加进ViewPager
            return textView; // 返回的内容就是页面显示的内容
        }

        @Override // 销毁item 1.是被加载到的父控件对象 既是ViewPager 2.要被销毁的item的下标  3.销毁的视图对象
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.d("zhou", "destroyItem: position："+position);
//            container.removeView((View) object); // 将视图从ViewPager移除掉
        }
    }
}
