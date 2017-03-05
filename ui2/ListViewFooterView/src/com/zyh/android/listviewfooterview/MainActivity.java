package com.zyh.android.listviewfooterview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// PullToRefresh组建
	
	private ListView listView;
	private CustomAdapter adapter;
	private TextView loadmoreView;
	private View loadingLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 找到ListView控件
		listView = (ListView) findViewById(R.id.listView);
		// LayoutInflater
		View headerView = LayoutInflater.from(this).inflate(R.layout.item_list, listView, false);
		listView.addHeaderView(headerView);
		// 找到FooterView
		View footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, listView, false);
		loadmoreView = (TextView) footerView.findViewById(R.id.loadMoreText);
		loadmoreView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadingLayout.setVisibility(View.VISIBLE);
				loadmoreView.setVisibility(View.GONE);
				new UpdateThread().start();
			}
		});
		loadingLayout = footerView.findViewById(R.id.loadinglayout);
		listView.addFooterView(footerView);
		
		adapter = new CustomAdapter(this, initData());
		listView.setAdapter(adapter);
	}
	
	private class UpdateThread extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// View.post Activity.runOnUiThread
			runOnUiThread(runnable);
		}
	}
	
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			List<MessageInfo> newData = initData();
			adapter.addAll(newData);
			loadingLayout.setVisibility(View.GONE);
			loadmoreView.setVisibility(View.VISIBLE);
		}
	};
	
	private List<MessageInfo> initData(){
		List<MessageInfo> data = new ArrayList<MessageInfo>();
		for (int i = 0; i < 20; i++){
			MessageInfo mi = new MessageInfo();
			mi.setPalyer("刘备");
			mi.setEvent("王者归来之雄霸天下");
			mi.setCode("600528");
			mi.setName("中铁二局");
			mi.setType(i % 2 == 0 ? "买入" : "卖出");
			mi.setCount(1000);
			data.add(mi);
		}
		return data;
	}
}
