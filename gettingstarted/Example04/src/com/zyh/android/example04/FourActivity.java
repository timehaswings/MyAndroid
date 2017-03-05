package com.zyh.android.example04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FourActivity extends Activity {
	
	// 如何增加新的页面？
	// android 四大组件：1 Activity 2. BroadcastReceiver 3.Service 4.ContentProvider
	// 四大组件之间的联系通常都用Intent
	// Intent 意图 
	// 1.CompoundName 组件名 必须要设置 CompoundName 组件名的意思就是告知系统现在要去到的哪个类
	//		intent.setClass(packageContext, cls)
	//		intent.setClassName(packageContext, className)
	//		intent.setComponent(component);
	// 2.Action和 Data/Type CompoundName
	
	// 每当程序里面产生一个新的页面时都要在AndroidManifest.xml里面进行页面的注册

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Intent intent = new Intent();
		// 1.context 2.跳转的页面的class对象
//		intent.setClass(packageContext, cls)
//		intent.setClassName(packageContext, className)
//		intent.setComponent(component);
//		intent.setAction(action);
	}
}
