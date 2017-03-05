package com.example.helloworld;

import android.net.Uri;
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
		//savedInstanceState.putCharSequence("test", "传递一个试数据");
		
		bu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setAction("android.intent.action.activity");
				MainActivity.this.startActivityForResult(intent, 1);
				//MainActivity.this.finish();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent outputIntent) {
		//System.out.println("requestCode:"+requestCode+"---resultCode:"+resultCode);    
		   // Bundle extra = outputIntent.getExtras();
		   // String tt = extra.getString("test");
		  //  System.out.println(tt);
	}

	
   public void openDial(View v){
	     Intent obj=new Intent();
	     obj.setAction(Intent.ACTION_CALL);
	     obj.setData(Uri.parse("tel:13985538745"));
	     this.startActivity(obj);
   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
