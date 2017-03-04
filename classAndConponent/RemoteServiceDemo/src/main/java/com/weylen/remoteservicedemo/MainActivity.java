package com.weylen.remoteservicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 远程服务需要用到的技术：AIDL（ANDROID INTERFACE DEFINE LANGUAGE）
    // 1.
    // 1.创建远程服务的步骤

    private boolean isBind; // 用来记录是否已经绑定过服务
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 绑定服务
     * @param view
     */
    public void bindServiceClick(View view){
        if (!isBind){
            Intent service = new Intent(this, RemoteService.class);
            bindService(service, connection, BIND_AUTO_CREATE);
        }

    }

    /**
     * 解除绑定服务
     * @param view
     */
    public void unbindServiceClick(View view){
        if (isBind = true){
            unbindService(connection);
            isBind = false;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override // 1. 服务的组件名 2. 返回Service.onBind的IBinder对象
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            Toast.makeText(MainActivity.this, "绑定服务成功", Toast.LENGTH_SHORT).show();
            RemoteAidl remoteAidl = RemoteAidl.Stub.asInterface(service);
            try {
                String nameStr = remoteAidl.getName();
                Log.d("zhou", "onServiceConnected: 设置前:"+nameStr);
                remoteAidl.setName("张三");
                nameStr = remoteAidl.getName();
                Log.d("zhou", "onServiceConnected: 设置后:"+nameStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind = true){
            unbindService(connection);
            isBind = false;
        }
    }
}
