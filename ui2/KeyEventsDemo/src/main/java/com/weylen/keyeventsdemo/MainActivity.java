package com.weylen.keyeventsdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    // 按键事件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override // 当按返回键的时候调用
    public void onBackPressed() {
//        super.onBackPressed();
        showExitDialog();
        Log.d("zhou", "onBackPressed: ");
    }

//    @Override // 按键的按下事件 1. 按键的编号 2.按键事件对象
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("zhou", "onKeyDown: keyCode:"+keyCode);
//        if (keyCode == KeyEvent.KEYCODE_BACK){ // 返回键
//            showExitDialog();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void showExitDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否要退出")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();


    }
}
