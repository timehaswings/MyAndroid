package com.zyh.android.expandablelistviewdemo;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter{

	private List<HashMap<String, String>> groupData;
	private List<List<HashMap<String, String>>> childData;
	private LayoutInflater mInflater;
	public MyAdapter(Context context, List<HashMap<String, String>> groupData, List<List<HashMap<String, String>>> childData) {
		this.mInflater = LayoutInflater.from(context);
		this.groupData = groupData;
		this.childData = childData;
	}
	
	@Override // 返回组的个数
	public int getGroupCount() {
		return groupData == null ? 0 : groupData.size();
	}

	@Override // 返回指定组对应的子列表数据
	public int getChildrenCount(int groupPosition) {
		return childData.get(groupPosition).size();
	}

	@Override// 组内容
	public Object getGroup(int groupPosition) {
		return groupData.get(groupPosition);
	}

	@Override // 子内容
	public Object getChild(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override // 组id
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override // 子id
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override // 同样的id是否引用同样的数据
	public boolean hasStableIds() {
		return false;
	}

	@Override // 组试图 1.组下标 2.这个组是否被打开
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.group_layout, parent, false);
		}
		// 找控件
		TextView textView = (TextView) convertView.findViewById(R.id.groupName);
		textView.setText(groupData.get(groupPosition).get("GroupName"));
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
		imageView.setImageResource(isExpanded ? R.drawable.list_icn_order_up : R.drawable.list_icn_order_down);
		return convertView;
	}

	@Override // 子试图
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.child_layout, parent, false);
		}
		// 找控件
		TextView textView = (TextView) convertView.findViewById(R.id.childName);
		textView.setText(childData.get(groupPosition).get(childPosition).get("ChildName"));
		TextView dataView = (TextView) convertView.findViewById(R.id.childData);
		dataView.setText(childData.get(groupPosition).get(childPosition).get("ChildData"));
		return convertView;
	}

	@Override // 子列表是否可以被选择
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
