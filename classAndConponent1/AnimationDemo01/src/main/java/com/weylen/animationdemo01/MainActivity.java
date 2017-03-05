package com.weylen.animationdemo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // 动画：TweenAnimation FrameAnimation 属性动画
    // TweenAnimation-->Animation
    // TranslateAnimation 移动动画
    // ScaleAnimation 缩放动画
    // RotateAnimation 旋转动画
    // AlphaAnimation 透明度动画
    // AnimationSet 动画集合(几种动画同时播放)
    // 特点：所有动画在执行结束之后，载体(视图)都会还原

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    /**
     * TranslateAnimation的监听器
     * @param view
     */
    public void playTranslateAnimation(View view){
        /**
         * 1.2 x轴的位置从什么地方到什么地方
         * 2.3 y轴的位置从什么地方到什么地方
         */
        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
        // Animation.ABSOLUTE 绝对值
        // Animation.RELATIVE_TO_SELF 相对于自身载体的大小 1 2 0.5
        // Animation.RELATIVE_TO_PARENT 相对于父控件 1 2 0.5
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.7f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_PARENT, 0f);
        // 设置动画完成的时间
        animation.setDuration(5000);
        // 设置动画的变速器
        animation.setInterpolator(new LinearInterpolator());
        // 设置动画的重复次数 Animation.INFINITE 无限重复
        animation.setRepeatCount(1);
        // 设置动画的重复模式 Animation.RESTART 重新开始 Animation.REVERSE 反转
        animation.setRepeatMode(Animation.REVERSE);
        // 动画监听器
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override // 动画开始
            public void onAnimationStart(Animation animation) {

            }

            @Override // 动画结束
            public void onAnimationEnd(Animation animation) {

            }

            @Override // 动画重复
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // 启动动画
        ((ViewGroup)imageView.getParent()).startAnimation(animation);
//        animation.cancel();
    }

    /**
     * 旋转按钮
     * @param view
     */
    public void playRotateAnimation(View view){
        // 1. 2. 旋转的角度
        // 3.4.5.6 表示旋转中心
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.ABSOLUTE, -imageView.getLeft(),
                Animation.ABSOLUTE, -imageView.getTop());
        rotateAnimation.setDuration(3000);
        imageView.startAnimation(rotateAnimation);
    }

    /**
     * 缩放动画
     * @param view
     */
    public void playScaleAnimation(View view){
        // 1.2 x的缩放范围 3.4 y的缩放范围
        // 5.6.7.8 表示缩放中心
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, // x的缩放中心
                Animation.RELATIVE_TO_SELF, 0.5f); // y的缩放中心
        scaleAnimation.setDuration(3000);
        imageView.startAnimation(scaleAnimation);
    }

    /**
     * 透明度动画
     * @param view
     */
    public void playAlphaAnimation(View view){
        // 透明度从多少到多少
        // 1.0表示不透明 0.0表示全透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        imageView.startAnimation(alphaAnimation);
    }

    /**
     * 动画集合
     * @param view
     */
    public void playAnimationSet(View view){
        // 1.shareInterpolator 是否共享变速器 true 由AnimationSet设置
        // false则由每一个动画单独设置
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(3000);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, // x的缩放中心
                Animation.RELATIVE_TO_SELF, 0.5f); // y的缩放中心
        animationSet.addAnimation(scaleAnimation);

        imageView.startAnimation(animationSet);
    }
}
