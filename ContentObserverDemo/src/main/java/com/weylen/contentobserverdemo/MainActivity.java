package com.weylen.contentobserverdemo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // ContentObserver 数据观察者 当有数据发生变化的时候可以进行监听。

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 10){
                Toast.makeText(MainActivity.this, "数据库发生了变化", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private TestObserver testObserver = new TestObserver(handler);

    public static final Uri uri = Uri.parse("content://test123.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerContentObserverClick(View view){
        // 1.Uri表示需要监听数据的地址
        // 2.当根Uri和其匹配时，数据发生变化是否需要通知
        // 3.接收数据变化的对象
        getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
                true,
                testObserver);

        getContentResolver().registerContentObserver(uri, true, testObserver);
        // 取消数据观察监听。
//        getContentResolver().unregisterContentObserver(testObserver);
    }

    public void notifyDataChangedClick(View view){
        // 当点击此按钮时就模拟数据发生变化
        // 1.通知哪一个Uri对应的数据发生变化 2.通知指定的ContentObserver 给null则通知所有注册此监听器的Observer
        getContentResolver().notifyChange(uri, null);
    }
}
