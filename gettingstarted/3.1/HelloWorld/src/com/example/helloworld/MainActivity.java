package com.example.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		Button bu= (Button)super.findViewById(R.id.button1);
		bu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent obj=new Intent();
				obj.setClass(MainActivity.this, MainActivity2.class);
				MainActivity.this.startActivity(obj);
				MainActivity.this.finish();
			}
		});
	}
   
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
