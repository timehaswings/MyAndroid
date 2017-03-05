package com.zyh.android.listviewchoicemode;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/3/24.
 */
public class CustomChoiceAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private String[] colors;
    private SparseBooleanArray status = new SparseBooleanArray();
    
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_list, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(colors[position]);
        textView.setTextColor(status.get(position) ? Color.BLUE : Color.GRAY);
        return convertView;
    }
}
