package com.weylen.handlerdemo02;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Thread.start() Thread.run()
    // 完成异步线程与主线程的交互问题

    private Handler handler;
    private TextView textView;
    private ProgressDialog pDialog;

    // handler.sendMessage 发送消息
    // handler.handleMessage 接收消息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        handler = new Handler(){
            @Override // 接收消息，接收由sendMessage发送的消息 1.msg即是发送的消息对象
            public void handleMessage(Message msg) {
                if (pDialog != null && pDialog.isShowing()){
                    pDialog.dismiss();
                }

                int arg1 = msg.arg1;
                int arg2 = msg.arg2;
                int what = msg.what;
                Object obj = msg.obj;
                Bundle bundle = msg.getData();
                if (bundle != null){
                    int num = bundle.getInt("num");
                    textView.setText(String.valueOf(arg1+arg2+what)+"\n"+obj+"\nnum:"+num);
                }

            }
        };
    }

    public void getDataClick(View view){
        pDialog = ProgressDialog.show(this, "", " 正在获取数据...");
        new GetDataThread().start();
    }

    private class GetDataThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String data = "这是获取到的数据";

            // 创建消息对象
            Message message = Message.obtain();
            // 封装数据
            message.arg1 = 10;
            message.arg2 = 20;
            message.what = 30;

            message.obj = data;

            Bundle bundle = new Bundle();
            bundle.putInt("num", 10);
            message.setData(bundle);
            // 发送消息
            handler.sendMessage(message);
            // 能不能直接调用handleMessage? handleMessage只是调用此方法而已，执行操作在异步线程
            // 而sendMessage执行操作实在创建Handler对象的线程里面。
//            handler.handleMessage(message);
        }
    }
}
