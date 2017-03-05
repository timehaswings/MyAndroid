package com.zyh.android.example04;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	// 如何增加新的页面？
	// android 四大组件：1 Activity 2. BroadcastReceiver 3.Service 4.ContentProvider
	// 每当程序里面产生一个新的页面时都要在AndroidManifest.xml里面进行页面的注册
	// 四大组件之间的联系通常都用Intent
	// Intent 意图  显示跳转 就是明确的知道要跳转的页面
	// 1.ComponentName 组件名 必须要设置 ComponentName 组件名的意思就是告知系统现在要去到的哪个类
	//		intent.setClass(packageContext, cls)
	//		intent.setClassName(packageContext, className)
	//		intent.setComponent(component);
	// 2.Action和 Data/Type 隐式跳转 让系统选择跳转的页面
	//   Action 就表示告诉系统现在要做的一个操作
	//   Intent.ACTION_VIEW 就表示告诉系统现在需求一个打开指定内容的面
	//   指定的内容就通过data和type来决定
	//   Intent.ACTION_CALL 打电话 需要拨打电话权限
	//   Intent.ACTION_DIAL 跳转到拨打电话界面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// 设定跳转的内容 setClass无非就是设定一个ComponentName而已
//				intent.setClass(MainActivity.this,  ThreeActivity.class);
//				intent.setClassName(MainActivity.this, "com.zyh.android.example04.ThreeActivity");
//				intent.setClassName(getPackageName(), "com.zyh.android.example04.ThreeActivity");
				ComponentName cName = new ComponentName(getPackageName(), "com.zyh.android.example04.ThreeActivity");
				intent.setComponent(cName);
				MainActivity.this.startActivity(intent);
			
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				// 设定数据 Uri.parse()解析一个字符串返回Uri对象
				intent.setData(Uri.parse("tel:10086"));
				MainActivity.this.startActivity(intent);
			}
		});
	}
}
