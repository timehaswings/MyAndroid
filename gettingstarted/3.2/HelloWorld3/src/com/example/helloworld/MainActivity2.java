package com.example.helloworld;

import java.util.ArrayList;

import com.example.helloworld.R.color;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends Activity {
    private Intent intent;
	private ArrayList<String> list;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
			this.intent = getIntent();
	        this.list=new ArrayList<String>();
	        	list.add("张三");
			    list.add("李四");
			    list.add("王五");
	        this.layout=(LinearLayout)super.findViewById(R.id.layout_names);
	}
    public void showNames(View v){
    	   layout.removeAllViews();
    	  
    	   for(String name:list){
    		   TextView vT=new TextView(this);
    		   vT.setText(name);
    		   vT.setTextColor(Color.BLUE);  
    		   vT.setTextSize(30);
    		   layout.addView(vT);
    	   }
		 //intent.putStringArrayListExtra("list", list);
		 //intent.putExtra("test", "这是activity2中的测试数据");
		 //setResult(6, intent);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
