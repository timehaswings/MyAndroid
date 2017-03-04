package com.weylen.remoteservicedemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // 在aidl里面如何使用自定义类型
    // 1.使用的类型必须是先Parcelable接口
    // 2.创建类型对应的Parcelable文件
    // 3.在需要使用的文件里面引入此类型即可。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
