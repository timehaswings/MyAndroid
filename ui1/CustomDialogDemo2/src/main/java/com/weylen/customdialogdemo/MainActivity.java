package com.weylen.customdialogdemo;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 显示自定义对话框按钮的监听器
     * @param view
     */
    public void showCustomDialog(View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // 1.Context 2.themeResId 样式资源id
        // android.R.style.Theme_Holo_Dialog_NoActionBar
        final Dialog dialog = new Dialog(this, R.style.Dialog_NoActionBar);
//        dialog.setContentView(R.layout.dialog_buy);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_buy, null);
        // 给按钮绑定监听
        dialogView.findViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // -1 表示MATCH_PARENT -2 WRAP_CONTENT
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int)
                (displayMetrics.widthPixels - 20 * displayMetrics.density), -2);
        // 1.要被添加的视图对象 2.被添加视图的布局参数
//        dialogView.setLayoutParams(layoutParams);
//        dialog.setContentView(dialogView);
        dialog.setContentView(dialogView, layoutParams);


        // 设置窗口大小
        Window window = dialog.getWindow(); // 获取对话框的窗口对象
        // 获取窗口的属性对象
        WindowManager.LayoutParams windowManagerParams = window.getAttributes();
        // 设置窗口的宽度
//        windowManagerParams.x = displayMetrics.widthPixels;
        windowManagerParams.gravity = Gravity.BOTTOM;
        Log.d("zhou", "showCustomDialog: 窗口的宽度:"+windowManagerParams.x);
        // 重新设定窗口属性
        window.setAttributes(windowManagerParams);

        dialog.show();
    }

    /**
     * 第二个按钮
     * @param view
     */
    public void showCustomDialog2(View view){
        final CustomDialog customDialog = new CustomDialog(this, R.style.Dialog_NoActionBar);
        customDialog.setMessageTitle("谢宁东：东哥百万实盘内参三期");
        customDialog.setMessageTime("服务周期：2015-01-10 至 2016-01-10");
        customDialog.setMessagePrice("订阅价格：1000 元");
        customDialog.setDialogInterface(new CustomDialog.DialogInterface() {
            @Override
            public void onButtonClick() {
                customDialog.dismiss();
                Toast.makeText(MainActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
            }
        });
        customDialog.show();
    }
}
