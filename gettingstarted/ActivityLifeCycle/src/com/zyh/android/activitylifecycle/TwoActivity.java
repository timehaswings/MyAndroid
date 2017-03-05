package com.zyh.android.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TwoActivity extends Activity {

	// Activity的生命周期
	// 页面从创建到销毁要经过的步骤：
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("zhou", "TwoActivity..onCreate..");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("zhou", "TwoActivity..onStart..");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("zhou", "TwoActivity..onRestart..");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("zhou", "TwoActivity..onResume..");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("zhou", "TwoActivity..onPause..");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("zhou", "TwoActivity..onStop..");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("zhou", "TwoActivity..onDestroy..");
	}
	
}
