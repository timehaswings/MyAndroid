package com.weylen.asynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    // 列表里面如何显示图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void asyncLoadImg(View view){
        String requestUrl = "http://192.168.1.103:8080/webapps/download/test123.jpg";
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new ImageAsyncTask(imageView, progressBar).execute(requestUrl);
    }
}
