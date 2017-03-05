package com.weylen.listimgdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by zhou on 2016/4/28.
 */
public class BitmapUtil {

    /**
     *
     * @param filePath 图片的路径
     * @param width 图片控件所支持的最大宽度
     * @param height 图片控件所支持的最大高度
     * @return
     */
    public static Bitmap parse(String filePath, int width, int height){
        Log.d("zhou", "parse: width:"+width+",height:"+height);
        Log.d("zhou", "parse: 未解析时图片大小："+4196352);
        // 模糊缩放
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只解析图片的宽高属性 返回的Bitmap对象则为null
        options.inJustDecodeBounds = true;
        // 此时解析的Bitmap为null
        BitmapFactory.decodeFile(filePath, options);
        int widthRatio = options.outWidth / width; // 宽的倍数
        int heightRatio = options.outHeight / height; // 高的倍数
        int maxRatio = Math.max(widthRatio, heightRatio);
        if (maxRatio <= 0){
            maxRatio = 1;
        }
        options.inSampleSize = maxRatio;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null){
            return null;
        }
        Log.d("zhou", "parse: 第一次模糊缩放大小:"+bitmap.getByteCount());

        float scaleWidth = ((float)width) / options.outWidth;
        float scaleHeight = ((float)height) / options.outHeight;
        float scale = Math.min(scaleHeight, scaleWidth);

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale); // 设置缩放比例
        // 执行缩放操作
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        Log.d("zhou", "parse: 第二次精确缩放大小:"+bitmap.getByteCount());

//        ByteArrayOutputStream bs = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bs);

//        // Log.d("zhou", "parse: 压缩后："+bs.size());
//        byte[] bytes = bs.toByteArray();
//        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        Log.d("zhou", "parse: 压缩后的大小:"+bitmap.getByteCount());
        return bitmap;
    }
}
