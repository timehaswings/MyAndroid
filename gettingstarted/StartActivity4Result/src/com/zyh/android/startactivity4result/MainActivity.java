package com.zyh.android.startactivity4result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	// 页面堆栈 
	// 堆栈 先进后出  A1-->B-->A2-->C A1-->B(返回A1，并且B要从堆栈销毁)-->C A1(销毁)-->B(销毁)-->A1
	// startActivityForResult 跳转是为了获取一个结果
	// 队列 先进先出
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	/**
	 * 注册按钮的监听事件
	 * @param view
	 */
	public void registerClick(View view){
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra("jisuan", "1 + 1 = ?");
		// 1.跳转的Intent对象 包含了跳转的ComponentName, Extra
		// 2.RequestCode 请求编号 发出这个请求的唯一值
		startActivityForResult(intent, 100);
	}
	
	@Override // 当有结果返回的时候都会响应此方法
	// 1.requestCode 请求编号的唯一值。因为在Activity里面的所有的startActivityForResult的返回都是响应此方法
	//   所以用它来区别这个结果的返回是由哪一个请求发出的
	// 2.resultCode 结果编号。主要判断的是结果是否正确的返回。一般来说，如果返回正确则值为Activity.RESULT_OK
	//   否则有可能是Activity.RESULT_CANCEL
	// 3.data 结果返回传递的参数
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("zhou", "..onActivityResult..");
		if(resultCode == RESULT_OK){ // 结果正确
			switch (requestCode) {
			case 100: // 表示此结果是requestCode为100的这个请求发出的
				Toast.makeText(MainActivity.this, "计算结果：1 + 1 = " + data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	}
}
