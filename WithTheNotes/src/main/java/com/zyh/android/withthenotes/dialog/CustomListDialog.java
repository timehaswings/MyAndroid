package com.zyh.android.withthenotes.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zyh.android.withthenotes.R;

import java.util.List;

/**
 * Created by zhou on 2016/1/7.
 */
public class CustomListDialog extends Dialog{

    private String title;
    private List<StringInfo> listData;// 列表数据
    private CustomListAdapter adapter;
    private Context context;
    private AdapterView.OnItemClickListener itemClickListener;
    private View anchor;

    public CustomListDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StringInfo> getListData() {
        return listData;
    }

    public void setListData(List<StringInfo> listData) {
        this.listData = listData;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setAnchor(View anchor) {
        this.anchor = anchor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_list_dialog);

        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomListAdapter(context,listData);
        listView.setAdapter(adapter);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 400; // 全屏的
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.y = anchor.getBottom();

        getWindow().setAttributes(params);
        listView.setOnItemClickListener(itemClickListener);

        setCanceledOnTouchOutside(true);
    }
}
