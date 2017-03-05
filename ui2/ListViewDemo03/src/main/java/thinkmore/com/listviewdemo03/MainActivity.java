package thinkmore.com.listviewdemo03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ListView SimpleAdapter(1.Context 2.Data List<Map> 3.LayoutResId 4.显示数据的key集合 String[] 5. 显示控件的id集合 int[])
    // 在ListView里面如果放置一些比较容易抢焦点的控件(EditText, Button, ImageButton, CheckBox, RadioButton...)，那么列表的item事件就不容易相应
    // 解决办法就是在控件里面加上android:focusable="false"，但是不能用于EditText,如果不行再加上android:focusableInTouchMode="false"
    /**
     * 列表视图
     */
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] data = new String[]{"红色","橙色", "黄色", "绿色", "蓝色", "青色", "紫色",
                "红色","橙色", "黄色", "绿色", "蓝色", "青色", "紫色",
                "红色","橙色", "黄色", "绿色", "蓝色", "青色", "紫色"};
        // 找控件
        listView = (ListView) findViewById(R.id.listView);
        final List<String> dataList = toList(data);
        final MyArrayAdapter<String> arrayAdapter = new MyArrayAdapter<>(this,
                R.layout.item_list, R.id.text1, toList(data));
        arrayAdapter.setOnViewClickListener(new MyArrayAdapter.OnViewClickListener() {
            @Override
            public void onButtonClick(int position) {
                String old = dataList.remove(position);
                arrayAdapter.remove(old);
                Log.d("zhou", "onButtonClick: 。。。。。");
            }
        });


        MyAdapter myAdapter = new MyAdapter(this, toList(data));
        listView.setAdapter(arrayAdapter);

        // 设置列表的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("zhou", "onItemClick: position:"+position);
            }
        });

        // listView设置滚动监听
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int childCount = view.getChildCount();
//                for (int i = 0; i < childCount; i++){
//                    View childView = view.getChildAt(i);
//                    childView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.d("zhou", "onClick: 按钮的点击");
//                        }
//                    });
//                }
            }
        });
    }

    private List<String> toList(String[] dataArray){
        List<String> data = new ArrayList<>();
        if (dataArray != null){
            int size = dataArray.length;
            data = new ArrayList<>();
            for (int i = 0; i < size; i++){
                data.add(dataArray[i]);
            }
        }
        return data;
    }
}
