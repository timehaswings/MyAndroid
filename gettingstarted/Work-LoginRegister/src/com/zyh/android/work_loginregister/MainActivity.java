package com.zyh.android.work_loginregister;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_success);
		
		TextView textView = (TextView) findViewById(R.id.message);
		RegisterInfo registerInfo = getIntent().getParcelableExtra("Data");
		textView.setText(registerInfo == null ? "´«µÝÊý¾ÝÊ§°Ü" : "»¶Ó­"+registerInfo.getAccount());
	}
}
