package com.weylen.listimgdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1.列表里面的图片如何进行加载

    // 先请求获取列表数据 name&imgPath;name&imgPath;
    // 文件管理器

    private GridView gridView;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new GridAdapter(this, initData());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DisplayImageActivity.class);
                String imgUrl = adapter.getData().get(position).getImgUrl();
                try {
                    String imgPath = ImageAsyncTask.defaultCache + File.separator + ImageAsyncTask.formatUrl(imgUrl);
                    intent.putExtra("ImgPath", imgPath);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private List<ItemInfo> initData(){
        final String IMG_ROOT_PATH = "http://192.168.1.101:8080/webapps/images/";
        List<ItemInfo> data = new ArrayList<>();
        for (int i = 0; i < 13; i++){
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.setName("张三--"+(i+1));
            itemInfo.setImgUrl(IMG_ROOT_PATH+"image%20("+(i+1)+").jpg");
            data.add(itemInfo);
        }
        return data;
    }
}
