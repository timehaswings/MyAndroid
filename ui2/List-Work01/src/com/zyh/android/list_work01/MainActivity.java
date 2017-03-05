package com.zyh.android.list_work01;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView listView;
	private MessageAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		adapter = new MessageAdapter(this, getData());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setRead(position, true);
			}
		});
	}
	 
	private List<MessageBean> getData(){
		List<MessageBean> data = new ArrayList<MessageBean>();
		for (int i = 0; i < 10; i++) {
			MessageBean mb = new MessageBean();
			mb.setTitle("招商银行一卡通卡户通知");
			mb.setTime("2015-12-20");
			mb.setContent("这样我们就获取一个到/test.do地址的HTTP连接了,我们打印con的class后发现其实是:sun.net.www.protocol.http.HttpURLConnection这个类,我们在写大数据流到服务器上时就会发现总是会出现OutOfMemoryError的错误,当然不同的机器上出现错误所对应的文件输出流的大小是不一样的.这个主要就是取决于本机的JVM的内存空间的大小了.");
			mb.setRead(false);
			mb.setSender("招商银行");
			mb.setReceiver("张三");
			mb.setId(i);
			data.add(mb);
		}
		return data;
	}
}
