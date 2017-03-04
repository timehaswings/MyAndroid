package com.zyh.android.withthenotes.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zyh.android.withthenotes.R;
import com.zyh.android.withthenotes.db.CostDBBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhou on 2016/1/15.
 */
public class BaseCostAdapter extends SimpleBaseAdapter<CostDBBean>{

    public BaseCostAdapter(Context context, List<CostDBBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = getInflater().inflate(R.layout.base_cost_item, parent, false);
        }

        CostDBBean bean = getData().get(position);
        TextView titleView = (TextView) convertView.findViewById(R.id.cost_title);
        titleView.setText(bean.getEventName());

        TextView moneyView = (TextView) convertView.findViewById(R.id.cost_money);
        moneyView.setText(bean.getEventCost());

        return convertView;
    }
}
