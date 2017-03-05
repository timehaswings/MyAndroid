package com.weylen.httpjavamode;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    // 数据的获取方式：get(请求参数是带在地址后面) post(参数是写入在body)
    // Http请求的方式：java.net.* 标准方式 apache重新封装 apache.client(在6.0api已经移除)
    // 数据接口：请求地址，请求参数，返回的数据 200 404 500
    // http://192.168.1.102:8080/webapps/login.action?account=admin&password=test

    // 1.所有的网络请求都必须放在异步线程
    // 2.异步线程与主线程的交互(Activity.runOnUiThread View.post Handler BroadcastReceiver)
    // 3.所有的网络请求都必须加上权限 <uses-permission android:name="android.permission.INTERNET"/>

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null){
                textView.setText(msg.obj.toString());
            }
        }
    };

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.result);
    }

    public void onJavaGetRequestClick(View view){
        new GetRequestThread().start();
    }

    public void onJavaPostRequestClick(View view){
        new PostRequestThread().start();
    }

    private class GetRequestThread extends Thread{
        @Override
        public void run() {
            String account = "张三";
            try {
                // UTF编码一个中文是占3个字节 GBK的编码一个中文占2个字节
                account = URLEncoder.encode(account, "UTF-8");
                URLDecoder.decode(account, "UTF-8"); // 以什么编码进行转换就应该以什么什么编码进行解析。
                Log.d("zhou", "run: 转换后的account:"+account);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 请求的地址
            final String requestUrl = "http://192.168.1.102:8080/webapps/login.action?account="+account+"&password=admin";
            try {
                // 1. 创建URL对象
                URL url = new URL(requestUrl);
                // 2. 打开连接得到连接对象
                URLConnection connection = url.openConnection();
                // 3. 得到服务器的响应数据
                InputStream inputStream = connection.getInputStream();
                // 转换成字符流 如果不设置编码 则会以平台默认编码进行转换(UTF-8)
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                // 继续封装BufferedReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                // 用来接收读取到的每一行数据
                String line ;
                // 拼接每一行的数据
                String result = "";
                // 循环读取 如果读取到的数据为null 则说明读到流的末尾
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                // 关闭流
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                Log.d("zhou", "run: result:"+result);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class PostRequestThread extends Thread{
        @Override
        public void run() {
            String requestUrl = "http://192.168.1.102:8080/webapps/login.action";
            try {
                // 创建URL对象
                URL url = new URL(requestUrl);
                // 打开连接得到URLConnection对象
                URLConnection connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                // 设置属性
                connection.setDoInput(true); // 可以读取数据
                connection.setDoOutput(true); // 可以写数据
                connection.setUseCaches(false); // POST请求不能使用缓存
                connection.setConnectTimeout(10000); // 连接超时
                connection.setReadTimeout(30000); // 读取数据超时
                httpConnection.setRequestMethod("POST"); // 设置请求方法 请求方法的名字必须全部大写

                // 向服务器写入数据
                OutputStream os = httpConnection.getOutputStream(); // 得到服务器的输出流
                String params = "account=张三&password=admin";
                PrintWriter writer = new PrintWriter(os);
                writer.write(params);
                writer.flush();

                // 读取数据
                InputStream inputStream = connection.getInputStream();
                // 转换成字符流 如果不设置编码 则会以平台默认编码进行转换(UTF-8)
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
                // 继续封装BufferedReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                // 用来接收读取到的每一行数据
                String line ;
                // 拼接每一行的数据
                String result = "";
                // 循环读取 如果读取到的数据为null 则说明读到流的末尾
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                // 关闭流
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                Log.d("zhou", "run: result:"+result);

                Message message = Message.obtain();
                message.obj = result;
                handler.sendMessage(message);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
