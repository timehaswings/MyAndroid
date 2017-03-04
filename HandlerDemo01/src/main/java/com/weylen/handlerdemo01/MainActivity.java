package com.weylen.handlerdemo01;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Handler
    // 1. 用来执行一系列任务,并不是直接执行任务，而是将任务添加到消息队列，消息队列里面的消息(Runnable)按照执行的延迟时间进行排列
    //    post postDelay postAtTime removeCallbacks移除需要执行的任务
    // 2. 完成异步线程与主线程的交互

    // 异步线程要修改界面：Activity.runOnUiThread, View.post


    /**
     * 所有handler的对象操作都是在创建此handler对象的线程里面执行。
     */
    private Handler handler;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        handler = new Handler();
    }

    private Runnable counterRunnable = new Runnable() {
        @Override
        public void run() {
            count++;
            textView.setText(String.valueOf(count));
            handler.postDelayed(counterRunnable, 1000);
        }
    };

    private int count = 0;
    private boolean isStop = false;
    // 点击按钮让数字每隔一秒增加1
    public void startCounterClick(View view){
//        isStop = false;
//        new Thread(){
//            @Override
//            public void run() {
//                while(!isStop){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (!isStop){
//                        count++;
//                        MainActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView.setText(String.valueOf(count));
//                            }
//                        });
//                    }
//                }
//
//            }
//        }.start();
        // 延迟1秒钟执行runnable，实际上就是调用runnable.run方法,
        // 当调用用run方法里面的代码之后，那么任务就算执行完毕。
       handler.postDelayed(counterRunnable, 1000);
    }

    // 点击按钮停止计数
    public void stopCounterClick(View view){
//        isStop = true;
        // 移除任务 实际上移除任务也就是将消息从消息队列移除掉
        handler.removeCallbacks(counterRunnable);

        new CustomThread().start();
    }

    class CustomThread extends Thread{

        public CustomThread(){
            // 是属于主线程。
            handler = new Handler();
        }

        @Override
        public void run() {
            // 属于异步线程
            handler = new Handler();
        }
    }
}
