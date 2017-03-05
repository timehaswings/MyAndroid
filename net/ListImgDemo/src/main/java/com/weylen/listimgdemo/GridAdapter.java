package com.weylen.listimgdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhou on 2016/4/29.
 */
public class GridAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<ItemInfo> data;
    public GridAdapter(Context context, List<ItemInfo> data){
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

    public List<ItemInfo> getData() {
        return data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item, parent, false);
        }
        // 找控件
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textView = (TextView) convertView.findViewById(R.id.name);

        ItemInfo itemInfo = data.get(position);
        // 设定内容
        textView.setText(itemInfo.getName());
        new ImageAsyncTask(imageView).execute(itemInfo.getImgUrl());
        return convertView;
    }
}
