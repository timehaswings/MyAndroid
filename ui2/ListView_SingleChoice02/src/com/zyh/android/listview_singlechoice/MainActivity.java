package com.zyh.android.listview_singlechoice;

import java.util.ArrayList;
import java.util.List;

import com.zyh.android.listview_singlechoice.SingleChoiceAdapter.OnRadioButtonClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// 列表里面的单选操作
	private ListView listView;
	private SingleChoiceAdapter adapter;
	private TextView textView;
	private String source;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*************初始化数据***************/
		final List<String> data = new ArrayList<String>();
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
		// 传递接口对象
		adapter.setOnRadioButtonClickListener(listener3);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setChoiceId(position);
				// 取出item对应的数据
				String str = data.get(position);
				// 设置数据
				textView.setText(source+"："+str);
			}
		});
		
		textView = (TextView) findViewById(R.id.message);
		source = textView.getText().toString(); // 取出TextView默认显示的内容
	}
	
	public void updateText(String text){
		// 设置数据
		textView.setText(source+"："+text);
	}
	
	private OnRadioButtonClickListener listener = new OnRadioButtonClickListener() {
		@Override
		public void onRadioButtonClick(String str) {
			Log.d("zhou", "..onRadioButtonClick..str:"+str);
			// 设置数据
			textView.setText(source+"："+str);
		}
	};
	
	private OnRadioButtonClickListener listener2 = new OnRadioButtonClickListener() {
		@Override
		public void onRadioButtonClick(String str) {
			Log.d("zhou", "..onRadioButtonClick..str:"+str);
			// 设置数据
			textView.setText(source+"："+str);
		}
	};
	
	private OnRadioButtonClickListener listener3 = new OnRadioButtonClickListener() {
		@Override
		public void onRadioButtonClick(String str) {
			Log.d("zhou", "..onRadioButtonClick..str:"+str);
			// 设置数据
			textView.setText(source+"："+str);
		}
	};
}
