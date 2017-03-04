package com.zyh.android.withthenotes.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyh.android.withthenotes.R;
import com.zyh.android.withthenotes.adapter.SimpleBaseAdapter;

import java.util.List;

/**
 * Created by zhou on 2016/1/7.
 */
public class CustomListAdapter extends SimpleBaseAdapter<StringInfo> {

    public CustomListAdapter(Context context, List<StringInfo> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = getInflater().inflate(R.layout.list_dialog_item,parent,false);
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.checkbox);
        final StringInfo info = getData().get(position);
        textView.setText(info.getName()); // 设置文本
        return convertView;
    }
}
