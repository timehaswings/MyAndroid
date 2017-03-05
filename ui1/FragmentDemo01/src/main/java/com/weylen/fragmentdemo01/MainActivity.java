package com.weylen.fragmentdemo01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // Fragment的创建过程
    // 系统包里面的Fragment需求的最低版本3.0 android.app.Fragment
    // 无版本要求的是支持包里面的 android.support.v4.app.Fragment

    // 1.创建类继承Fragment
    // 2.重写里面的onCreateView方法

    // 加载方法：
    // 1.xml里面通过fragment标签进行加载
    // 2.代码里面通过FragmentTransaction进行操作：add replace remove 事务 commit
    // 一个事务对象只能提交一次。提交完成之后，此事务对象就不再作用。

    // FragmentManager 碎片管理器
    // 如果获取的是android.app下面的对象 则在Activity里面使用getFragmentManager
    // 如果获取的是支持包(android.support.v4.app..)的对象则使用getSupportFragmentManager

    // note: 1.在eclipse本身类继承的是Activity，在Activity里面是没有getSupportFragmentManager方法。
    // 如果要获取对象 则需要让Activity继承FragmentActivity
    // 2.Fragment的包必须和FragmentManager,FragmentTransaction所在的包一致

    @Override // 1.Bundle 和 HashMap类似 map实现的是Seri.. Bundle实现的是Parcelable
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取FragmentManager
        FragmentManager fragmentManager = getFragmentManager();
        // 找Fragment对象 如果id或者Tag不存在 则返回的是null对象
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);
        Fragment fragment1 = fragmentManager.findFragmentByTag("simpleFragment");
        Log.d("zhou", "onCreate: fragment:"+fragment+",fragment1:"+fragment1);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 开启一个新的事务
        // 1.要被添加到的容器的id 2.被添加的Fragment对象 3.Fragment对应的TAG值
//        fragmentTransaction.add(R.id.container, new SimpleFragment(), SimpleFragment.TAG);
//        fragmentTransaction.add(R.id.container, new SimpleFragment(), SimpleFragment.TAG);
//        fragmentTransaction.add(R.id.container, new SimpleFragment(), SimpleFragment.TAG);
        // 所谓的replace就是先remove、在add
        fragmentTransaction.replace(R.id.container, new SimpleFragment(), SimpleFragment.TAG);
        fragmentTransaction.replace(R.id.container, new SimpleFragment(), SimpleFragment.TAG);
        SimpleFragment simpleFragment = new SimpleFragment();
        fragmentTransaction.replace(R.id.container, simpleFragment, SimpleFragment.TAG);
        fragmentTransaction.commit();

        Fragment fragment3 = fragmentManager.findFragmentByTag(SimpleFragment.TAG);
        Log.d("zhou", "onCreate: fragment3-1:"+fragment3);

//        fragmentManager.beginTransaction()
//                .remove(fragmentManager.findFragmentByTag(SimpleFragment.TAG))
//                .commit();
    }

    @Override // 窗口焦点改变
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Fragment fragment3 = getFragmentManager().findFragmentByTag(SimpleFragment.TAG);
        Log.d("zhou", "onCreate: fragment3-2:"+fragment3);
        getFragmentManager().beginTransaction()
                .remove(fragment3)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
