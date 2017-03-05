package com.zyh.android.listviewchoicemode;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/24.
 */
public class CustomChoiceAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private String[] colors;
    private SparseBooleanArray status = new SparseBooleanArray();
    private boolean isCheckBoxVisible; // 记录CheckBox是否可见
    
    public CustomChoiceAdapter(Context context, String[] colors){
        mInflater = LayoutInflater.from(context);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public void updateTextColor(int position, boolean isChecked){
    	status.put(position, isChecked);
    	this.notifyDataSetChanged();
    }
    
    public void clearChoice(){
    	status.clear();
    	this.notifyDataSetChanged();
    }
    
    public void updateCheckBoxVisible(boolean isVisible){
    	this.isCheckBoxVisible = isVisible;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_list, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(colors[position]);
        textView.setTextColor(status.get(position) ? Color.BLUE : Color.GRAY);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.box);
        // 设置CheckBox的显示状态
        checkBox.setVisibility(isCheckBoxVisible ? View.VISIBLE : View.GONE);
        checkBox.setChecked(status.get(position));
        checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateTextColor(position, checkBox.isChecked());
			}
		});
        return convertView;
    }
}
