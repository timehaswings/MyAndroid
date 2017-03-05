package com.zyh.android.activitylaunchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AActivity extends Activity {

	// Activity加载模式，在AndroidManifest.xml里面进行Activity的配置。
	// standard标准的 A-->B-->C-->A 标准模式下 所有的跳转都会产生一个新的实例
	// singleTop 在页面的堆栈的栈顶只有一个实例 A-->B-->A-->A
	// 如果栈顶的两个页面是同一个的话 则只会产生一个新的实例
	
	// singleTask 在同一个堆栈里面只会产生一个新的实例 B-->A
	// singleInstance 在任意的堆栈里面都只会产生一个新的对象
	
	private boolean boo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("zhou", "..onCreate..this:"+this);
		boo = getIntent().getBooleanExtra("boo", false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("zhou", "..onResume..执行了onResume方法");
	}
	
	/**
	 * 跳转按钮的监听事件
	 * @param view
	 */
	public void jumpClick(View view){
		Intent intent = new Intent(this, BActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		startActivity(intent);
	}
}
