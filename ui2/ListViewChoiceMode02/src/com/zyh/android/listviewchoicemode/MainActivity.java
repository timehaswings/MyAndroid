package com.zyh.android.listviewchoicemode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // EmptyView 绌鸿鍥� 鍦ㄥ垪琛ㄥ唴瀹逛负绌虹殑鏃跺�欐樉绀猴紝涓�鑸敤浜庢彁绀虹敤鎴�

    // 1.选择模式
    // ListView.CHOICE_SINGLE 单选
    // ListView.CHOICE_MODE_MULTIPLE 多选 会同时响应item的点击事件
    // ListView.CHOICE_MODE_MULTIPLE_MODAL 多选 把单选和点击事件分开。但是必须要设置多选的监听setMultiChoiceModeListener

    private CustomChoiceAdapter adapter;
    private ListView listView;
    private ActionMode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        View emptyView = findViewById(R.id.EmptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "鐐瑰嚮浜嗙┖瑙嗗浘", Toast.LENGTH_SHORT).show();
                String colors[] = getResources().getStringArray(R.array.Colors);
                adapter = new CustomChoiceAdapter(MainActivity.this, colors);
                listView.setAdapter(adapter);
            }
        });
        listView.setEmptyView(emptyView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.getCheckedItemCount();// 获取选中的item的个数
//        listView.getCheckedItemIds(); // 获取选中item的id集合
//        listView.getCheckedItemPositions();// 获取选中item的下标集合
//        // 1.下标 2.boolean是否被选中
        
        // 多选模式的监听
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override // item的选中状态发生改变
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            	Log.d("zhou", "..onItemCheckedStateChanged..item的选中状态改变");
            	mode.setTitle("当前选中："+listView.getCheckedItemCount()+"个");
            	adapter.updateTextColor(position, checked);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            	// 在ActionMode上面添加了一个菜单
            	// 1.组id 2.itemId 3.排序 4.菜单的标题
            	menu.add(Menu.NONE, 200, Menu.NONE, "取消");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            	MainActivity.this.mode = mode;
                return true;
            }

            @Override // ActionMode上面的菜单点击的时候响应 2.MenuItem就是点击的菜单对象
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            	if (item.getItemId() == 200){
            		// 退出多选模式
            		// 先清除所有的选中状态
            		listView.clearChoices();
            		adapter.clearChoice();
            		mode.finish();
            	}
                return false;
            }

            @Override // 销毁的时候调用
            public void onDestroyActionMode(ActionMode mode) {
            	Log.d("zhou", "..onDestroyActionMode..销毁ActionMode");
            	adapter.updateCheckBoxVisible(false);
            	adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	Log.d("zhou", "..onItemClick..点击了:"+position);
            }
        });
        
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("zhou", "..onItemLongClick..");
//				listView.setItemChecked(1, true);// 设置指定下标的item的选中状态
//		        listView.clearChoices(); 
				return false;
			}
		});
    }
    
    /**
     * 加入购物车按钮的点击事件
     * @param view
     */
    public void addShoppingCarClick(View view){
    	listView.setItemChecked(0, true);// 设置指定下标的item的选中状态
        listView.clearChoices(); // 清除选中状态
        if(mode != null){
        	mode.setTitle("当前选中：0个");
        	adapter.updateCheckBoxVisible(true);
        	adapter.updateTextColor(0, false);
        }
    }
}
