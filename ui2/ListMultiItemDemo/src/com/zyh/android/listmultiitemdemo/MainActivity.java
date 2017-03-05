package com.zyh.android.listmultiitemdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	// 多item操作
	// getCount getItem getItemId getView
	
	// 1.单选 2.多选 3.FooterView,HeaderView 4.多item操作 5.EmptyView
	
	private MultiItemAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new MultiItemAdapter(this, initData());
		listView.setAdapter(adapter);
//		listView.setSelection(position);// 设定选中哪一行
	}
	
	public static String getNowTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
	}
	
	private List<MessageEntity> initData(){
		List<MessageEntity> data = new ArrayList<MessageEntity>();
		for (int i = 0; i < 10; i++){
			MessageEntity messageEntity = new MessageEntity();
			messageEntity.setTime(getNowTime());// 设置当前时间
			messageEntity.setContent("Hello 你好吗?");
			messageEntity.setId(i); 
			messageEntity.setType(i % 2); // 设置类型
			data.add(messageEntity);
		}
		return data;
	}
}
