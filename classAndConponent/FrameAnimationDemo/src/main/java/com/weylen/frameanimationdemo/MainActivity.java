package com.weylen.frameanimationdemo;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Tween Frame（图片连续播放） 属性动画
    // FrameAnimation不是属于Animation的子类

    private AnimationDrawable animationDrawable1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AnimationDrawable animationDrawable = new AnimationDrawable();
        // 添加一帧 1、Drawable 2、这一帧显示的时间
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_0), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_1), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_2), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_3), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_4), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_5), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_6), 200);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.sample_7), 200);

        animationDrawable.setOneShot(true);// 设置是否指执行一次动画
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        Drawable drawable = imageView.getDrawable(); // 获取图片控件设置的src属性的值，图片对象
        Drawable drawable = imageView.getBackground(); // 获取控件设置的background属性值
        if (drawable instanceof AnimationDrawable){
            animationDrawable1 = (AnimationDrawable) drawable;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable1.isRunning()){
                    animationDrawable1.stop();
                }else{
                    animationDrawable1.start();
                }
            }
        });
    }
}
