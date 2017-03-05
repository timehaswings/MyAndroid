package thinkmore.com.imageviewdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        // Drawable-->图片 获取方式--> getResource().getDrawable(R.mipmap.ic_launcher);
//        imageView.setImageDrawable();

        // 设置图片资源，资源可以是颜色 也可以是图片 R.mipmap.xxx R.drawable.xxx
        imageView.setImageResource(R.color.colorAccent);
        // 设置图片Bitmap 比较耗内存 基本上一个Bitmap对象是一个图片大小的4倍
        // OOM（Out Of Memory）
        // 获取Bitmap的方式：
        // 1.
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        // 转换成BitmapDrawable对象
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();// 获取Bitmap对象
        imageView.setImageBitmap(bitmap);
        // 2. 通过Bitmap解析器获取
        //  解析资源：1.资源包对象 2.图片的id
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageView.setImageBitmap(bitmap1);
        // 设置图片Uri Uri可以是网络图片也可以是本机的图片
    }

    // 按钮的点击事件
    public void getImageFromLocale(View view){
        // action data type
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");// 设置类型为图片
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK){
            Log.d("zhou", "onActivityResult: data:"+data);
            // 获取图片的Uri值
            Uri dataUri = data.getData();
            imageView.setImageURI(dataUri);
        }
    }
}
