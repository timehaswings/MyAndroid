package com.zyh.android.example01;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// View 表示视图
	// ViewGroup 视图组 任何可以添加视图的对象都是属于ViewGroup的子类，LinearLayout, RelativeLayout
	// ViewGroup.LayoutParams 布局参数(宽，高，重心等属性)
	// ViewGroup.addView 添加视图
	

	@Override // 表示页面在创建的时候调用
	// onCreate是属于Activity生命周期的其中一个方法
	// 对于Activity里面的生命周期方法都必须调用父类的方法 否则会抛异常
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置内容视图 整个页面显示的东西
		setContentView(R.layout.activity_main);// 需求的参数是一个布局资源id
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		System.out.println("密度值："+displayMetrics.density);
		
//		Context 上下文 (1)application + (n)activity + (n)service
		Application applicatin = getApplication();
		Context context = getApplicationContext();
		System.out.println("application:"+applicatin);
		System.out.println("context:"+context);
		TextView textView = new TextView(this);// 创建TextView对象
		textView.setText("Hello 你好吗");
		textView.setText(R.string.hello_world);// 字符串资源id
		textView.setText(String.valueOf(3));
		textView.setBackgroundResource(android.R.color.holo_blue_light);
		textView.setWidth(200);
		setContentView(textView);
		
		// 1.width  2.height

		
		// 200指的像素值
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)(200 * displayMetrics.density), -1);
		setContentView(textView, params);
	}
}
