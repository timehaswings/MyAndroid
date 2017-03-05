package com.weylen.fragmentbackstackdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int count; // 计数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // 添加Fragment的点击事件
    public void addFragment(View view){
        count++;

        NumberFragment numberFragment = new NumberFragment();
        // 此方法只能在未被依附Activity之前调用
        Bundle bundle = new Bundle(); // 构建一个Bundle对象
        bundle.putInt("Number", count); // 封装数据
        numberFragment.setArguments(bundle); // 设置参数

        // 替换Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, NumberFragment.getInstance(count), NumberFragment.TAG)
                .addToBackStack(null) // 添加进返回堆栈
                .commit();
    }

    // 返回按钮的点击事件
    public void backClick(View view){
        // 先获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        // 获取所有返回堆栈里面的Fragment的个数
        int count = manager.getBackStackEntryCount();
        Log.d("zhou", "backClick: count:"+count);
        if (count <= 1){
            showExitDialog();
        }else{
//            onBackPressed();
            manager.popBackStack(); // 从返回堆栈弹出栈顶的一个
        }
    }

    private void showExitDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要退出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
