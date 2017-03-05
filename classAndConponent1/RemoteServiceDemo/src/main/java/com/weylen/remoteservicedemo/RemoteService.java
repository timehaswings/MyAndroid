package com.weylen.remoteservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by zhou on 2016/4/21.
 */
public class RemoteService extends Service{

    public RemoteAidl.Stub stub = new RemoteAidl.Stub() {

        private String name;

        @Override
        public void setName(String name) throws RemoteException {
            this.name = name;
        }

        @Override
        public String getName() throws RemoteException {
            return this.name == null ? "default name " : this.name;
        }
    };

    @Nullable
    @Override // 就是在绑定服务的时候调用，返回决定绑定是否成功 null表示绑定失败
    public IBinder onBind(Intent intent) {
        return stub;
    }


}
