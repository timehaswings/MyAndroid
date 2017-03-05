package com.zyh.android.expandablelistviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;

public class MainActivity extends Activity {

	// 1.ExpandableListView 二级列表视图
	
	private ExpandableListView expandableListView;
	public static final String DATASTR1 = "This child is even";
	public static final String DATASTR2 = "This child is odd";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		expandableListView = (ExpandableListView) findViewById(R.id.listView);
		
		// 封装组数据
		List<HashMap<String, String>> groupData = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 20; i++){
			HashMap<String, String> groupMap = new HashMap<String, String>();
			groupMap.put("GroupName", "Group "+(i));
			groupData.add(groupMap);
		}
		// 封装子列表数据
		List<List<HashMap<String, String>>> childData = new ArrayList<List<HashMap<String,String>>>();
		int groupSize = groupData.size(); // 组的大小
		for (int i = 0; i < groupSize; i++){ // 每一个组对应一个List<Map>数据
			List<HashMap<String, String>> childListData = new ArrayList<HashMap<String,String>>();
			// 给每一个组添加子列表数据
			for (int j = 0; j < 15; j++){
				HashMap<String, String> childMap = new HashMap<String, String>();
				childMap.put("ChildName", "Child "+(0));
				childMap.put("ChildData", j % 2 == 0 ? DATASTR1 : DATASTR2);
				childListData.add(childMap);
			}
			childData.add(childListData); // 添加每一个组数据
		}
		
		// 1.Context 上下文
		// 2.List<? extends Map<String, ?>> groupData 组数据 size表示一共有多少组
		// 3.LayoutResId expandedGroupLayout 组被打开时显示的布局
		// 4.LayoutResId collapsedGroupLayout 组被关闭时显示的布局
		// 5.String[] groupFrom 要给组布局里面的控件设置数据的key集合
		// 6.int[] groupTo 组布局里面需要设置数据的控件的id集合
		// 7.List<List<? extends Map<String, ?>>>  childData.size表示总共有多少个组的子数据 第一个List的大小
		//   childData.get(groupPosition) 表示指定组对应的子列表数据
		// 8.LayoutResId childLayout 子布局
		// 9.String[] childFrom 要给子布局里面的控件设置数据的key集合
		// 10.int[] groupTo 子布局里面需要设置数据的控件的id集合
		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
				groupData, R.layout.group_layout, R.layout.group_layout, 
				new String[]{"GroupName"}, new int[]{R.id.groupName}, childData, 
				R.layout.child_layout, new String[]{"ChildName", "ChildData"}, new int[]{R.id.childName, R.id.childData});
		
		MyAdapter myAdapter = new MyAdapter(this, groupData, childData);
		expandableListView.setAdapter(myAdapter);
		expandableListView.setGroupIndicator(null);
		
		// 设置组的关闭监听
		expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				Log.d("zhou", "..onGroupCollapse..被关闭的组的下标："+groupPosition);
			}
		});
		
		// 设置组的打开监听
		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Log.d("zhou", "..onGroupExpand..组被打开的下标:"+groupPosition);
			}
		});
		
		// 组的点击监听
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override // 返回true表示拦截事件， 父类的事件就接收不了
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				Log.d("zhou", "..onGroupClick..组呗点击的下标："+groupPosition);
				return false;
			}
		});
		
		// 子列表被点击监听
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				Log.d("zhou", "..onChildClick..子列表被点击：所属的组："+groupPosition+",第几个："+childPosition);
				return false;
			}
		});
	}
}
