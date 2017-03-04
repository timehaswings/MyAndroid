package com.zhy.android.servicedemo01;

import java.io.Serializable;

import com.zhy.android.servicedemo01.MyService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements Serializable{
	// new Activity()? 表示创建类创建一个对象而已，但是不表示一个页面
	// new MyService() 

	// 5.0以上不能使用隐式启动服务方式
	// startService 启动服务之后，那么服务和页面就没有任何的关联 stopService
	// bindService 服务和页面绑定之后，页面的生命周期就会影响服务的生命周期, 页面退出  服务也就被销毁。
	
	// 页面与服务的交互(数据传递)：
	// 1.startService 通过intent对象进行数据封装传递
	// 2.bindService 
	
	// 服务怎么传递数据给页面？
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	private int count;
	public void startServiceClick(View view){
		count++;
		Intent intent = new Intent("testAction.test");
		intent.putExtra("Play", true);
		intent.putExtra("Index", 1);
		intent.putExtra("KillSelf", count >= 5);
		startService(intent);
//		stopService(intent); // 关闭服务
	}
	
	/**
	 * 绑定服务按钮监听器
	 * @param view
	 */
	public void bindServiceClick(View view){
		Intent intent = new Intent(this, MyService.class);
		intent.putExtra("Play", true);
		intent.putExtra("Index", 1);
		// 1.绑定的服务对象
		// 2.绑定的回调对象
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}
	
	private ServiceConnection connection = new ServiceConnection() {
		@Override // 服务未连接
		public void onServiceDisconnected(ComponentName name) {
			Log.d("zhou", "..onServiceDisconnected..:");
		}
		
		@Override // 服务链接 1.绑定的服务的组件名 2.就是Service.onBind方法返回的对象
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("zhou", "..onServiceConnected..service:"+service);
			MyBinder myBinder = (MyBinder) service;
			MyService myService = myBinder.getService();
		}
	};
	
	protected void onDestroy() {
		super.onDestroy();
//		// 解除绑定
//		unbindService(connection);
	};
}
