package thinkmore.com.customadapter01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ****LayoutInflater 布局转换器(将layout转换(解析)成一个View对象，实际上应该算是layout布局的根对象)****
    // 获取方式：
    // 1.Activity.getLayoutInflater(),在Activity里面使用getLayoutInflater方法获取。
    // 2.Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) 获取系统服务
    // 3.LayoutInflater.from(Context); 通过LayoutInflater的静态方法获取，需要传递一个Context对象
    // 解析方法：
    // 1.inflater.inflate(LayoutResId, ViewGroup); 1.布局资源id 2.ViewGroup;
    //   解析后的对象是布局根标签对象，第二个参数就表示在解析之后得到的视图对象的外层是否需要再次包装一个ViewGroup对象
    //   当给定的ViewGroup对象不为null时，则会在解析后的视图再包装一个parent，而且，包装的parent的属性会影响解析后的view对象。
    //   ViewGroup为null ： 解析的视图大小由加载它的父控件来决定 (在找不到parent的使用)
    //   ViewGroup不为null : 则大小由ViewGroup来决定（基本不用）
    // 2.inflater.inflate(LayoutResId, ViewGroup, Boolean); 这里讨论的当ViewGroup不为null的时候，第三个参数就
    //   决定解析后的视图大小是否根据ViewGroup发生改变 true跟着改变 false不改变
    //  1.若为true: 则解析后的View的大小由ViewGroup决定 （基本不用）
    //  2.若为false: 则解析后的View大小由View的根标签决定 （最常用的）

    private View childView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//
//        relativeLayout = new RelativeLayout(this);
//        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams((int)(400 * displayMetrics.density),
//                (int)(400 * displayMetrics.density)));
//
//        LayoutInflater inflater = LayoutInflater.from(this);
////        childView = inflater.inflate(R.layout.item_list, relativeLayout);
//        childView = inflater.inflate(R.layout.item_list, relativeLayout, true);
//        Log.d("zhou", "onCreate: view:"+childView);
//
//
//        setContentView(childView); // 默认大小
//        setContentView(view, null); // 自己控制大小
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        // 初始化列表内容
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            data.add("数据内容--------------"+(i));
        }
        // 创建适配器对象
        final CustomAdapter customAdapter = new CustomAdapter(this, data);
        // 设定适配器
        listView.setAdapter(customAdapter);
        // item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setBackgroundResource(android.R.color.holo_blue_bright);
                customAdapter.addChooseId(position);
                customAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Log.d("zhou", "onWindowFocusChanged: rLayout:"+relativeLayout.getWidth()+",height:"+relativeLayout.getHeight());
//        Log.d("zhou", "onWindowFocusChanged: childView:"+childView.getWidth()+",height:"+childView.getHeight());
    }
}
