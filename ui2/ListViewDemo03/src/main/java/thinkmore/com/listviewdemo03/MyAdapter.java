package thinkmore.com.listviewdemo03;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MyAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<String> data;
    public MyAdapter(Context context, List<String> data){
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){ // 检查convertView是否可以重复使用。 是根据item的类型和convertView是否为null判断是否可以重复使用
            convertView = mInflater.inflate(R.layout.item_list, parent, false);
        }
        // 找到Item视图里面的文本控件
        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(data.get(position));
        // 按钮
        convertView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zhou", "onClick: 监听事件。。。。");
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
