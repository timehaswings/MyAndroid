package com.zyh.android.intentdemo04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void buttonClick(View view){
		Person person = new Person("张三", "四川成都", 22);
		Intent intent = new Intent();
		// 自定义的Person在所有的数据类型里面都没有找到
		// 1. 实现Serializable接口
		// 2. 实现Parcelable
		//    实现里面2个抽象方法：describeContents writeToParcel
		//    创建Parcelable内部接口Creator的属性. 写法必须是 public static final Parcelable.Creator CREATOR = new ....
		//    
		Person2 person2 = new Person2("张三", "四川成都", 22);
		intent.putExtra("Person", person);
		intent.putExtra("Person2", person2);
		intent.setClass(this, TargetActivity.class);
		startActivity(intent);
	}
}
