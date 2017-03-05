package com.zyh.android.example03;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.textView);
//		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
	}
	
	
	/**
	 * 通过xml的方法注册按钮的监听
	 * @param view
	 */
	public void buttonClick(View view){
		Log.d("zhou", "..buttonClick..点击按钮");
		// 修改文本的内容和颜色
		textView.setText("修改的文本");// 设定文本的内容
		// int color指的并不是color的id值，而是颜色值
		Resources res = getResources();// 得到res资源包对象
		int blueColor = res.getColor(R.color.blue);// 获取对应颜色的int类型值
		textView.setTextColor(blueColor);
	}
}
