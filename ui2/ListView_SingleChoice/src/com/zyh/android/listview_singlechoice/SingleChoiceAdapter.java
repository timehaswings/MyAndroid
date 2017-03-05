package com.zyh.android.listview_singlechoice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class SingleChoiceAdapter extends BaseAdapter{

	private List<String> data;
	private LayoutInflater mInflater;
	private int choiceId = -100; // 记录选中的下标
	public SingleChoiceAdapter(Context context, List<String> data){
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
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
	
	public int getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(int choiceId) {
		// 两次点击的是同一个下标 则不操作
		if (this.choiceId == choiceId){
			return;
		}
		this.choiceId = choiceId;
		this.notifyDataSetChanged();
	}

	@Override // 空间换时间 牺牲内存换取效率
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){ // 表示convertView有多少个新对象，那么ViewHolder就有多少个新对象
			holder = new ViewHolder();
			// 构建新的convertView对象
			convertView = mInflater.inflate(R.layout.item_singlechoice, parent, false);
			// 找控件
			holder.textView = (TextView) convertView.findViewById(R.id.text1);
			holder.rb = (RadioButton) convertView.findViewById(R.id.radioButton);
			// 保存holder对象
			convertView.setTag(holder);
		}else {
			// 取出convertView里面保存的tag属性值
			holder = (ViewHolder)convertView.getTag();
		}
		
		// 设置数据
		holder.textView.setText(data.get(position));
		// 设置RadioButton的选中状态
		// 如果下标等于选中的下标则设置选中状态
		holder.rb.setChecked(position == choiceId);
		// 给RadioButton设置点击事件
		holder.rb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setChoiceId(position);
			}
		});
		
		return convertView;
	}

	private class ViewHolder{
		private TextView textView;
		private RadioButton rb;
//		private Object tag;
//		
//		public Object getTag() {
//			return tag;
//		}
//		public void setTag(Object tag) {
//			this.tag = tag;
//		}
	}
}
