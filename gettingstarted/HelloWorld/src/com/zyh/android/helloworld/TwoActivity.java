package com.zyh.android.helloworld;

import com.zyh.android.helloworld2222.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class TwoActivity extends Activity {

	// 图片 文本 颜色 音频 视频
	// 1.5~2.x 都是手机系统
	// 3.x 平板系统
	// 4.0以上 手机和平板都可以用
	
	// assets和 res下面存放都是资源
	// res 下面的资源在打包的时候都会被编译成2进制格式，res的资源会自动和R文件产生关联，实际上就是R文件保存了关于res资源的id
	// assets下面的内容是不会被编译，也不会和R文件产生关联，获取只能通过流的方式。
	
	// res资源包
	// drawable-->图片 mipmap(studio)、
	// layout--布局资源 显示在手机上的页面效果
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Log.d("HelloWorld", "dpi:"+displayMetrics.densityDpi);
		Log.d("HelloWorld", "密度:"+displayMetrics.density);
	}
}
