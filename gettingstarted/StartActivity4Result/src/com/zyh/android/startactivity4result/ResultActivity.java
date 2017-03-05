package com.zyh.android.startactivity4result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ResultActivity extends Activity{

	private EditText inputEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		inputEdit = (EditText) findViewById(R.id.resultEdit);
	}
	
	/**
	 * 确定按钮的监听
	 * @param view
	 */
	public void sureClick(View view){
		Log.d("zhou", "..sureClick..点击了确定按钮");
		String result = inputEdit.getText().toString();
		// 封装数据
		Intent data = new Intent();
		data.putExtra("result", result);
		// 将结果进行返回
		// 1. 结果返回的编号，一般如果返回有正确结果值写：Activity.RESULT_OK
		//    如果是其他情况则返回:Activity.RESULT_CANCELED
		// 2. 传递的数据
		// 当调用setResult的时候就会响应发出请求的Activity里面的onActivityResult方法
		// 通常都会销毁此页面
		setResult(Activity.RESULT_OK, data);
		this.finish(); // 销毁页面
	}
}
