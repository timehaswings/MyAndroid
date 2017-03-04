package com.zyh.android.withthenotes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyh.android.withthenotes.adapter.BaseCostAdapter;
import com.zyh.android.withthenotes.db.CostDBBean;
import com.zyh.android.withthenotes.db.CostDBColumn;
import com.zyh.android.withthenotes.db.CostDBInterface;
import com.zyh.android.withthenotes.db.CostDBUtil;
import com.zyh.android.withthenotes.dialog.CustomListDialog;
import com.zyh.android.withthenotes.dialog.StringInfo;
import com.zyh.android.withthenotes.util.CostContentObserver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by zhou on 2016/1/15.
 */
public class CostListDetailsActivity extends AppCompatActivity {

    private TextView searchHintView;
    private ViewGroup container;
    private List<OnDataSetChanged> listeners = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int size = listeners.size();
            if(msg.what == 2 && size != 0){
                for(int i = 0 ;i < size ; i++){
                    listeners.get(i).onChange();
                }
                Log.d("zhou", "handleMessage: ");
            }
        }
    };

    private CostContentObserver observer = new CostContentObserver(handler);

    public interface OnDataSetChanged{
        void onChange();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost_details);

        container = (ViewGroup) findViewById(R.id.container);

        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText("消费明细");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.search);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopupWindow(v);
            }
        });

        searchHintView = (TextView) findViewById(R.id.search_hinttext);
        initFragment(0, new BaseCostAdapter(this, null), null, null);

        getContentResolver().registerContentObserver(CostDBColumn.CONTENT_URI, true, observer);
    }

    private void initFragment(int type, BaseCostAdapter costAdapter, String year, String month){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for(int i = 0; i < count ; i++){
            getSupportFragmentManager().popBackStack();
        }

        CostListFragment listFragment = CostListFragment.getInstance(type, year ,month);
        listFragment.setListAdapter(costAdapter);
        replaceFragment(listFragment, CostListFragment.TAG);
        queryCost(type, costAdapter);
    }

    private void replaceFragment(CostListFragment fragment, String tag){
        listeners.add(fragment);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private ProgressDialog progressDialog;

    private void queryCost(int type, BaseCostAdapter costAdapter){
        progressDialog = ProgressDialog.show(this, "", "查询中...");
        switch (type){
            case 0:
                queryCostYear(costAdapter);
                break;
            case 1:
                queryCostCurrentYearMonth(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        costAdapter);
                break;
            case 2:
                queryCostCurrentYearAll(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        costAdapter);
                break;
        }
    }

    private void queryCostYear(final BaseCostAdapter costAdapter){
        CostDBUtil.queryCostYear(this, new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        costAdapter.setData(data);
                        costAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void queryCostCurrentYearAll(String year, final BaseCostAdapter costAdapter){
        CostDBUtil.queryCostYearAll(this, year,new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        costAdapter.setData(data);
                        costAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void queryCostCurrentYearMonth(String year, final BaseCostAdapter adapter){
        CostDBUtil.queryCostMonth(this, year,new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void queryCostCurrentYearMonthDay(String year, String month,final BaseCostAdapter adapter){
        CostDBUtil.queryCostYearMonthDay(this, year, month, new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void showListPopupWindow(View anchor){
        final List<StringInfo> data = new ArrayList<>();
        data.add(new StringInfo("按年查看", false));
        data.add(new StringInfo("当年按月查看", false));
        data.add(new StringInfo("当年所有记录", false));
        final CustomListDialog listDialog = new CustomListDialog(this, R.style.Dialog_NoTitle);
        listDialog.setListData(data);
        listDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDialog.dismiss();
                searchHintView.setText(data.get(position).getName());
                container.removeAllViews();
                initFragment(position, new BaseCostAdapter(CostListDetailsActivity.this, null),String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),null);
            }
        });
        listDialog.setAnchor(anchor);
        listDialog.show();
    }


    public void onItemClick(int position,int type, BaseCostAdapter oldAdapter){
        if(type == 0){
            String year = oldAdapter.getData().get(position).getYear();
            CostListFragment listFragment = CostListFragment.getInstance(1, year, null);
            BaseCostAdapter adapter = new BaseCostAdapter(CostListDetailsActivity.this, null);
            listFragment.setListAdapter(adapter);
            replaceFragment(listFragment, CostListFragment.TAG);
            queryCostCurrentYearMonth(year, adapter);
        }else if(type == 1){
            String year = oldAdapter.getData().get(position).getYear();
            String month = oldAdapter.getData().get(position).getMonth();

            CostListFragment listFragment = CostListFragment.getInstance(2, year, month);
            BaseCostAdapter adapter = new BaseCostAdapter(CostListDetailsActivity.this, null);
            listFragment.setListAdapter(adapter);
            replaceFragment(listFragment, CostListFragment.TAG);
            queryCostCurrentYearMonthDay(year, month, adapter);
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count > 1){
            super.onBackPressed();
        }else{
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listeners.clear();
        getContentResolver().unregisterContentObserver(observer);
    }

    public void removeListener(CostListFragment fragment){
        listeners.remove(fragment);
    }
}
