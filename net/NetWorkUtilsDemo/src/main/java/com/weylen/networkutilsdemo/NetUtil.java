package com.weylen.networkutilsdemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by zhou on 2016/4/28.
 */
public class NetUtil {

    interface ResponseInputStream{
        void onResponse(InputStream inputStream, int status);
    }

    interface ResponseString{
        void onResponse(String data, int status);
    }

    public static void getRequest(String url, ResponseInputStream listener){
        new RequestThread(url, listener, null).start();
    }

    public static void getRequest(String url, ResponseString listener){
        new RequestThread(url, null, listener).start();
    }

    public static void postRequest(String url, HashMap<String, String> params, ResponseInputStream listener){
        new RequestThread(url, params, listener, null).start();
    }

    public static void postRequest(String url, HashMap<String, String> params, ResponseString listener){
        new RequestThread(url, params, null, listener).start();
    }

    private static class RequestThread extends Thread{

        private String url;
        private ResponseInputStream responseInputStream;
        private ResponseString  responseString;
        private HashMap<String, String> params;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1 && responseInputStream != null){
                    responseInputStream.onResponse((InputStream) msg.obj, 1);
                }else if (msg.what == 2 && responseString != null){
                    responseString.onResponse((String) msg.obj, 1);
                }else if (msg.what == -1){
                    if (responseString != null){
                        responseString.onResponse(null, -1);
                    }

                    if (responseInputStream != null){
                        responseInputStream.onResponse(null, -1);
                    }
                }
            }
        };

        private RequestThread(String url, ResponseInputStream responseInputStream,
                               ResponseString responseString){
            this.responseInputStream = responseInputStream;
            this.responseString = responseString;
            this.url = url;
        }

        private RequestThread(String url, HashMap<String, String> params, ResponseInputStream responseInputStream,
                              ResponseString responseString){
            this.responseInputStream = responseInputStream;
            this.responseString = responseString;
            this.url = url;
            this.params = params;
        }

        private void sendMessage(InputStream inputStream){
            Message message = Message.obtain();
            message.obj = inputStream;
            message.what = 1; // 表示现在传递的是一个流
            handler.sendMessage(message);
        }

        private void sendMessage(String data){
            Message message = Message.obtain();
            message.obj = data;
            message.what = 2; // 表示现在传递的是一个字符串
            handler.sendMessage(message);
        }

        /**
         * 发送错误
         * @param error
         */
        private void sendMessage(int error){
            Message message = Message.obtain();
            message.what = error;
            handler.sendMessage(message);
        }

        /**
         * 解析输入流 得到此流对应的字符串
         * @param inputStream
         * @return
         */
        private String parseInputStream(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String result = "";
            while((line = bufferedReader.readLine()) != null){
//                result += line + "\n"; // 加换行符
                result += line;
            }
            bufferedReader.close();
            return result;
        }

        /**
         * 解析输入流 得到此流对应的字符串
         * @param inputStream
         * @return
         */
        private String parseInputStream(InputStream inputStream, String charset) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line;
            String result = "";
            while((line = bufferedReader.readLine()) != null){
//                result += line + "\n"; // 加换行符
                result += line;
            }
            bufferedReader.close();
            return result;
        }

        private void doGet(URLConnection connection) throws IOException {
            // 3.获取到此URL的输入流
            InputStream inputStream = connection.getInputStream();
            if (responseInputStream != null){
                sendMessage(inputStream);
            }else if (responseString != null){
                String result = parseInputStream(inputStream, "GBK");
                sendMessage(result);
            }
        }

        private void doPost(URLConnection connection) throws IOException{
            // 设置请求的属性
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setUseCaches(false); // POST不能使用缓存
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 写参数
            OutputStream outputStream = httpURLConnection.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream);
            out.println(parseParams());
            out.flush();

            InputStream inputStream = httpURLConnection.getInputStream();
            if (responseInputStream != null){
                sendMessage(inputStream);
            }else if (responseString != null){
                String result = parseInputStream(inputStream, "GBK");
                sendMessage(result);
            }
        }

        private String parseParams(){
            // 第一种：迭代key
            Set<String> keySet = params.keySet();
            Iterator<String> keyIterator = keySet.iterator();
            String paramStr = "";
            while (keyIterator.hasNext()){
                String key = keyIterator.next();
                String value = params.get(key);
                paramStr += key+"="+value + "&";
            }
            if (paramStr.length() > 0){
                paramStr = paramStr.substring(0, paramStr.length() - 1);
            }
            Log.d("zhou", "doPost: paramStr:"+paramStr);

            // 第二种：迭代key value
//            Set<Map.Entry<String, String>> setEntry = params.entrySet();
//            Iterator<Map.Entry<String,String>> mapIterator = setEntry.iterator();
//            while (mapIterator.hasNext()){
//                Map.Entry<String, String> entry = mapIterator.next();
//                String key = entry.getKey();
//                String value = entry.getValue();
//            }
            return paramStr;
        }

        @Override
        public void run() {
            // java标准请求方式的步骤
            try {
                // 1.创建URL对象
                URL requestUrl = new URL(url);
                // 2.打开连接
                URLConnection connection = requestUrl.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(30000);
                // 判断是get请求还是post请求
                if (params == null || params.isEmpty()){
                    doGet(connection);
                }else{
                    doPost(connection);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendMessage(-1);
            }
        }
    }
}
