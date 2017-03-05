package com.weylen.listimgdemo;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by zhou on 2016/4/29.
 */
public class DisplayImageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        String imgPath = getIntent().getStringExtra("ImgPath");

        ImageView imageView = new ImageView(this);
        setContentView(imageView);

        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        imageView.setImageBitmap(bitmap);
    }
}
