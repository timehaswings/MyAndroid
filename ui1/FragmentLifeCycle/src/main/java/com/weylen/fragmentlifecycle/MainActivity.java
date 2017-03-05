package com.weylen.fragmentlifecycle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Fragment如果没有被添加进返回堆栈，那么在被替换或者移除之后，对象即被销毁。
    // Fragment被添加进返回堆栈，在被替换或者移除之后，只会销毁Fragment创建的视图，而对象会被保留起来。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 按钮的点击事件
    public void addFragmentClick(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FragmentLifeCycle(), FragmentLifeCycle.TAG)
                .addToBackStack(null) // 添加进返回堆栈
                .commit();
    }

    public void updateFragmentClick(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentLifeCycle.TAG);
        if (fragment != null){
            FragmentLifeCycle fragmentLifeCycle = (FragmentLifeCycle) fragment;
            fragmentLifeCycle.updateText("修改的内容");
        }
    }
}
