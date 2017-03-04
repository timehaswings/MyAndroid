package com.weylen.filemanagerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhou on 2016/5/3.
 */
public class FileAdapter extends BaseAdapter{

    private List<FileInfo> data;
    private LayoutInflater mInflater;
    public FileAdapter(Context context, List<FileInfo> data){
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

    public List<FileInfo> getData() {
        return data;
    }

    public void setData(List<FileInfo> data) {
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.file_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.fileImg);
            holder.nameView = (TextView) convertView.findViewById(R.id.fileName);
            holder.sizeView = (TextView) convertView.findViewById(R.id.fileSize);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 得到此条item的数据对象
        FileInfo fileInfo = data.get(position);
        // 设置数据
        holder.imageView.setImageResource(fileInfo.getImgResId());
        holder.sizeView.setText(fileInfo.getSize());
        holder.nameView.setText(fileInfo.getName());

        return convertView;
    }

    private class ViewHolder{
        private ImageView imageView;
        private TextView nameView;
        private TextView sizeView;
    }
}
