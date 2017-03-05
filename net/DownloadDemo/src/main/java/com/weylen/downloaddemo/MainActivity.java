package com.weylen.downloaddemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // 如何将一张图片压缩到100K以内
    // 上传与下载
    // 下载--> http --> 文件流 --> 保存在sdcard上面

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
    }

    private ProgressDialog progressDialog;
    public void downloadNormalFile(View view){
        // 检查SDCard是否可用 在6.0需要先获取SDCard操作权限
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(MainActivity.this, "您的储存卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = ProgressDialog.show(this, "", "下载中...");
        String downloadUrl = "http://192.168.1.101:8080/webapps/download/xxxx.msi";
        DownloadUtil.downloadFile(downloadUrl,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),
                new DownloadUtil.OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(String filePath, int code) {
                        if (progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        String source = code != 1 ? "下载失败" : "下载成功";
                        Toast.makeText(MainActivity.this, source, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void downloadImage(View view){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(MainActivity.this, "您的储存卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = ProgressDialog.show(this, "", "下载中...");
        String downloadUrl = "http://192.168.1.101:8080/webapps/download/test123.jpg";
        DownloadUtil.downloadFile(downloadUrl,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(),
                new DownloadUtil.OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(String filePath, int code) {
                        if (progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        // OOM
//                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                        Log.d("zhou", "onDownloadComplete: bitmap:size:"+bitmap.getByteCount());
                        imageView.setImageBitmap(BitmapUtil.parse(filePath, 500, 500));
                    }
                });
    }

}
