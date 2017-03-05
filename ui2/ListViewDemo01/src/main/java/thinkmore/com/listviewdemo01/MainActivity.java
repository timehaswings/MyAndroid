package thinkmore.com.listviewdemo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ListView 是属于ViewGroup的子类，但是它添加视图的方式不是通过addView
    // 而是通过setAdapter(设置适配器)
    // 所有的AdapterView都是通过setAdapter的方式设定视图

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 在本页面所加载的视图里面找
//        this.findViewById(R.id.listView);
//        // 在View所对应的页面里面找
//        listView.findViewById(R.id.listView);

        // 构建数据对象
        List<HashMap<String, String>> data = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            // 构建Map对象
            HashMap<String, String> map = new HashMap<>();
            // 1.key 2.value
            map.put("Name", "基础护理"+(i+1));
            // 添加数据
            data.add(map);
        }
        // 构建适配器对象
        /**
         * 1.Context对象 上下文对象， 在Activity可以用的Application Activity
         * 2.Data 数据 List<HashMap<String,String>>
         * 3.布局的id
         * 4.key集合 指的是匹配id集合的数据的key
         * 5.id集合 指的是需要动态设定内容的控件id集合
         */
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item_listview,
                new String[]{"Name"}, new int[]{R.id.text});
        listView = (ListView) findViewById(R.id.listView);
        // 设置适配器
        listView.setAdapter(simpleAdapter);
        // 获取子控件的个数
        int count = listView.getChildCount();
        Log.d("zhou", "onCreate: count:"+count+", width : " + listView.getWidth());

        // 设置滚动监听
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override // 1.当前滚动的列表视图对象 2.滚动状态：
            // AbsListView.OnScrollListener.SCROLL_STATE_IDLE 停下来的状态
            // AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL 触摸滚动
            // AbsListView.OnScrollListener.SCROLL_STATE_FLING 自动滚动
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    int count = listView.getChildCount();
                    // 获取适配器的item的个数
                    int adapterCount = simpleAdapter.getCount();
                    Log.d("zhou", "onScrollStateChanged: count:"+count+", adapterCount:"+adapterCount);
                }
            }

            @Override // 滚动时 1.当前的列表视图对象 2.第一个可见视图的下标 3.总共可见视图的个数 4.总共的item的个数
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("zhou", "onScroll: firstVisibleItem："+firstVisibleItem+",visibleItemCount:"+visibleItemCount
                +",totalItemCount:"+totalItemCount);
            }
        });
        // 设置列表视图里面的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override // 1.当前列表视图对象 2.点击的item对应的视图对象 3.item的下标 4.item的id 默认id和position一样
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("zhou", "onItemClick: 点击了：view:"+view+",position:"+position);
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setText("Hello 你好吗");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = listView.getChildCount();
        Log.d("zhou", "onResume: count:"+count+", width : " + listView.getWidth());
    }

    @Override // 窗口焦点发生变化
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int count = listView.getChildCount();
        Log.d("zhou", "onResume: count:"+count);
    }
}
