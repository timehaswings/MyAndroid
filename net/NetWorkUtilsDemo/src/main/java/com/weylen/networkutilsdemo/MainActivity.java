package com.weylen.networkutilsdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // 将网络请求的代码进行封装成一个工具类

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.result);
    }

    private ProgressDialog progressDialog;
    public void testUtilGetClick(View view){
        progressDialog = ProgressDialog.show(this, "", "加载中...");
        String url = "http://192.168.1.101:8080/webapps/login.action?account=admin&password=admin";
        NetUtil.getRequest(url, new NetUtil.ResponseString() {
            @Override
            public void onResponse(String data, int status) {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                // 表示请求失败
                if (status == -1){
                    data = "数据获取失败";
                }
                textView.setText(data);
            }
        });
    }

    public void testUtilPostClick(View view){
        progressDialog = ProgressDialog.show(this, "", "加载中...");
        String url = "http://192.168.1.101:8080/webapps/register.action";
        HashMap<String, String> params = new HashMap<>();
        params.put("name", "sanzhang");
        params.put("password", "123456");
        params.put("address", "四川成都");
        params.put("phone", "136456465");
        NetUtil.postRequest(url, params, new NetUtil.ResponseString() {
            @Override
            public void onResponse(String data, int status) {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                // 表示请求失败
                if (status == -1){
                    data = "数据获取失败";
                }
                textView.setText(data);
            }
        });
    }
}
