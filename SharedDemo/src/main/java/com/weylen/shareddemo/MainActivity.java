package com.weylen.shareddemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 数据储存：1.SharedPreference 2.Files 3.SQLite 4.ContentProvider 5.NetWork

    // SharedPreference文件存储的位置：data/data/packageName/shared_prefs

    private EditText nameEdit, pwdEdit;
    private CheckBox autoLoginBox;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        nameEdit = (EditText) findViewById(R.id.username);
        pwdEdit = (EditText) findViewById(R.id.password);
        autoLoginBox = (CheckBox) findViewById(R.id.autoLogin);
        findViewById(R.id.login).setOnClickListener(loginClick);

        // 先取得保存的登录状态
        boolean isAutoLogin = getAutoLoginState();
        // 设置CheckBox的状态
        autoLoginBox.setChecked(isAutoLogin);

        // 自动登录
        if (isAutoLogin){
            AccountBean accountBean = getAccountInfo();
            // 设置显示的内容
            nameEdit.setText(accountBean.getName());
            pwdEdit.setText(accountBean.getPassword());
        }
    }

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 取得输入的用户名和密码
            String nameStr = nameEdit.getText().toString();
            String pwdStr = pwdEdit.getText().toString();
            // 检查是否输入了内容
//            if ("".equals(nameStr) || "".equals(pwdStr)){
//
//            }
            // 判断输入的内容是否为空
            if (TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(pwdStr)){
                Toast.makeText(MainActivity.this, "请输入用户名或者密码", Toast.LENGTH_SHORT).show();
                return;
            }
            // 执行登录操作
            progressDialog = ProgressDialog.show(MainActivity.this, "", "登录中...");
            new LoginThread().start();
        }
    };

    private class LoginThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 是否需要自动登录
            boolean isAutoLogin = autoLoginBox.isChecked();
            // 保存自动登录状态
            setupAutoLogin(isAutoLogin);
            if (isAutoLogin){
                // 保存账户信息
                setupAccountInfo();
            }

            // 取消ProgressDialog
            if (progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            // 登录成功 跳转到登录成功的页面
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            MainActivity.this.finish();
        }
    }

    // 保存登录状态
    public void setupAutoLogin(boolean isAutoLogin){
        // 1.文件名 2.访问模式
        SharedPreferences sharedPreferences = getSharedPreferences("login.state", Context.MODE_PRIVATE);
        // 取得编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 保存数据 1.key 2.value
        editor.putBoolean("IsAutoLogin", isAutoLogin);
        // 提交
        editor.commit();
    }

    // 保存登录状态
    public void setupAccountInfo(){
        // 1.文件名 2.访问模式
        SharedPreferences sharedPreferences = getSharedPreferences("login.account", Context.MODE_PRIVATE);
        // 取得编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // 保存数据 1.key 2.value
        editor.putString("AccountName", nameEdit.getText().toString());
        editor.putString("AccountPwd", pwdEdit.getText().toString());
        // 提交
        editor.commit();
    }

    // 获取保存的状态
    public boolean getAutoLoginState(){
        // 1.文件名 2.访问模式
        SharedPreferences sharedPreferences = getSharedPreferences("login.state", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsAutoLogin", false);
    }

    public AccountBean getAccountInfo(){
        // 1.文件名 2.访问模式
        SharedPreferences sharedPreferences = getSharedPreferences("login.account", Context.MODE_PRIVATE);
        AccountBean accountBean = new AccountBean();
        accountBean.setName(sharedPreferences.getString("AccountName", ""));
        accountBean.setPassword(sharedPreferences.getString("AccountPwd", ""));
        return  accountBean;
    }
}
