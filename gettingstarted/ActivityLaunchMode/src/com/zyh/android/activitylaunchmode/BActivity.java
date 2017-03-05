package com.zyh.android.activitylaunchmode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		
		findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textViewClick(v);
			}
		});
	}
	
	public void textViewClick(View view){
		Intent intent = new Intent(this, CActivity.class);
		intent.putExtra("boo", true);
		startActivity(intent);
	}
}
