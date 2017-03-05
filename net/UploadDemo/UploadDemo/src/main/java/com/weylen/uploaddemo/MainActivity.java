package com.weylen.uploaddemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // SDCard根目录列表-->listFiles(名字，大小，图标)
    // 列表点击事件：目录-->内层列表

    private static final String ROOT = Environment.getExternalStorageDirectory().toString();
    private ListView listView;
    private FileAdapter fileAdapter;
    private TextView currentPathView;
    private String currentPath = ROOT; // 保存当前的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPathView = (TextView) findViewById(R.id.currentPath);

        listView = (ListView) findViewById(R.id.listView);
        fileAdapter = new FileAdapter(this, null);
        listView.setAdapter(fileAdapter);
        listView.setOnItemClickListener(itemClickListener);

        // 显示根目录
        listDir(new File(ROOT));
    }

    // 遍历文件夹
    private void listDir(File fileDir){
        if (!fileDir.isDirectory()){ // 如果传递的不是文件夹则直接返回
            return;
        }
        // 赋值
        currentPath = fileDir.getAbsolutePath();
        currentPathView.setText(currentPath); // 设置当前的路径

        File[] files = fileDir.listFiles();// 取得目录下面所有的文件列表对象
        List<FileInfo> data = new ArrayList<>(); // 数据对象
        if (files != null && files.length > 0){
            for (File file : files){ // 循环文件列表
                FileInfo fileInfo = new FileInfo();// 文件信息类
                fileInfo.setPath(file.getAbsolutePath()); // 设置路径
                fileInfo.setName(file.getName()); // 设置名字
                FileInfo.FileType fileType = FileUtil.getFileType(file);
                fileInfo.setType(fileType); // 设置类型
                fileInfo.setImgResId(FileUtil.getFileImgResId(fileType));
                fileInfo.setSize(FileUtil.getFileSize(file));
                data.add(fileInfo);
            }
        }
        fileAdapter.setData(data);
        fileAdapter.notifyDataSetChanged();
    }

    // 列表的点击事件
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 取得当前点击的这个item的数据
            FileInfo fileInfo = fileAdapter.getData().get(position);
            if (fileInfo.getType() == FileInfo.FileType.DIR){
                // 是文件夹
                listDir(new File(fileInfo.getPath()));
            }else{
                // 文件
                // TODO
                upload(fileInfo);
            }
        }
    };

    @Override
    public void onBackPressed() {
        // 如果当前路径等于根路径的话则退出 否则返回上一层
        if (ROOT.equals(currentPath)){
            super.onBackPressed();
        }else {
            listDir(new File(currentPath).getParentFile());
        }
    }

    private ProgressDialog progressDialog;

    /**
     * 上传操作
     * @param fileInfo
     */
    private void upload(FileInfo fileInfo){
        String url = "http://192.168.1.103:8080/webapps/upload.action";
        progressDialog = ProgressDialog.show(this, "", "上传中...");
        UploadUtil.upload(url, fileInfo, new UploadUtil.OnUploadCompleteListener() {
            @Override
            public void onUpload(String data, int status) {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                if (status == 1){
                    Toast.makeText(MainActivity.this, "上传成功-->"+data, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                }
            }
       });
    }
}
