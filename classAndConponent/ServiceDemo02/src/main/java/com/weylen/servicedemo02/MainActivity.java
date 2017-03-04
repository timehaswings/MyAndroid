package com.weylen.servicedemo02;

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

import com.weylen.remoteservicedemo.RemoteAidl;

public class MainActivity extends AppCompatActivity {

    // 当手机电量过低或者是内存不足的时候，系统首先杀死没有服务的进程，在按照服务的优先级
    // 比如：每隔15分钟定位一次 服务+线程 服务+闹钟

    // 本地服务 远程服务


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bindRemoteService(View view){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.weylen.remoteservicedemo",
                "com.weylen.remoteservicedemo.RemoteService"));
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "绑定远程服务成功", Toast.LENGTH_SHORT).show();
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

        }
    };
}
