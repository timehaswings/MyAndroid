package com.android.administrator.simpleplayer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * 泛型的类型即表示数据的类型
 * @param <T>
 */
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
	
	/**
	 * 更新一条数据
	 */
	public void updateData(int position,T t){
		if(data == null || position < 0 || position >= data.size()){
			return;
		}
		data.set(position, t);
	}
	
	/**
	 * 重新设定数据
	 * @param newData
	 */
	public void setData(List<T> newData){
		this.data = newData;
	}
	
	/**
	 * 添加一条数据
	 * @param t
	 */
	public void addData(T t){
		if(data == null){
			data = new ArrayList<T>();
		}
		data.add(t);
	}
	
	/**
	 * 添加一组数据
	 * @param newData
	 */
	public void addData(List<T> newData){
		if(data == null){
			data = new ArrayList<T>();
		}
		data.addAll(newData);
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public List<T> getData(){
		return data;
	}
	
	/**
	 * 获取LayoutInflater对象
	 * @return
	 */
	public LayoutInflater getInflater(){
		return mInflater;
	}
}
