package com.weylen.viewpagerworkdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 广告的页面 内容页面
     */
    private ViewPager adPager, contentPager;

    /**
     * 广告单选按钮组和内容单选按钮组
     */
    private RadioGroup adGroup, contentGroup;

    private HorizontalScrollView scrollView;

    private DisplayMetrics displayMetrics;

    private float xy = 295.f / 720.f;

    // 用来判断广告图片是否需要进行自动滚动
    private boolean isDestroy = false;
    // 判断线程是否存活
    private boolean isThreadAlive;

    // 定义上一次选择的内容的Pager下标
    private int lastChoiceId = -1;

    /**
     * 广告页面的个数
     */
    public static final int AD_PAGER_SIZE = 4;

    private int[] rbIds = new int[]{R.id.rb01, R.id.rb02, R.id.rb03, R.id.rb04,
            R.id.rb05, R.id.rb06};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews(){
        adPager = (ViewPager) findViewById(R.id.adPager);
        adGroup = (RadioGroup) findViewById(R.id.adGroup);
        contentPager = (ViewPager) findViewById(R.id.contentPager);
        contentGroup = (RadioGroup) findViewById(R.id.contentGroup);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);

        setupAdPagerHeight();
        setupAdPagerAdapter();
        setupAdRadioButton();

        setupContentPagerAdapter();
        setupRadioButtonClick();

        adGroup.check(0);// 默认选中第一个
        adPager.setOnPageChangeListener(adPageChangeListener);
        contentPager.setOnPageChangeListener(contentPagerListener);
    }

    /**
     * 设置广告图片的视图高度
     */
    private void setupAdPagerHeight(){
        int height = (int) (displayMetrics.widthPixels * xy);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, height);
        adPager.setLayoutParams(layoutParams);
    }

    /**
     * 设置广告图片的适配器
     */
    public void setupAdPagerAdapter(){
        List<View> data = new ArrayList<>();
        int[] imgResId = new int[]{R.drawable.pic01, R.drawable.pic02, R.drawable.pic03, R.drawable.pic04};
        for (int i = 0; i < AD_PAGER_SIZE; i++){
            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(imgResId[i]);
            imageView.setImageResource(R.mipmap.banner);
            data.add(imageView);
        }
        GenericPagerAdapter pagerAdapter = new GenericPagerAdapter(data);
        adPager.setAdapter(pagerAdapter);
    }

    /**
     * 设置广告图片下方的RadioButton
     */
    public void setupAdRadioButton(){
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < AD_PAGER_SIZE; i++){
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.radiobutton, adGroup, false);
            radioButton.setId(i); // 设置RadioButton的id值
            adGroup.addView(radioButton);
        }
    }

    /**
     * 设置内容部分的适配器
     */
    public void setupContentPagerAdapter(){
        List<View> data = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            TextView textView = new TextView(this);
            textView.setText("这是第"+(i+1)+"个页面");
            textView.setTextSize(16);
            data.add(textView); // 添加视图
        }
        GenericPagerAdapter pagerAdapter = new GenericPagerAdapter(data);
        contentPager.setAdapter(pagerAdapter);
    }

    /**
     * 设置Content的单选按钮的点击事件
     */
    public void setupRadioButtonClick(){
        int size = rbIds.length;
        for (int i = 0; i < size; i++){
            findViewById(rbIds[i]).setOnClickListener(contentRbClickListener);
        }
    }

    // 内容单选按钮的点击事件
    private View.OnClickListener contentRbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId(); // 拿到当前点击的RadioButton的id值
            if (lastChoiceId == id){ // 如果点击的是同一个页面
                return;
            }

            for (int i = 0 ; i < rbIds.length; i++){
                if (id == rbIds[i]){
                    contentPager.setCurrentItem(i);
                    break; // 跳出循环
                }
            }
            lastChoiceId = id;
        }
    };

    /**
     * 滚动内容单选组
     */
    public void scrollContentGroup(int position){
        View view = findViewById(rbIds[position]);
        // 得到选中的RadioButton的右边的坐标值
        int right = view.getRight();
        // 如果坐标值超过了屏幕的宽度，则滚动对应的偏移量
        if (right > displayMetrics.widthPixels){
            scrollView.scrollBy(right - displayMetrics.widthPixels, 0);
        }

        // 如果是第一个或者是第二个下标 则直接滚动到0，0
        if(position == 0 || position == 1){
            scrollView.scrollTo(0, 0);
        }
    }

    private ViewPager.OnPageChangeListener adPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            adGroup.check(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ViewPager.OnPageChangeListener contentPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            contentGroup.check(rbIds[position]);
            scrollContentGroup(position);
            lastChoiceId = rbIds[position]; // 记录选中的页面对应的RadioButton的id值
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isDestroy = false;
        if (!isThreadAlive){
            new UpdateAdPager().start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDestroy = true;
    }

    class UpdateAdPager extends Thread{
        @Override
        public void run() {
            isThreadAlive = true;
            while(!isDestroy) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 页面+1 如果超过限制 则选中第一页
                        int currentId = adPager.getCurrentItem();
                        currentId++;
                        if (currentId == AD_PAGER_SIZE){
                            currentId = 0;
                        }
                        adPager.setCurrentItem(currentId, true);
                    }
                });
            }
            isThreadAlive = false;
        }
    }
}
