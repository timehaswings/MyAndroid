package com.zyh.android.vieweventsdemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnTouchListener{

	// Collection-->List Set
	
	// View里面的事件
	// 点击事件(onClick) 长按事件(longClick) 触摸事件(onTouch)
	// 给一个视图设置监听
	// 1.找对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		view.findViewById(id);// 在View所对应的视图布局里面去找指定id的控件
		View view = this.findViewById(R.id.button); // 在页面所加载的布局里面去找指定的id控件
		Button button = (Button)view;// 强制转换 向下转型
//		TextView textView = (TextView)view;
		
		// coll-->ArrayList@368462
//		Collection<String> coll = new ArrayList<String>();
//		List<String> list = (List<String>) coll;
		// 设置监听方式无非就是调用里面的set方法
		// OnTouchListener一个接口
		
		// 1.通过匿名内部类的方式
//		button.setOnTouchListener(new View.OnTouchListener() {			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				return false;
//			}
//		});
		
		// 2.通过类的方式实现
//		button.setOnTouchListener(new MyTouch());
//		// 3.通过内名内部类实现 并且以属性接受它
		button.setOnTouchListener(touchListener);
//		// 4.本类实现
//		button.setOnTouchListener(this);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("点击事件");
			}
		});
		
		button.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				System.out.println("长按事件");
				// 返回true表示拦截此监听，拦截之后点击事件不会触发
				return true;
			}
		});
	}
	
	private View.OnTouchListener touchListener = new View.OnTouchListener() {			
		@Override // 在视图出发触摸事件响应 1.当前事件的主体对象(视图对象) 2.事件对象，主要提供当前事件动作和坐标等参数、
		// 坐标是相对值，相对于视图对象来说
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();// 获取事件类型
			float x = event.getX();
			float y = event.getY();// 获取当前的坐标(相对于视图)
			switch (action) {
			case MotionEvent.ACTION_DOWN:// 按下事件
				System.out.println("按下....................x:"+x+",y:"+y);
				break;
			case MotionEvent.ACTION_MOVE:// 移动事件
				System.out.println("移动....................x:"+x+",y:"+y);
				break;
			case MotionEvent.ACTION_UP:// 松开事件
				System.out.println("松开....................x:"+x+",y:"+y);
				break;
			}
			// 返回值表示是否拦截此监听，true表示拦截，拦截之后，点击和长按事件都不能触发
			return false;
		}
	};
	
	private class MyTouch implements View.OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return false;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("整个页面的触摸事件");
		return true;
	}
}
