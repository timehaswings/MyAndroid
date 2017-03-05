package com.weylen.menudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // menu 菜单 菜单的创建是由系统完成的。只需要重写方法即可。
    // OptionsMenu SubMenu ContextMenu

    // ContextMenu 需要进行注册才能够使用

    // PopupMenu 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        registerForContextMenu(button);// 给某一个视图注册长按事件
//        unregisterForContextMenu(button); // 解除长按事件的注册
    }

    @Override // 创建选项菜单 1、Menu菜单对象
    public boolean onCreateOptionsMenu(Menu menu) {
        // 1.groupId 菜单所属组的id 2.itemId 菜单选项的id 是唯一值，用来辨别是哪个选项 3.order 排序
        // 从小到大 小值在前 如果一样 则按照添加顺序显示 4.菜单的标题 字符串或者字符串资源id
        MenuItem menuItem = menu.add(Menu.NONE, 100, Menu.NONE, "Window"); // 返回的MenuItem
        // 通过xml的方式进行添加
        // 1.菜单的资源id 2.菜单对象
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem buildItem = menu.findItem(R.id.build);
        buildItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                buildItem.setChecked(item.isChecked());
                return true;
            }
        });

        // 先添加一个选项菜单
        SubMenu subMenu = menu.addSubMenu(Menu.NONE, 200, Menu.NONE, "Action");
        subMenu.add(Menu.NONE, 201, Menu.NONE, "关机");
        subMenu.add(Menu.NONE, 202, Menu.NONE, "注销");
        subMenu.add(Menu.NONE, 203, Menu.NONE, "重启");

        return super.onCreateOptionsMenu(menu);
    }

    @Override // 监听选项菜单的点击事件
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // 获取菜单选项的id值
        switch (id){
            case 100:
                Toast.makeText(MainActivity.this, "点击了window", Toast.LENGTH_SHORT).show();
                break;
            case R.id.build:
                item.setChecked(item.isChecked());
                break;
            case R.id.Analyze:
                item.setChecked(item.isChecked());
                break;
            case 201:
                Toast.makeText(MainActivity.this, "点击了关机", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override // 创建长按菜单
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override // 长按菜单的点击事件
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId(); // 获取菜单选项的id值
        switch (id){
            case R.id.build:
                Toast.makeText(MainActivity.this, "点击了长按菜单里面的Build", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
