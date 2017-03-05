package com.zhy.android.servicedemo01;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{
	
	public class MyBinder extends Binder{
		/**
		 * 自定义方法 返回服务类对象
		 * @return
		 */
		public MyService getService(){
			return MyService.this; // 能不能返回new MyService()
		}
	}

	@Override // 返回null 则说明绑定不成功, 但是不会调用Connection onServiceDisconnect方法
	public IBinder onBind(Intent intent) {
		Log.d("zhou", "..onBind..:");
		boolean play = intent.getBooleanExtra("Play", false);
		int index = intent.getIntExtra("Index", -1);
		Log.d("zhou", "..onBind..play:"+play+",index:"+index);
		return new MyBinder();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("zhou", "..onCreate..:");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("zhou", "..onStartCommand..:");
		boolean play = intent.getBooleanExtra("Play", false);
		int index = intent.getIntExtra("Index", -1);
		boolean isKillSelf = intent.getBooleanExtra("KillSelf", false);
		if (isKillSelf) {
			stopSelf();
		}
		Log.d("zhou", "..onStartCommand..play:"+play+",index:"+index);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("zhou", "..onDestroy..:");
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.d("zhou", "..onRebind..:");
	}
	
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("zhou", "..onUnbind..:");
		return super.onUnbind(intent);
	}
}
