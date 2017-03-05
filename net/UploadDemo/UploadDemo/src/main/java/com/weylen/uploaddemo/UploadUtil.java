package com.weylen.uploaddemo;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhou on 2016/5/4.
 */
public class UploadUtil {

    /**
     * 上传完成时的回调接口
     */
    public interface OnUploadCompleteListener{
        /**
         * 上传完成时调用此方法
         * @param data 服务器返回的数据
         * @param status 上传的状态码 1 表示上传成功 其他表示上传失败
         */
        void onUpload(String data, int status);
    }

    public static void upload(String url, FileInfo fileInfo, OnUploadCompleteListener listener){
        new UploadThread(url, fileInfo, listener).start();
    }

    private static class UploadThread extends Thread{

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (listener != null){
                    listener.onUpload((String) msg.obj, msg.what);
                }
            }
        };

        private String url;
        private FileInfo fileInfo;
        private OnUploadCompleteListener listener;
        private UploadThread(String url, FileInfo fileInfo, OnUploadCompleteListener listener){
            this.url = url;
            this.fileInfo = fileInfo;
            this.listener = listener;
        }

        @Override
        public void run() {
            // URLConnection 默认不能上传大文件-->20M
            try {
                // 1.创建URL对象 打开到此url的连接
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                // 2.设置属性
                connection.setDoInput(true); // 可读
                connection.setDoOutput(true); // 可写
                connection.setRequestMethod("POST"); // 上传必须使用POST请求
                connection.setUseCaches(false); // POST请求不能使用缓存
                connection.setConnectTimeout(10000); // 连接超时
                connection.setReadTimeout(60000); // 读取数据超时
                // 设置流的具体大小
//                connection.setFixedLengthStreamingMode();// 在已知流的大小下使用
                // 设置块的大小
                connection.setChunkedStreamingMode(2 * 1024 * 1024); // 在未知流的大小下使用
                // 封装的文件名
                connection.setRequestProperty("FileName", fileInfo.getName()); // 设置请求的头信息
                // 创建要上传文件的输入流对象
                FileInputStream fileInputStream = new FileInputStream(fileInfo.getPath());
                // 封装文件的长度
                connection.setRequestProperty("FileLength", String.valueOf(fileInputStream.available()));

                // 3.获取输出流 写入文件
                OutputStream os = connection.getOutputStream();

                // 写流
                int i ;
                byte[] bytes = new byte[2048];
                while ((i = fileInputStream.read(bytes)) != -1 ){
                    os.write(bytes, 0 , i);
                    os.flush();
                }
                fileInputStream.close();

                // 4.获取服务器返回的数据
                int code = connection.getResponseCode(); // 响应状态码
                if (code == 200){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                    String line;
                    String result = "";
                    while ((line = bufferedReader.readLine()) != null){
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    sendMessage(result, 1);
                }else {
                    sendMessage(null, -1);
                }

            } catch (IOException e) {
                e.printStackTrace();
                sendMessage(null, -1);
            }
        }

        public void sendMessage(String result, int status){
            Message message = Message.obtain();
            message.what = status;
            message.obj = result;
            handler.sendMessage(message);
        }
    }
}
