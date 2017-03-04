package com.weylen.alarmmanagerdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // AlarmManager 闹钟管理器(全局性的定时器)和Timer类似

    public static final String ACTION = "com.test.action";
    private boolean isRegisterReceiver;
    private ReceiverAralm receiverAralm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // 系统会自动开启一个线程来执行此任务
//                Log.d("zhou", "run: 是在异步线程执行");
//            }
//        }, 1000);
    }

    private PendingIntent getPendingIntent(){
        Intent intent = new Intent();
        intent.setAction(ACTION);
        return PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRegisterReceiver){
            receiverAralm = new ReceiverAralm();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION);
            registerReceiver(receiverAralm, filter);
            isRegisterReceiver = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRegisterReceiver && receiverAralm != null){
            unregisterReceiver(receiverAralm);
        }
    }

    private boolean isStartAlarm;
    /**
     * 开启按钮点击事件
     * @param view
     */
    public void startAlarmClick(View view){
        // AlarmManager对象获取方式
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (isStartAlarm){
            alarmManager.cancel(getPendingIntent());// 取消闹钟
        }else{
            // 1.首次执行的时间必须>= 5000
            // 2.两次之间的间隔时间>= 1分钟
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000
                    , 5000, getPendingIntent());
            Log.d("zhou", "startAlarmClick: 开启闹钟");
        }
        isStartAlarm = !isStartAlarm;
        // 设置一次性闹钟
        // 参数：1.时间类型 AlarmManager.ELAPSED_REALTIME 相对于开机时间 ELAPSED_REALTIME_WAKEUP
        // AlarmManager.RTC 系统时间  AlarmManager.RTC_WAKEUP
        // 2.闹钟执行的时间
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*10, getPendingIntent());
        // 设置一次性闹钟，只不过此会在低电量的情况下也会执行
//        alarmManager.setAndAllowWhileIdle();
        // 设置不精确闹钟 给定的时间只是表示闹钟会在接近这个时间执行
//        alarmManager.setExact();

    }

    private class ReceiverAralm extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("zhou", "onReceive: 接收到闹钟发送的广播");
        }
    }
}
