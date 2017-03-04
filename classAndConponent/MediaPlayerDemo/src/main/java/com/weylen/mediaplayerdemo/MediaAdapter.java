package com.weylen.mediaplayerdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhou on 2016/4/22.
 */
public class MediaAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<MediaBean> data;

    public MediaAdapter(Context context, List<MediaBean> data){
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

    public void setData(List<MediaBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<MediaBean> getData() {
        return data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.media_item, parent, false);
        }
        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        TextView artistView = (TextView) convertView.findViewById(R.id.artist);
        TextView timeView = (TextView) convertView.findViewById(R.id.time);

        // 设置内容
        MediaBean mediaBean = data.get(position);
        titleView.setText(mediaBean.getTitle());
        artistView.setText(mediaBean.getArtist());
        timeView.setText(mediaBean.getDuration());
        return convertView;
    }
}
