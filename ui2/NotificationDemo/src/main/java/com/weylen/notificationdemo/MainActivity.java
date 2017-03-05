package com.weylen.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Notification 通知类
    // NotificationManager 管理器(发布，取消通知) 单实例类
    // PendingIntent 即将到来的意图
    // Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int count;
    /**
     * 显示普通通知的点击事件
     * @param view
     */
    public void showNormalNf(View view){
        count++;
        // 创建Builder对象
        Notification.Builder builder = new Notification.Builder(this);
        // 通过Builder设置Notification的属性
        builder.setTicker("小红给你发了一条消息");// 提示文本
        // 图标的resId
        builder.setSmallIcon(android.R.drawable.ic_dialog_info); // 设置图标
        // Bitmap的获取方式：
        // 1.通过Drawable进行转换
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        // 2.BitmapFactory
        // 参数：1.Resources对象 2.图片id
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        builder.setLargeIcon(bitmap); // 设置大图标
        builder.setContentTitle("王小红"); // 内容标题
        builder.setContentText("周末嗨吗？"); // 内容文本
        builder.setWhen(System.currentTimeMillis());// 通知发出的时间， 时间是毫秒数
        builder.setNumber(11); // 设置数量
        builder.setContentInfo("info"); // 设置信息 信息和数量显示在同一位置上，2个都设置就只会显示信息

//        builder.setSound(Uri.parse("just for test")); // 设置声音
//        // <uses-permission android:name="android.permission.VIBRATE"/>
//        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000}); // 设置震动 ，需要权限
//        // 1.led的颜色 2. 亮的时间 3. 不亮的时间
//        builder.setLights(Color.GREEN, 1000, 1000); // 设置闪烁
        // Notification.DEFAULT_ALL 表示3个都是默认的
        builder.setDefaults(Notification.DEFAULT_ALL);// 设置默认的提示

        // 设置点击通知做的事情

        Intent callIntent = null;
        if (count % 2 == 0){
            callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:10086")); // 设置数据
        }else{
            callIntent = new Intent(this, DetailsActivity.class);
            callIntent.setData(Uri.parse("tel:10086")); // 设置数据
        }
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        // PendingIntent在创建之后都会保存起来。
        // note: 描述的PendingIntent 和 requestCode以及 intent有关。
        //       只要requestCode不一样，则说明PendingIntent不是所描述的。
        //       只跟Intent里面的compoundName有关。跟数据这些是无关的。
        // PendingIntent.FLAG_NO_CREATE 当描述的PendingIntent对象如果不存在就直接返回null
        // PendingIntent.FLAG_ONE_SHOT 创建的PendingIntent只能够使用一次。
        // PendingIntent.FLAG_CANCEL_CURRENT 描述的对象如果存在就直接取消当前的，而构建一个新的对象
        // PendingIntent.FLAG_UPDATE_CURRENT 更新当前的，如果描述的对象存在就直接更新当前对象的数据，而不是创建新的。

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("zhou", "showNormalNf: pendingIntent:"+pendingIntent);
        builder.setContentIntent(pendingIntent);

        // 设置点击清除按钮的PendingIntent
        builder.setDeleteIntent(pendingIntent);

        builder.setPriority(Notification.PRIORITY_DEFAULT);
        // 如果不想要取消通知
        Notification notification = builder.build();
        // 不能清除
//        notification.flags = Notification.FLAG_NO_CLEAR;
        // 最高优先级的通知
        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;

        // 取得通知管理器对象
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 发布通知
        // 参数 1.通知的id 唯一值，用来取消通知 2.通知对象
        // 需求的最低版本是16
        manager.notify(100, notification);
    }

    /**
     * 通知的兼容做法
     * @param view
     */
    public void showCompatNf(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        count++;
        // 通过Builder设置Notification的属性
        builder.setTicker("小红给你发了一条消息");// 提示文本
        // 图标的resId
        builder.setSmallIcon(android.R.drawable.ic_dialog_info); // 设置图标
        // Bitmap的获取方式：
        // 1.通过Drawable进行转换
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        // 2.BitmapFactory
        // 参数：1.Resources对象 2.图片id
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        builder.setLargeIcon(bitmap); // 设置大图标
        builder.setContentTitle("王小红"); // 内容标题
        builder.setContentText("周末嗨吗？"); // 内容文本
        builder.setWhen(System.currentTimeMillis());// 通知发出的时间， 时间是毫秒数
        builder.setNumber(11); // 设置数量
        builder.setContentInfo("info"); // 设置信息 信息和数量显示在同一位置上，2个都设置就只会显示信息

//        builder.setSound(Uri.parse("just for test")); // 设置声音
//        // <uses-permission android:name="android.permission.VIBRATE"/>
//        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000, 1000}); // 设置震动 ，需要权限
//        // 1.led的颜色 2. 亮的时间 3. 不亮的时间
//        builder.setLights(Color.GREEN, 1000, 1000); // 设置闪烁
        // Notification.DEFAULT_ALL 表示3个都是默认的
        builder.setDefaults(Notification.DEFAULT_ALL);// 设置默认的提示

        // 设置点击通知做的事情

        Intent callIntent = null;
        if (count % 2 == 0){
            callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.putExtra("NfId", count);
            callIntent.setData(Uri.parse("tel:10086")); // 设置数据
        }else{
            callIntent = new Intent(this, DetailsActivity.class);
            callIntent.putExtra("NfId", count);
            callIntent.setData(Uri.parse("tel:10086")); // 设置数据
        }
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        // PendingIntent在创建之后都会保存起来。
        // note: 描述的PendingIntent 和 requestCode以及 intent有关。
        //       只要requestCode不一样，则说明PendingIntent不是所描述的。
        //       只跟Intent里面的compoundName有关。跟数据这些是无关的。
        // PendingIntent.FLAG_NO_CREATE 当描述的PendingIntent对象如果不存在就直接返回null
        // PendingIntent.FLAG_ONE_SHOT 创建的PendingIntent只能够使用一次。
        // PendingIntent.FLAG_CANCEL_CURRENT 描述的对象如果存在就直接取消当前的，而构建一个新的对象
        // PendingIntent.FLAG_UPDATE_CURRENT 更新当前的，如果描述的对象存在就直接更新当前对象的数据，而不是创建新的。

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("zhou", "showNormalNf: pendingIntent:"+pendingIntent);
        builder.setContentIntent(pendingIntent);

        // 设置点击清除按钮的PendingIntent
        builder.setDeleteIntent(pendingIntent);

        // 设置自动取消 当PendingIntent被触发的时候自动取消
//        builder.setAutoCancel(true);

        builder.setPriority(Notification.PRIORITY_DEFAULT);
        // 如果不想要取消通知
        Notification notification = builder.build();
        // 不能清除
//        notification.flags = Notification.FLAG_NO_CLEAR;
        // 最高优先级的通知
//        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;

        // 取得通知管理器对象
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 发布通知
        // 参数 1.通知的id 唯一值，用来取消通知 2.通知对象
        // 需求的最低版本是16
        manager.notify(count, notification);
    }
}
