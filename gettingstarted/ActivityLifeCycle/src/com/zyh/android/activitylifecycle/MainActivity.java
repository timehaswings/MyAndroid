package com.zyh.android.activitylifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Activity的生命周期
	// 页面从创建到销毁要经过的步骤：
	
	// 当设备横竖屏切换时页面的生命周期会重新执行。
	
	private TextView messageView;
	
	@Override // Bundle savedInstanceState 保存的页面对象
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("zhou", "MainActivity..onCreate..");
		messageView = (TextView) findViewById(R.id.message);
		if(savedInstanceState != null){
			// 获取保存的颜色值， 如果key不存在则返回-1
			int color = savedInstanceState.getInt("Color", -1);
			if (color != -1){
				messageView.setTextColor(color);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("zhou", "MainActivity..onStart..");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("zhou", "MainActivity..onRestart..");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("zhou", "MainActivity..onResume..");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("zhou", "MainActivity..onPause..");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("zhou", "MainActivity..onStop..");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("zhou", "MainActivity..onDestroy..");
	}
	
	@Override // 保存页面状态 此方法介于onPause与onStop之间执行
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d("zhou", "MainActivity..onSaveInstanceState..:");
		outState.putInt("Color", Color.RED);// 保存一个颜色值
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("zhou", "..onConfigurationChanged..");
	}
	
	public void jumpClick(View view){
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:80000"));
//		Intent intent = new Intent(this, TwoActivity.class);
		startActivity(intent);
		messageView.setTextColor(Color.RED);
	}
}
