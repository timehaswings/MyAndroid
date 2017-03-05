package com.zyh.android.intentdemo04;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TargetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target);
		
		Person person = getIntent().getParcelableExtra("Person");
		Log.d("zhou", "..onCreate..person:"+person);
		Serializable serializable = getIntent().getSerializableExtra("Person2");
		Person2 person2 = (Person2) serializable;
		Log.d("zhou", "..onCreate..person2:"+person2);
	}
}
