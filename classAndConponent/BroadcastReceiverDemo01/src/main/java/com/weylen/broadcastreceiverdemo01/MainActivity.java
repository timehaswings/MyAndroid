package com.weylen.broadcastreceiverdemo01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // BroadcastReceiver 接收广播
    // 什么是广播？ 1.频道 2.广播一直发送
    // 在android里面广播的发送：系统发送 针对于手机状态的改变，自定义发送(传递数据)
    // 广播的分类：普通广播 有序广播 粘性广播

    // 接收器的注册：
    // 静态注册 在AndroidManifest.xml里面进行注册
    // 动态注册 代码进行注册 可以灵活的控制接收器的生命周期

    // Activity--Service--BroadcastReceiver--ContentProvider

    private DataReceiver receiver;
    private IntentFilter filter;
    private boolean isRegistReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 动态注册接收器按钮
     * @param view
     */
    public void registerReceiverClick(View view){
        if (isRegistReceiver){
            Toast.makeText(MainActivity.this, "已经注册过广播", Toast.LENGTH_SHORT).show();
            return;
        }
        receiver = new DataReceiver();// 构建接收器对象
        filter = new IntentFilter(); // 构建Filter对象
        filter.setPriority(100);
        filter.addAction(Intent.ACTION_TIME_TICK); // 系统时间每过一分钟发出广播，此广播只能动态注册接收
        filter.addAction("com.weylen.action.Normal"); // 添加一个自定义发送的广播的频道
        // 1.接收器对象 2.IntentFilter对象 addAction添加接受广播的频道
        registerReceiver(receiver, filter);
        isRegistReceiver = true;
    }

    /**
     * 解除注册接收器按钮
     * @param view
     */
    public void unregisterReceiverClick(View view){
        if (isRegistReceiver && receiver != null){
            this.unregisterReceiver(receiver);
            isRegistReceiver = false;
        }
    }

    private class DataReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)){
                Log.d("zhou", "onReceive: 系统时间过了一分钟");
            }else if (intent.getAction().equals("com.weylen.action.Normal")){
                String time = intent.getStringExtra("NowTime");
                String words = intent.getStringExtra("Words");
                Log.d("zhou", "DataReceiver onReceive: time:"+time+",words:"+words);
                Toast.makeText(MainActivity.this, time+" : "+words, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * n(Activity)+n(Service)+1(Application)
     * Context的子类：Activity Service Application
     * 发送普通广播按钮
     * @param view
     */
    public void sendNormalBroadClick(View view){
        Intent intent = new Intent(); // 创建Intent
        intent.setAction("com.weylen.action.Normal");// 设置广播所在的频道
        intent.putExtra("NowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        intent.putExtra("Words", "Today is Friday");
        sendBroadcast(intent); // 发送一条普通广播
    }

    /**
     * 发送有序广播按钮:接收器按照接收器优先级进行，优先级越高则先接收
     * 优先级的范围:-1000 ~ 1000
     * 先接收到广播的接收器有权利拦截广播
     * @param view
     */
    public void sendOrderedBroadClick(View view){
        Intent intent = new Intent(); // 创建Intent
        intent.setAction("com.weylen.action.Normal");// 设置广播所在的频道
        intent.putExtra("NowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        intent.putExtra("Words", "Today is Friday");
        sendOrderedBroadcast(intent, null);
    }
}
