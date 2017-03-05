package com.zyh.android.jisuanqi;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private int width = 0;
	public static final String[][] BUTTON_NAMES = {{"7","8","9","/"},{"4","5","6","*"},{"1","2","3","-"},{".","0","=","+"}};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 
		// 构建一个空的point对象
		Point point = new Point();
		Log.d("zhou", "..onCreate.读取前.point.x:"+point.x+", point.y:"+point.y);
		// getSize获取屏幕的大小，这里的大小指的是屏幕的分辨率
		getWindowManager().getDefaultDisplay().getSize(point);
		Log.d("zhou", "..onCreate.读取后.point.x:"+point.x+", point.y:"+point.y);
		width = point.x / 4;
		Log.d("zhou", "..onCreate..1/4的宽："+width);
		// 找到容器
		TableLayout parentView = (TableLayout) findViewById(R.id.container);
		// 4行
		for(int i = 0; i < 4; i++){
			// 每一行要创建一个TableRow
			TableRow tableRow = new TableRow(this);
			// 4列
			for(int j = 0; j < 4; j++){
				// 每一列创建一个按钮
				TextView button = createButton(10*(i+1)+j, BUTTON_NAMES[i][j]);
				// 将按钮添加到tableRow里面
				tableRow.addView(button);
			}
			parentView.addView(tableRow);
		}
		// 找到删除按钮
		TextView deleteView = (TextView) findViewById(R.id.deleteButton);
		deleteView.setWidth(width - 7);
		
	}
	
	private TextView createButton(int id, String text){
		TextView button = new TextView(this);
		// 设置的布局参数
		TableRow.LayoutParams params = new TableRow.LayoutParams(width, width);
		button.setLayoutParams(params);
		params.leftMargin = 1;
		params.rightMargin = 1;
		params.topMargin = 1;
		params.bottomMargin = 1;
		// 设置文本
		button.setText(text);
		// 设置id
		button.setId(id);
		// 设置监听
		button.setOnClickListener(buttonClick);
		// 设置文本大小
		button.setTextSize(30);
		// 设置背景
		button.setBackgroundResource(android.R.color.darker_gray);
		// 设置重心
		button.setGravity(Gravity.CENTER);
		
		return button;
	}
	
	private View.OnClickListener buttonClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case 10: // 
				
				break;

			default:
				break;
			}
		}
	};
}
