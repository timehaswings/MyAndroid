package com.zyh.android.withthenotes.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class SimpleBaseAdapter<T> extends BaseAdapter{

	private List<T> data;
	private LayoutInflater mInflater;
	
	protected SimpleBaseAdapter(Context context,List<T> data){
		mInflater = LayoutInflater.from(context);
		this.data = data;
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}
	
	@Override
	public T getItem(int position) {
		return data.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	public void updateData(int position,T t){
		if(data == null || position < 0 || position >= data.size()){
			return;
		}
		data.set(position, t);
	}

	public void setData(List<T> newData){
		this.data = newData;
	}

	public void addData(T t){
		if(data == null){
			data = new ArrayList<T>();
		}
		data.add(t);
	}

	public void addData(List<T> newData){
		if(data == null){
			data = new ArrayList<T>();
		}
		data.addAll(newData);
	}

	public void removeData(int i){
		if(data != null){
			data.remove(i);
		}
	}

	public List<T> getData(){
		return data;
	}

	public LayoutInflater getInflater(){
		return mInflater;
	}
}
