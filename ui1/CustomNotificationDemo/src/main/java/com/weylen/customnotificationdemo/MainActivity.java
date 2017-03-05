package com.weylen.customnotificationdemo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    // 自定义通知

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮的点击事件
     * @param view
     */
    public void showCustomNf(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setTicker("正在播放：小龙人");

        // 远程视图 1.包名 2.布局的资源的id
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.remote_views);
        // 设置内容或者其他属性，通过反射的方式
        // 1.ViewID 控件的id值 2.颜色值
        remoteViews.setTextColor(R.id.musicInfo, Color.RED);// 设置文本的颜色
        remoteViews.setTextViewText(R.id.musicInfo, "李四"); // 设置文本的内容
//        remoteViews.setTextViewTextSize(R.id.musicInfo, );
        // 1.控件的id 2.方法的名字 3.值
        remoteViews.setInt(R.id.musicInfo, "setBackgroundColor", Color.GREEN);
        remoteViews.setCharSequence(R.id.musicName, "setText", "好汉歌");
        remoteViews.setInt(R.id.image, "setImageResource", R.mipmap.filelist_audio);

        // 给按钮绑定监听
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:46465"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.cancel, pendingIntent);

        builder.setContent(remoteViews); // 设置通知显示的自定义视图

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100, builder.build());
    }
}
