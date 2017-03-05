package com.weylen.popupmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // PopupMenu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.showPopupMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

    }

    private void showPopupMenu(View anchor){
        // 1.参数 上下文
        // 2.View anchor 表示的是一个固定物 固定此popup显示的位置
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        // 添加菜单
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, 100, Menu.NONE, "Window"); // 代码添加
        popupMenu.getMenuInflater().inflate(R.menu.main, menu); // xml解析添加
        // 菜单的点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu1:
                        Toast.makeText(MainActivity.this, "点击了菜单1", Toast.LENGTH_SHORT).show();
                        break;
                    case 100:
                        Toast.makeText(MainActivity.this, "点击了Window", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        // 显示
        popupMenu.show();
    }
}
