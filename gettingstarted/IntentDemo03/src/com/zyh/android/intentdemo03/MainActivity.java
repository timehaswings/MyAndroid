package com.zyh.android.intentdemo03;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	// 第一个页面：取出输入框的内容 EditText.getText().toString();
	// 1.显式跳转
	// 2.隐式跳转 
	
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.inputEdit);
		findViewById(R.id.button).setOnClickListener(onClick);
	}
	
	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 1.先获取输入框的内容
			String str = editText.getText().toString();
			Log.d("zhou", "..onClick..str:"+str);
			// 2.构建Intent对象
			/**************显示跳转**************/
//			Intent intent = new Intent();
//			// 设置跳转的组件  1. Context 2.跳转的类的名字：包+类名
//			intent.setComponent(new ComponentName(MainActivity.this, "com.zyh.android.intentdemo03.TargetActivity"));
//			// 设置传递数据
//			intent.putExtra("text", str);
//			// 启动跳转
//			MainActivity.super.startActivity(intent);
			/**************隐式跳转**************/
			// 当系统里面没有任何一个页面可以接收传递的Intent(action data type category),会抛出异常
			Intent intent = new Intent();// action data(Uri) type category
//			intent.setAction("com.zyh.android.intentdemo03.ACTION");
			intent.setAction(Intent.ACTION_VIEW);
			// 设置传递数据
			intent.putExtra("text", str); // 传递的额外数据
			intent.setType("text/plain");
			try{
				MainActivity.super.startActivity(intent);
			}catch(Exception e){
				e.printStackTrace();
				Log.d("zhou", "..onClick..没有页面可以接收此Intent");
			}
			
		}
	};
}
