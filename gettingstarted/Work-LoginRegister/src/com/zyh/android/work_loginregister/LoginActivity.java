package com.zyh.android.work_loginregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private EditText accountEdit, pwdEdit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		accountEdit = (EditText) findViewById(R.id.account);
		pwdEdit = (EditText) findViewById(R.id.password);
	}
	
	/**
	 * 登录按钮的点击事件
	 * @param view
	 */
	public void loginClick(View view){
		String account = accountEdit.getText().toString();
		String password = pwdEdit.getText().toString();
		Intent intent = getIntent();
		RegisterInfo registerInfo = intent.getParcelableExtra("Data");
		if(registerInfo == null){
			Toast.makeText(this, "请先注册", Toast.LENGTH_SHORT).show();
			return;
		}
		if(account.equals(registerInfo.getAccount()) && password.equals(registerInfo.getPassword())){
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("Data", registerInfo);
			startActivity(intent);
		}else{
			Toast.makeText(this, "账号或密码不正确", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 注册按钮
	 * @param view
	 */
	public void registerClick(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
}
