package thinkmore.com.listviewdemo02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // ListView
    // SimpleAdapter--> BaseAdapter

    // 适配器里面几个重要的方法：
    // 1.getCount(); 返回item的个数
    // 2.getItem(); 返回指定下标对应的内容对象 内容对象时一个Object对象，所以可以随意返回。
    // 3.getItemId(); 返回指定下标Item对应的id值，一般返回position即可。
    // 4.geView(); 返回指定下标item的视图对象。

    // 列表在滚动的时候都会重复调用getView方法。

    private ListView listView;
    private List<HashMap<String, String>> data;

    public static final int UPDATE_TEXT_CODE  = 100;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 构建数据对象
        data = new ArrayList<>();
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
        simpleAdapter = new SimpleAdapter(this, data, R.layout.item_listview,
                new String[]{"Name"}, new int[]{R.id.text});
//        simpleAdapter.notifyDataSetInvalidated(); // 刷新所有的
        listView = (ListView) findViewById(R.id.listView);
        // 设置适配器
        listView.setAdapter(simpleAdapter);

        // 设置列表视图里面的item点击事件
        // 匿名内部类使用一个局部变量为什么需要声明成final
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override // 1.当前列表视图对象 2.点击的item对应的视图对象 3.item的下标 4.item的id 默认id和position一样
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = (TextView) view.findViewById(R.id.text);
//                textView.setText("Hello 你好吗");
//                Map map = data.get(position); // 取出下标对应的Map对象
//                map.put("Name", "hello 你好吗"); // 重新封装数据
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                // 取出item对应的字符串数据
                String str = data.get(position).get("Name");
                // 封装数据
                // 1.封装字符串
                intent.putExtra("Data", str);
                // 2.封装下标
                intent.putExtra("Position", position);
                // 跳转
                startActivityForResult(intent, UPDATE_TEXT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && requestCode == UPDATE_TEXT_CODE){
            // 取得返回的数据和下标
            String str = intent.getStringExtra("Data");
            int position = intent.getIntExtra("Position", -1);
            if (position != -1){
                Map map = this.data.get(position); // 取出下标对应的Map对象
                map.put("Name", str); // 重新封装数据
                simpleAdapter.notifyDataSetChanged(); // 刷新界面，表示重新调用getView方法 刷新改变的
            }
        }
    }
}
