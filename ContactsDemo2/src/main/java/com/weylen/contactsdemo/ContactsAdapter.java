package com.weylen.contactsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhou on 2016/4/15.
 */
public class ContactsAdapter extends BaseAdapter{

    private List<ContactsBean> data;
    private LayoutInflater mInflater;

    public ContactsAdapter(Context context, List<ContactsBean> data){
        this.data = data;
        mInflater = LayoutInflater.from(context);
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
     * 用来更新列表数据
     * @param newData
     */
    public void updateData(List<ContactsBean> newData){
        this.data = newData;
        this.notifyDataSetChanged();
    }

    public List<ContactsBean> getData(){
        return data;
    }

    public void deleteData(int position){
        if (data != null && position >= 0 && position < getCount()){
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新某一条数据
     * @param contactsBean
     * @param position
     */
    public void updateData(ContactsBean contactsBean, int position){
        this.data.set(position, contactsBean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.contacts_item, parent, false);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView phoneView = (TextView) convertView.findViewById(R.id.phone);
        // 获取item对应的数据对象
        ContactsBean contactsBean = data.get(position);
        nameView.setText(contactsBean.getName());
        phoneView.setText(contactsBean.getPhone());

        return convertView;
    }
}
