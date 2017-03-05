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
public class ProvinceAdapter extends BaseExpandableListAdapter{

    private LayoutInflater mInflater;
    private List<String> provinceData;
    private List<List<List<String>>> quData;
    private List<List<String>> cityData;
    private Context context;

    public ProvinceAdapter(Context context, List<String> provinceData, List<List<String>> cityData, List<List<List<String>>> quData){
        this.mInflater = LayoutInflater.from(context);
        this.provinceData = provinceData;
        this.quData = quData;
        this.cityData = cityData;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return provinceData == null ? 0 : provinceData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.province_layout, parent, false);
        }
        TextView provinceNameView = (TextView) convertView.findViewById(R.id.provinceName);
        provinceNameView.setText(provinceData.get(groupPosition));

        return convertView;
    }

    @Override // 返回一个ExpandableListView对象
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.city_child_layout, parent, false);
        }
        MyExpandableListView listView = (MyExpandableListView) convertView.findViewById(R.id.cityList);
        CityAdapter cityAdapter = (CityAdapter) listView.getExpandableListAdapter(); // 获取列表对应的适配器
        if (cityAdapter == null){
            cityAdapter = new CityAdapter(context, cityData.get(groupPosition), quData.get(groupPosition));
        }else{
            cityAdapter.setCityData(cityData.get(groupPosition));
            cityAdapter.setQuData(quData.get(groupPosition));
        }
        listView.setAdapter(cityAdapter);
        return convertView;
    }

    @Override // 返回child是否可以被点击
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
