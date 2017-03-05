package com.weylen.animationdemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

public class MainActivity extends AppCompatActivity {

    // 1.在res文件夹下面定义anim文件夹
    // 2.在anim文件夹下面创建动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 加载xml动画按钮监听
     * @param view
     */
    public void loadXmlAnimation(View view){
        // 先加载动画
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.set_anim);
        anim.setRepeatCount(1);
        anim.setInterpolator(new LinearInterpolator());
//        anim.setDuration(1000);
        view.startAnimation(anim);
    }
}
