package com.zyh.android.intentdemo02;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TargetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target);
		
		Intent intent = getIntent();
		// 1.key 2.默认值 就是当key不存在返回的值
		int num = intent.getIntExtra("num", -1);
		String str = intent.getStringExtra("str");
		Bundle bundle = intent.getBundleExtra("bundle");
		String name = bundle.getString("name");
		ArrayList<String> list = (ArrayList<String>) intent.getSerializableExtra("list");
		Log.d("zhou", "..onCreate..num:"+num+"\nstr:"+str+"\nname:"+name+"\nlist:"+list);
		
	}
}
