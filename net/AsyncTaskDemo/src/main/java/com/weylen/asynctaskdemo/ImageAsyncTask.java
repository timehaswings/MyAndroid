package com.weylen.asynctaskdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by zhou on 2016/4/29.
 * 1.Params 参数的类型
 * 2.Progress 进度类型
 * 3.Result 结果类型
 */
public class ImageAsyncTask extends AsyncTask<String, Integer, Bitmap>{

    // 图片的缓存分为两种：
    // 内存缓存(将对象保存在内存里面，这种操作只适合于小图片) 读取快但是占用内存
    // 文件缓存(将图片保存在SDCard里面) 读取慢但是不消耗内存

    // 1.首先检查内存里面是否存在此图片，如果存在就直接显示，不存在
    // 2.检查文件里面是否存在此图片，如果存在则解析然后显示，不存在
    // 3.下载图片，下载完成，保存文件，保存Bitmap对象

    private static final String defaultCache = Environment.getExternalStorageDirectory() + File.separator
            + "defaultCache";

    private String cache = defaultCache;

    private ImageView imageView;
    private ProgressBar progressBar;
    public ImageAsyncTask(ImageView imageView, ProgressBar progressBar){
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    private void checkCacheFile(String filePath){
        File cacheFile = new File(filePath);
        if (!cacheFile.exists()){
            cacheFile.mkdirs();
        }
    }

    /**
     * 设置缓存的位置
     * @param newFile
     */
    public void setCache(String newFile){
        this.cache = newFile;
    }

    @Override // 此方法在异步线程使用 当使用execute(String... params),系统调用此方法;
    protected Bitmap doInBackground(String... params) {
        if (params != null && params.length > 0){
            String requestUrl = params[0]; // 取出传入的地址参数
            try {
                // 检查默认缓存的文件夹是否存在
                checkCacheFile(cache);
                String fileName = formatUrl(requestUrl);
                // 检查内存里面是否存在图片对象
                Bitmap bitmap = CacheUtil.getInstance().getBitmap(fileName);
                if (bitmap != null){ // 如果对象存在 则直接返回
                    Log.d("zhou", "doInBackground: 内存缓存存在，直接返回");
                    return bitmap;
                }
                // 对象不存在，则检查SDCard里面是否有此图片
                String filePath = cache + File.separator + fileName;
                bitmap = BitmapUtil.parse(filePath, 500, 500);
                if (bitmap != null){
                    // 保存图片在内存里面
                    CacheUtil.getInstance().setBitmap(fileName, bitmap);
                    Log.d("zhou", "doInBackground: 文件缓存存在，直接返回");
                    return bitmap;
                }
                Log.d("zhou", "doInBackground: 两种保存都不存在，执行下载");
                // 执行下载
                URLConnection connection = new URL(requestUrl).openConnection();
                int length = connection.getContentLength();
                InputStream inputStream = connection.getInputStream();
                /****文件缓存****/
                // 创建文件对应的输出流对象
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                int i;
                byte[] bytes = new byte[2048];
                while ((i = inputStream.read(bytes)) != -1){
                    fileOutputStream.write(bytes, 0, i);
                    // 发布进度
                    publishProgress(length, i);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();
                // 返回对象
                bitmap = BitmapUtil.parse(filePath, 500, 500);
                CacheUtil.getInstance().setBitmap(fileName, bitmap);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String formatUrl(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "UTF-8");
    }

    @Override // 进度时更新时调用 当时用 publishProgress(Integer... values)，系统调用此方法;
    protected void onProgressUpdate(Integer... values) {
        if (values != null && values.length == 2){
            progressBar.setMax(values[0]);
            progressBar.incrementProgressBy(values[1]);// 在原有的进度上增加多少
        }
    }

    @Override // 当有结果返回的时候调用
    protected void onPostExecute(Bitmap bitmap) {
        if (imageView != null && bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }
}
