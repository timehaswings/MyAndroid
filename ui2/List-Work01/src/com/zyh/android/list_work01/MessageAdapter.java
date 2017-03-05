package com.zyh.android.list_work01;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private List<MessageBean> data;
	private int black, gray;
	private Drawable newMesasgeIcon;
	public MessageAdapter(Context context, List<MessageBean> data) {
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
		black = context.getResources().getColor(R.color.black);
		gray = context.getResources().getColor(R.color.gray);
		newMesasgeIcon = context.getResources().getDrawable(R.drawable.xxzx_new);
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
	
	/**
	 * 修改指定下标的已读状态
	 * @param position 下标
	 * @param isRead 状态 true表示已读
	 */
	public void setRead(int position, boolean isRead){
		data.get(position).setRead(isRead);
		notifyDataSetChanged();
	}

	@Override // 时间换空间 牺牲效率换取内存
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = mInflater.inflate(R.layout.item_message, parent, false);
		}
		// 找出控件
		TextView titleView = (TextView) convertView.findViewById(R.id.title);
		TextView timeView = (TextView) convertView.findViewById(R.id.time);
		TextView contentView = (TextView) convertView.findViewById(R.id.content);
		// 设置数据
		// 拿到item对应的数据对象
		MessageBean mb = data.get(position);
		titleView.setText(mb.getTitle());
		timeView.setText(mb.getTime());
		contentView.setText(mb.getContent());
		
		boolean isRead = mb.isRead();
		if (isRead) {
			titleView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			titleView.setTextColor(gray);
		}else {
			titleView.setCompoundDrawablesWithIntrinsicBounds(null, null, newMesasgeIcon, null);
			titleView.setTextColor(black);
		}
		
		return convertView;
	}

}
