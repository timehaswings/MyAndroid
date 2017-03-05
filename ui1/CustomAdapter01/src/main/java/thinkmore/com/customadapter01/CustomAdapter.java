package thinkmore.com.customadapter01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class CustomAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<String> data;
    private List<Integer> idsList;
    public CustomAdapter(Context context, List<String> data){
        mInflater = LayoutInflater.from(context);
        this.data = data;
        idsList = new ArrayList<>();
    }

    @Override // 返回的是Item的个数
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override // 返回指定下标item的内容
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override // 指定下标item的id值
    public long getItemId(int position) {
        return position;
    }

    /**
     * 将点击的item的下标封装在List里面
     * @param position
     */
    public void addChooseId(int position){
        // 先检查容器里面是否已经添加了这个下标
        boolean isContains = idsList.contains(position);
        if (!isContains){ // 不包含
            idsList.add(position);
        }
    }

    @Override // 指定下标item 的视图对象
    // 1.下标 2.是否可以重复使用的视图 3.ListView
    // 1.item的类型 如果是单item操作的 类型就不用考虑，因为类型只有一种
    // 2.convertView 是否为null convertView什么时候为null? 什么时候不为null?

    // 1.convertView重复使用。
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){ // 判断是否可以重复使用
            // 每次使用inflate方法都和new关键字一样，表示创建一个新的对象
            view = mInflater.inflate(R.layout.item_list, parent, false);
        }else{
            view = convertView;
        }
        // 将布局里面的视图找出来 从View所对应的布局里面找控件
        TextView textView = (TextView) view.findViewById(R.id.textView);
        // 取出对应下标的数据
        String str = data.get(position);
        // 设定显示的字符串内容
        textView.setText(str);

        // 判断此item的下标是否包含在点击的容器里面
        boolean isContains = idsList.contains(position);
        if (isContains){ // 则表示此条item被点击过
            view.setBackgroundResource(android.R.color.holo_blue_bright);
        }else {
            view.setBackgroundResource(android.R.color.transparent);
        }

        return view;
    }
}
