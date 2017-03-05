package com.weylen.downloaddemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by zhou on 2016/4/28.
 */
public class DownloadUtil {

    interface OnDownloadCompleteListener{
        void onDownloadComplete(String filePath, int code);
    }

    /**
     *  下载文件
     * @param downloadUrl 下载的文件的地址
     * @param filePath 保存文件的文件夹路径
     * @param listener 下载完成的监听
     */
    public static void downloadFile(String downloadUrl, String filePath, OnDownloadCompleteListener listener){
        new DownloadThread(downloadUrl, filePath, listener).start();
    }

    private static class DownloadThread extends Thread{

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (listener != null){
                    listener.onDownloadComplete(filePath, msg.what);
                }
            }
        };

        private String downloadUrl;
        private String filePath;
        private OnDownloadCompleteListener listener;

        private DownloadThread(String downloadUrl, String filePath, OnDownloadCompleteListener listener){
            this.downloadUrl = downloadUrl;
            this.filePath = filePath;
            this.listener = listener;
        }

        /**
         * 1表示下载成功 -1表示下载失败
         * @param code
         */
        private void sendMessage(int code){
            handler.sendEmptyMessage(code);
        }

        private static String getUrlName(String downloadUrl) throws UnsupportedEncodingException {
            return URLEncoder.encode(downloadUrl, "UTF-8");
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = new URL(downloadUrl).openConnection().getInputStream();
                // 构建输出流对象
                String name = getUrlName(downloadUrl);
                Log.d("zhou", "run: 文件名："+name);
                filePath = filePath + File.separator + name;
                FileOutputStream fos = new FileOutputStream(filePath);
                // 用来接收每次读取到的字节个数
                int i;
                // 用来接收每次读取到的字节
                byte[] bytes = new byte[2048];
                while ((i = inputStream.read(bytes)) != -1){
                    fos.write(bytes, 0, i);
                    fos.flush();
                }
                fos.close();
                inputStream.close();
                sendMessage(1);
            } catch (IOException e) {
                e.printStackTrace();
                sendMessage(-1);
            }
        }
    }
}
