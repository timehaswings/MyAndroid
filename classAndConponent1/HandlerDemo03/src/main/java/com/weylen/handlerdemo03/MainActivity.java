package com.weylen.handlerdemo03;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsQuery2.OnQueryCompleteListener{

    // 1.执行任务
    // 2.完成异步线程与主线程的交互

    // 联系人的查询操作

    // 类与类之间的数据传递：ContactsQuery类要将查询的数据传递给MainActivity进行显示
    // 1.接口回调 在获取数据类里面定义接口 在需要使用数据类里面实现接口。
    //    在传递数据类里面取得实现接口类实例
    // 2.Handler sendMessage发送消息 handleMessage接收消息

    private TextView textView;

    private Handler handler = new Handler(){
        @Override // 接收消息
        public void handleMessage(Message msg) {
            if (progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            List<String> contactsNameList = (List<String>) msg.obj;
            textView.setText(contactsNameList.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
    }

    private ProgressDialog progressDialog;
    public void queryContactsClick(View view){
        progressDialog = ProgressDialog.show(this, "", "查询中...");
        new ContactsQuery2().query(this, this);

        new ContactsQuery2().query(this, new ContactsQuery2.OnQueryCompleteListener() {
            @Override
            public void onQueryComplete(List<String> data) {

            }
        });
    }

    @Override
    public void onQueryComplete(List<String> data) {
        Log.d("zhou", "onQueryComplete: "+Thread.currentThread());
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        textView.setText(data.toString());
    }
}
