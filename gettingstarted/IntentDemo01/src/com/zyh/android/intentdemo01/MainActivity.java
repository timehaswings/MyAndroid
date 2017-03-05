package com.zyh.android.intentdemo01;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
	// Intent
	// 1. 显示跳转 明确的知道要跳转的页面 创建Intent 设定ComponentName Context.startActivity
	// 2. 隐式跳转 Action Data Type(Category 种类)
	
	// Intent.ACTION_VIEW 需要系统找一个页面来显示指定是数据 data, type
	// type 指的是MIME类型 image/png *表示任意的类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// 1.View 表示当前触发这个事件的视图对象
	public void showImage(View view){
//		IntentFilter
		// 创建Intent对象 1.参数即是action值
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(null); // 设置数据
		intent.setType(""); // 设置数据类型
		// Uri表示同一资源标识符 http://(网路路径) file://(本机的路径) content://(ContentProvider)
		// http://www.baidu.com/index.html
		// Environment.getExternalStorageDirectory() 获取储存卡的根目录
		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/sample_7.jpg";
		Log.d("zhou", "..showImage..sdcard路径："+Environment.getExternalStorageDirectory().getAbsolutePath());
		// 判断文件是否存在
		File file = new File(filePath);
		if(file.exists()){
			// 当同时要设置数据和类型时只能够使用下面的方法
			intent.setDataAndType(Uri.parse("file://" + filePath), "image/*"); // 设置数据和类型
			MainActivity.this.startActivity(intent);
		}else{
			Log.d("zhou", "..showImage..文件不存在");
		}

	}
	
	public void buttonClick(View view){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/sample_7.jpg";
		intent.setDataAndType(Uri.parse("file://" + filePath), "image/*"); // 设置数据和类型
		startActivity(intent);
	}
}
