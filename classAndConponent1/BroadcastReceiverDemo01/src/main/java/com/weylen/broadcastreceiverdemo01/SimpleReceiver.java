package com.weylen.broadcastreceiverdemo01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zhou on 2016/4/22.
 */
public class SimpleReceiver extends BroadcastReceiver{

    @Override // 当有消息到达的时候响应此方法 1.上下文 2.发送广播的Intent对象
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction(); // 得到发出广播的Action值
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)){
            Log.d("zhou", "onReceive: 开机完成");
//            context.startService()
        }else if (intent.getAction().equals("com.weylen.action.Normal")){
            String time = intent.getStringExtra("NowTime");
            String words = intent.getStringExtra("Words");
            Log.d("zhou", "SimpleReceiver onReceive: time:"+time+",words:"+words);
            abortBroadcast(); // 拦截广播
//            clearAbortBroadcast();// 清除拦截
        }
    }
}
