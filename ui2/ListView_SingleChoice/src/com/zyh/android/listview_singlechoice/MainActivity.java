package com.zyh.android.listview_singlechoice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	// 列表里面的单选操作
	private ListView listView;
	private SingleChoiceAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*************初始化数据***************/
		List<String> data = new ArrayList<String>();
		data.add("红色");
		data.add("橙色");
		data.add("黄色");
		data.add("绿色");
		data.add("蓝色");
		data.add("青色");
		data.add("紫色");
		data.add("红色");
		data.add("橙色");
		data.add("黄色");
		data.add("绿色");
		data.add("蓝色");
		data.add("青色");
		data.add("紫色");
		data.add("红色");
		data.add("橙色");
		data.add("黄色");
		data.add("绿色");
		data.add("蓝色");
		data.add("青色");
		data.add("紫色");
		
		listView = (ListView) findViewById(R.id.listView);
		adapter = new SingleChoiceAdapter(this, data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setChoiceId(position);
			}
		});
	}
}
