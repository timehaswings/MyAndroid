package com.zyh.android.intentdemo01;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImgActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img);
		
		Intent intent = getIntent();// 获取跳转到此页面的Intent对象
		Uri uri = intent.getData(); // 获取传递的数据
		if(uri != null){
			ImageView imgView = (ImageView) findViewById(R.id.image);
			imgView.setImageURI(uri);// 设置图片的来源为一个uri值
		}
	}
}
