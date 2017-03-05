package com.zyh.android.intentdemo03;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TargetActivity extends Activity {

	private TextView textView ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target);
		
		textView = (TextView) findViewById(R.id.textView);
		// 获取跳转到此页面的Intent对象
		String text = getIntent().getStringExtra("text");
		textView.setText(text);
	}
}
