package com.zyh.android.intentdemo02;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	// Extra 额外的数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	public void startClick(View view){
		Intent intent = new Intent(this, TargetActivity.class);
		intent.putExtra("num", 10);
		intent.putExtra("str", "Hello nihaoma");
		// 1. Bundle 和HashMap类似 保存数据--
		//    键值对 Map的键值对都是泛型 Bundle的key都是String类型
		//    HashMap实现的是Serializable Bundle实现的是Parcelable   
		
		// Serializable 序列化接口
		// 序列化： 将数据或者对象保存在文件里面(ObjectOutputStream)
		// 反序列化： 从文件里面将数据读出来(ObjectInputStream)
		// Parcelable 接口 是将数据或者对象保存在内存里面
		Bundle bundle = new Bundle();
		bundle.putString("name", "张三");
		intent.putExtra("bundle", bundle);
		
		// Serializable ArrayList LinkedList HashSet HashMap
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		intent.putExtra("list", list);
		// Parcelable Bundle
		// 自定义数据类型
		startActivity(intent);
	}
}
