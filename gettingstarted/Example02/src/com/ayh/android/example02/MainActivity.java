package com.ayh.android.example02;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		// Context Application(单实例) Activity Service
		TextView textView = new TextView(getApplication());
		textView.setBackgroundResource(android.R.color.holo_blue_bright);
		textView.setText("Hello 你好");
		
		// 创建一个空的对象用来接收窗口的属性
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		// LayoutParams对于每一个ViewGroup都会拥有一个静态内部类
		// 选择谁应该根据被添加到的父控件来决定
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int)(200 * displayMetrics.density), -1);
//		textView.setLayoutParams(layoutParams);
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
	
		// textView被添加到默认大小是wrap_content
		linearLayout.addView(textView, layoutParams);
//		 一个控件只能够被添加一次，如果想要多次使用，则应该先从它的父控件里面将它移除之后才能再次添加
		linearLayout.removeAllViews();// 移除布局里面所有的控件
		linearLayout.removeView(textView);// 移除指定的视图
		linearLayout.addView(textView);
		
		TextView textView2 = new TextView(getApplication());
		textView2.setBackgroundResource(android.R.color.holo_orange_dark);
		textView2.setText("你好啊");
		// 1.是被添加的视图对象 2.添加的位置 0表示第一个
		linearLayout.addView(textView2, 0);
		
		linearLayout.setBackgroundResource(android.R.color.holo_red_light);
		setContentView(linearLayout);
	}
}
