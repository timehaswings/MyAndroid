package com.zyh.android.listviewfooterview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater; // 解析布局 转换视图
	private List<MessageInfo> data; // 列表显示的数据
	public CustomAdapter(Context context, List<MessageInfo> data) {
		this.mInflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override // 返回item的个数
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 添加一整组数据
	 * @param newData
	 */
	public void addAll(List<MessageInfo> newData){
		if(this.data == null){
			data = new ArrayList<MessageInfo>();
		}
		data.addAll(newData);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_list, parent, false);
			holder.textView1 = (TextView) convertView.findViewById(R.id.text1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.text2);
			holder.textView3 = (TextView) convertView.findViewById(R.id.text3);
			holder.textView4 = (TextView) convertView.findViewById(R.id.text4);
			holder.textView5 = (TextView) convertView.findViewById(R.id.text5);
			holder.textView6 = (TextView) convertView.findViewById(R.id.text6);
			// 保存holder对象
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		// 设置显示的数据
		MessageInfo mi = data.get(position); // 取出item对应的数据对象
		holder.textView1.setText(mi.getPalyer()); // 选手
		holder.textView2.setText(mi.getEvent()); // 所属赛事
		holder.textView3.setText(mi.getCode()); // 股票代码
		holder.textView4.setText(mi.getName()); // 股票名称
		holder.textView5.setText(mi.getType()); // 交易类型
		holder.textView6.setText(String.valueOf(mi.getCount())); // 交易数量
		return convertView;
	}

	private class ViewHolder{
		private TextView textView1, textView2, textView3, textView4, textView5, textView6;
	}
}
