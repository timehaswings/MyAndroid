package com.weylen.alertdialogdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Dialog

    private static final String[] COLORS = {"红色", "橙色", "黄色", "绿色", "蓝色", "青色", "紫色"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 显示标准对话框的点击事件
     * @param view
     */
    public void showNormalDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置标题
        builder.setTitle("提示");
        // 设置信息
        builder.setMessage("您是否要退出此App?");
        // 设置图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        // 设置按钮 积极地
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override // 1.对话框对象 2.哪一个按钮
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // 消极的
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override // 1.对话框对象 2.哪一个按钮
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // 中性的 中立的
        builder.setNeutralButton("你猜", new DialogInterface.OnClickListener() {
            @Override // 1.对话框对象 2.哪一个按钮
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create(); // 创建对话框
        // 指的是点击对话框的外部或者返回键
        alertDialog.setCancelable(false); // 是否可以取消
        // 设置点击对话框的外部是否可以取消
        alertDialog.setCanceledOnTouchOutside(false);
        // 设置对话框的销毁监听
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d("zhou", "onDismiss: ");
            }
        });
        // 设置对话框的取消监听
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d("zhou", "onCancel: ");
            }
        });

        // 显示
        alertDialog.show();
    }

    /**
     * 显示列表对话框
     * @param view
     */
    public void showListDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 1. 列表的数据 2.列表的item点击事件
        builder.setItems(COLORS, new DialogInterface.OnClickListener() {
            @Override // 1.对话框对象 2.下标
            public void onClick(DialogInterface dialog, int which) {
                Log.d("zhou", "onClick: 点击的item的下标："+which);
                Toast.makeText(MainActivity.this, COLORS[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setTitle("选择你喜欢的颜色");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("zhou", "onClick: 点击了确定");
            }
        });
        builder.create().show();
    }

    /**
     * 单选对话框
     * @param view
     */
    public void showSingleDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 1.列表的数据 数组 2.默认选择的下标 如果没有默认的则给-1 3.列表的点击事件
        builder.setSingleChoiceItems(COLORS, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("zhou", "onClick: 点击的item的下标："+which);
                Toast.makeText(MainActivity.this, COLORS[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setTitle("选择你喜欢的颜色");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("zhou", "onClick: 点击了确定");
            }
        });
        builder.create().show();
    }

    /**
     * 多选对话框
     * @param view
     */
    public void showMultiChoiceDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final boolean[] booleen = new boolean[COLORS.length];
        booleen[0] = true;
        booleen[6] = true;
        // 1.列表的数据 数组 2.默认选择的下标，如果给null则说明没有被选择的，如果不给null，则boolean数组的长度必须
        // 和列表数据的数组长度一致。3.列表的点击事件
        builder.setMultiChoiceItems(COLORS, booleen, new DialogInterface.OnMultiChoiceClickListener() {
            @Override // 1.对话框对象 2.哪一个item的状态被改变了 3.当前item的状态
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // 修改boolean数组里面对应item的状态
                booleen[which] = isChecked;
            }
        });
        builder.setTitle("选择你喜欢的颜色");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = booleen.length;
                for (int i = 0; i < size; i++){
                    if (booleen[i]){
                        Log.d("zhou", "onClick: "+COLORS[i]);
                    }
                }
            }
        });
        builder.create().show();
    }

    /**
     * 显示进度对话框
     * @param view
     */
    public void showProgressDialog(View view){
        // 无进度 不想要标题 就给空
//        ProgressDialog dialog = ProgressDialog.show(this, "", "正在更新...");
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("正在更新...");
        // 设置对话框窗口的按键事件
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override // 1.对话框对象 2.按键的Code值 3.按键事件对象
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK){ // 如果按的是返回键
                    dialog.dismiss();
                }
                return false;
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 显示精确进度对话框
     * @param view
     */
    public void showProgressDialog2(View view){
        final ProgressDialog dialog = new ProgressDialog(this);
        // 设置进度条的样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setIndeterminate(false);// 设置是否为模糊状态
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        dialog.setMessage("信息");
        dialog.show();
        // 启动新线程
        new Thread(){
            @Override
            public void run() {
                dialog.setMax(100); // 设置进度条的最大值
                dialog.setProgress(0);
                int progress = 0;
                while(dialog.getProgress() < dialog.getMax()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress += 8;
                    dialog.setProgress(progress);// 在原有的进度上增加8个点
                    Log.d("zhou", "run: progress:"+dialog.getProgress());
                }
                Log.d("zhou", "run: 下载完成");

            }
        }.start();
    }
}
