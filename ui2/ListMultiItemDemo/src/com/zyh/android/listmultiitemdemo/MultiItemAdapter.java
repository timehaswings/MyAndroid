package com.zyh.android.listmultiitemdemo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MultiItemAdapter extends BaseAdapter{

	// item的类型值必须是小于item的类型总数
	public static final int TYPE_COUNT = 2;
	public static final int TYPE_LEFT = 0;
	public static final int TYPE_RIGHT = 1;
	
	private LayoutInflater mInflater;
	private List<MessageEntity> data;
	public MultiItemAdapter(Context context, List<MessageEntity> data){
		this.mInflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public MessageEntity getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override // 2.convertView表示可以重复使用的视图对象。
	// 重复使用的条件是：1. convertView != null 2.item的类型一致
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1.需要先获取此item的类型
		int type = getItemViewType(position);
		if(convertView == null){
			switch (type) { // 根据类型转换指定的视图
			case TYPE_LEFT:
				convertView = mInflater.inflate(R.layout.item_left, parent, false);
				break;
			case TYPE_RIGHT:
				convertView = mInflater.inflate(R.layout.item_right, parent, false);
				break;
			}
		}
		
		switch (type) {
		case TYPE_LEFT:
			TextView descView = (TextView) convertView.findViewById(R.id.desc);
			descView.setText("测试描述内容");
			break;
		}
		
		ImageView headImgView = (ImageView) convertView.findViewById(R.id.headImg);
		TextView timeView = (TextView) convertView.findViewById(R.id.time);
		TextView contentView = (TextView) convertView.findViewById(R.id.content);
		
		// 设定内容
		MessageEntity messageEntity = getItem(position); // 获取指定下标对应的数据对象
		timeView.setText(messageEntity.getTime());
		contentView.setText(messageEntity.getContent());
		
		return convertView;
	}
	
	@Override // 返回指定下标item的类型
	public int getItemViewType(int position) {
		return getItem(position).getType();
	}

	// 返回item的种类的个数
	public int getViewTypeCount() {
        return TYPE_COUNT;
    }
}
