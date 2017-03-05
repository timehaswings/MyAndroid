package com.weylen.provincedemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhou on 2016/4/5.
 */
public class CityAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mInflater;
    private List<String> cityData; // 对应省的城市数据
    private List<List<String>> quData;

    public CityAdapter(Context context,List<String> cityData, List<List<String>> quData){
        this.quData = quData;
        this.cityData = cityData;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // 返回的是对应省的城市
    public int getGroupCount() {
        return cityData == null ? 0 : cityData.size();
    }

    @Override // 返回的是对应省的城市的市区
    public int getChildrenCount(int groupPosition) {
        return quData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void setCityData(List<String> cityData) {
        this.cityData = cityData;
    }

    public void setQuData(List<List<String>> quData) {
        this.quData = quData;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.city_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.cityName);
        textView.setText(cityData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.qu_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.quName);
        textView.setText(quData.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
