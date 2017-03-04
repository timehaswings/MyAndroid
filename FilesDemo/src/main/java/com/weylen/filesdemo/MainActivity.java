package com.weylen.filesdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class MainActivity extends AppCompatActivity {

    // 2.Files 序列化保存 主要用于游戏数据保存。
    // 序列化的读写操作 ObjectOutputStream ObjectInputStream

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveDataClick(View view){
        try {
            // 打开的文件目录：data/data/packageName/files/xxx
            // 打开指定名字的文件 1.文件名 2.访问模式
            FileOutputStream fos = openFileOutput("game.data", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeInt(100);
            objectOutputStream.writeUTF("Hello 你好");
            AccountInfo info = new AccountInfo();
            info.setName("张三");
            info.setPwd("123456");
            objectOutputStream.writeObject(info);
            objectOutputStream.flush();
            objectOutputStream.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void redDataClick(View view){
        try {
            FileInputStream fis = openFileInput("game.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            // 读取数据和写入数据的顺序一致
            int num = ois.readInt();
            String str = ois.readUTF();
            AccountInfo accountInfo = (AccountInfo) ois.readObject();
            Log.d("zhou", "redDataClick: num:"+num+",str:"+str+",name:"+accountInfo.getName()
                +",pwd:"+accountInfo.getPwd());
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
