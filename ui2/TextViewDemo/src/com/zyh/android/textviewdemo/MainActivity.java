package com.zyh.android.textviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	// 控件部分：
	// 布局：
	// TextView 
	// 123456789
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView textView = (TextView) findViewById(R.id.textView);
		String source = "<font color='red'>123</font><font color='green'>456</font><font color='blue'>789</font>";
		// View.VISIBLE View.INVISIBLE View.GONE
		textView.setVisibility(View.VISIBLE);
		textView.setText(Html.fromHtml(source));
	}
}
