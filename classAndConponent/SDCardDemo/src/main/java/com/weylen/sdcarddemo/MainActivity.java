package com.weylen.sdcarddemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // 储存卡 SDCard

    // 读取SDCard信息不需要权限，读写操作需要权限

    private static final int REQUEST_SDCARD = 1;

    private static String[] PERMISSIONS_SDCARD = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮监听器
     * @param view
     */
    public void getInfoFromSdcard(View view){
        showSdcard();
//        logSDCardInfo();
    }

    public void showSdcard() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestSDCardPermissions();

        } else {
            logSDCardInfo();
        }
    }

    private void requestSDCardPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            ActivityCompat
                    .requestPermissions(MainActivity.this, PERMISSIONS_SDCARD,
                            REQUEST_SDCARD);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_SDCARD, REQUEST_SDCARD);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_SDCARD) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                logSDCardInfo();
            } else {
                Toast.makeText(MainActivity.this, "未获取到读取SDCard的权限", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 输出SDCard的信息
     */
    private void logSDCardInfo(){
        // 先检查SDCard是否可用
        // 获取SDCard的状态
        String state = Environment.getExternalStorageState();
        Log.d("zhou", "getInfoFromSdcard: state:"+state);
        // 储存卡的状态不可用
        if (!Environment.MEDIA_MOUNTED.equals(state)){
            Toast.makeText(MainActivity.this, "SDCard不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        File rootFile = Environment.getRootDirectory();// 根目录文件夹
        File sdcardFile = Environment.getExternalStorageDirectory();// 获取SDCard的目录
        File dataFile = Environment.getDataDirectory(); // Data目录
        File cacheFile = Environment.getDownloadCacheDirectory();// 下载缓存目录
        // 获取sdcard下面不同类型的文件夹
        File picFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d("zhou", "logSDCardInfo: rootFile:"+rootFile);
        Log.d("zhou", "logSDCardInfo: sdcardFile:"+sdcardFile);
        Log.d("zhou", "logSDCardInfo: dataFile:"+dataFile);
        Log.d("zhou", "logSDCardInfo: cacheFile:"+cacheFile);
        Log.d("zhou", "logSDCardInfo: picFile:"+picFile);

        // 显示SDCARD的图片
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picFile+File.separator+"testpic.jpg"));

        // 写数据
        try {
            FileOutputStream fos = new FileOutputStream(new File(picFile, "test123.jpg"));
            // 读取assets下面的图片通过
            InputStream in = getAssets().open("pic/test123.jpg"); // 地址相对于assets包
            int i = -1; // 用来记录读取到的字节数
            byte[] bytes = new byte[1024];// 用来保存每次读取到的字节
            while ((i = in.read(bytes)) != -1){ // ==-1表示读取到流的末尾
                fos.write(bytes, 0, i);
                fos.flush();
            }
            in.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
