package com.weylen.toastdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 提示用户：1.Toast 2.Dialog 3.Notification

    // NOTE: Toast效果不能放在异步线程执行。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private int count;
    private Toast toast;
    
    /**
     * 按钮的点击事件
     * @param view
     */
    public void showNormalToastClick(View view){
        count++;
        if (toast == null){
            toast = Toast.makeText(this, "恭喜你，晋级英勇黄铜四"+(count), Toast.LENGTH_SHORT);
        }
        toast.setText("恭喜你，晋级英勇黄铜四"+(count)); // 设置Toast显示的文本
        toast.show();
    }

    /**
     * 自定义Toast按钮的点击事件
     * @param view
     */
    public void showCustomToastClick(View view){
        count++;
        // 1.创建Toast对象
        Toast toast = new Toast(this);
        // 2.设置属性
        toast.setDuration(Toast.LENGTH_LONG);
        View v = LayoutInflater.from(this).inflate(R.layout.custom_toast, null);
        // 找到TextView
        TextView messageView = (TextView) v.findViewById(R.id.message);
        int resId = 0;
        String text = null;
        if (count % 2 == 0){ // 1是哭脸
            resId = R.mipmap.emoji_87;
            text = "你在完成你的晋级赛失败了，掉到了英勇黄铜V";
        }else{ // 0是笑脸
            resId = R.mipmap.emoji_85;
            text = "你在完成你的晋级赛成功，晋级到英勇黄铜IV";
        }
        messageView.setText(text);
        // 绘制图片
        messageView.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);

        toast.setView(v);
        // 1.重心 2.x的偏移量 3.y的偏移量
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        // 显示
        toast.show();
    }
}
