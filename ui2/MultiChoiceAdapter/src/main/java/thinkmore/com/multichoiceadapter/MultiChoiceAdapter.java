package thinkmore.com.multichoiceadapter;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MultiChoiceAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<ProductInfo> data;
    private boolean isVisible; // 判断CheckBox是否显示
    private Map<Integer, Boolean> status = new HashMap<>();
    // 表示key为Integer的map
    private SparseArray sparseArray = new SparseArray();
    // key为Integer值为Boolean，当key不存在时返回的默认值是false
    private SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();

    public MultiChoiceAdapter(Context context, List<ProductInfo> data){
        this.mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public ProductInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置CheckBox的可见性 true 可见 false 隐藏
     * @param visible
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
        this.notifyDataSetChanged(); // 更新界面 重新调用getView方法
    }

    public void removeChoose(){
        int count = getCount();
        List<ProductInfo> removeData = new ArrayList<>();
        for (int i = 0; i < count; i++){ // 0 ~ 19 0~18
            ProductInfo productInfo = data.get(i);
            if (productInfo.isChecked()){
                removeData.add(productInfo);
            }
        }
        data.removeAll(removeData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_gridview, parent, false);
            holder.imgView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.priceView = (TextView) convertView.findViewById(R.id.price);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            // 保存holder对象
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 获取item对应的数据对象
        final ProductInfo productInfo = getItem(position);
        // 设置数据
        holder.nameView.setText(productInfo.getProductName());
        holder.priceView.setText(productInfo.getProductPrice());

        // 设置CheckBox的可见性
        holder.checkBox.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        // 设置CheckBox的状态
//        holder.checkBox.setChecked(status.get(position) == null ? false : status.get(position));
        holder.checkBox.setChecked(productInfo.isChecked());
        // 设置CheckBox的点击事件
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存CheckBox的状态
//                status.put(position, holder.checkBox.isChecked());
                productInfo.setChecked(holder.checkBox.isChecked());
            }
        });

        return convertView;
    }

    private class ViewHolder{
        private ImageView imgView;
        private TextView nameView, priceView;
        private CheckBox checkBox;
    }
}
