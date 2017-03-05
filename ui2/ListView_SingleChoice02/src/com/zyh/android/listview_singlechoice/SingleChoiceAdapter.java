package com.zyh.android.listview_singlechoice;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class SingleChoiceAdapter extends BaseAdapter{
	// 接口回调的操作步骤
	// 1.在触发事件的类里面写接口
	// 2.声明方法 方法里面一般以参数来进行数据的传递
	// 3.在需要接收此事件的类里面实现接口，并且传入接口对象
	
	public interface OnRadioButtonClickListener{
		void onRadioButtonClick(String str);
	}

	// 在设置之后，此对象指向的就是MainActivity里面的listener
	private OnRadioButtonClickListener onRadioButtonClickListener;
	private List<String> data;
	private LayoutInflater mInflater;
	private MainActivity context;
	private int choiceId = -100; // 记录选中的下标
	private int lastClickId = -1; // 上一次点击的下标
	
	// 用来保存每一个item对象的选中状态
	private HashMap<Integer, Boolean> status = new HashMap<Integer, Boolean>();
	
	
	public SingleChoiceAdapter(MainActivity context, List<String> data){
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	public void setOnRadioButtonClickListener(OnRadioButtonClickListener onRadioButtonClickListener) {
		this.onRadioButtonClickListener = onRadioButtonClickListener;
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
//		holder.rb.setChecked(position == choiceId);
		// Map里面key不存在时，返回的值是null
//		Boolean isChecked = status.get(position); // 取出在status保存的对应下标的状态
//		holder.rb.setChecked(isChecked == null ? false : isChecked);
		
		holder.rb.setChecked(choiceId == position);
		// 给RadioButton设置点击事件
		holder.rb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setChoiceId(position);
				if (onRadioButtonClickListener != null) {
					onRadioButtonClickListener.onRadioButtonClick(data.get(position));
				}
//				context.updateText(data.get(position));
				// 将上一次保存的状态改成false
//				if(lastClickId != -1){
//					status.put(lastClickId, false);
//				}
//				status.put(position, true);
//				lastClickId = position;
//				notifyDataSetChanged();
			}
		});
		
		// 选择改变监听
//		holder.rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				setChoiceId(position);
//				Log.d("zhou", "..onCheckedChanged..position:"+position);
//			}
//		});
		
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
