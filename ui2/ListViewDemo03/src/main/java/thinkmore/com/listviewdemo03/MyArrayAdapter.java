package thinkmore.com.listviewdemo03;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public class MyArrayAdapter<T> extends ArrayAdapter<T>{

    private List<T> data;
    private T[] dataArray;

    // 定义接口
    public interface OnViewClickListener{
        /**
         * 点击按钮时调用此方法，并且传入按钮对应的item下标
         * @param position
         */
        void onButtonClick(int position);
    }

    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public MyArrayAdapter(Context context, int resource, int textViewResourceId, T[] objects) {
        super(context, resource, textViewResourceId, objects);
        setNotifyOnChange(true);
        this.dataArray = objects;
        toList();
    }

    public MyArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        this.data = objects;
    }

    private void toList(){
        if (dataArray != null){
            int size = dataArray.length;
            data = new ArrayList<>();
            for (int i = 0; i < size; i++){
                data.add(dataArray[i]);
            }
        }
        Log.d("zhou", "toList: data.size:"+data.size());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zhou", "onClick: 按钮的点击事件："+position);
                if (onViewClickListener != null){
                    onViewClickListener.onButtonClick(position);
                }
//                else if(dataArray != null){
////                    System.arraycopy(dataArray, position + 1, dataArray, position, dataArray.length - position -1);
//                }
            }
        });
        return view;
    }
}
