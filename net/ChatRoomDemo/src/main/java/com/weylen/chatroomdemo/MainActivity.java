package com.weylen.chatroomdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    // Socket操作同样的是属于网络操作，所以就必须放在异步线程执行
    // openfire

    private EditText inputEdit;
    private TextView textView;
    private Socket client;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && msg.obj != null && msg.obj instanceof Socket){
                client = (Socket) msg.obj;
                new ReadFromServer(client).start();// 启动读取服务器的线程
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.message);
        inputEdit = (EditText) findViewById(R.id.input);
    }

    /**
     * 发送按钮
     * @param view
     */
    public void sendClick(View view){
        // 首先应该取出输入框里面的内容
        String text = inputEdit.getText().toString();
        if (TextUtils.isEmpty(text)){
            Toast.makeText(MainActivity.this, "清输入发送的内容", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            sendMessage(text);
            inputEdit.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String text) throws IOException {
        if (client == null){
            new ConnectServer(text).start();
        }else{
            PrintWriter printWriter = new PrintWriter(client.getOutputStream());
            printWriter.println(text);
            printWriter.flush();
        }
    }

    private class ConnectServer extends Thread{

        private String text;
        public ConnectServer(String text){
            this.text = text;
        }

        @Override
        public void run() {
            try {
                Socket client = new Socket("192.168.1.102", 54321);
//                client.setSoTimeout(5000);
                Log.d("zhou", "run: 连接成功");
                if (text != null){
                    PrintWriter printWriter = new PrintWriter(client.getOutputStream());
                    printWriter.println(text);
                    printWriter.flush();
                    Log.d("zhou", "run: 发送消息成功");
                }

                Message message = Message.obtain();
                message.obj = client;
                message.what = 1;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(-1);
                Log.d("zhou", "run: e:"+e.getMessage());
            }
        }
    }

    private class ReadFromServer extends Thread{

        private Socket client;
        private boolean isExit;

        private ReadFromServer(Socket client){
            this.client = client;
        }

        @Override
        public void run(){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "GBK"));
                String line;
                while(!isExit && (line = reader.readLine()) != null){
                    System.out.println(line);
                    if (line.equals("exitOk")){
                        isExit = true;
                    }
                    final String msg = line;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.append(msg);
                            textView.append("\n");
                        }
                    });

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
