package com.zyh.android.withthenotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.zyh.android.withthenotes.adapter.BaseCostAdapter;
import com.zyh.android.withthenotes.db.CostDBBean;
import com.zyh.android.withthenotes.db.CostDBInterface;
import com.zyh.android.withthenotes.db.CostDBUtil;

import java.util.List;

/**
 * Created by zhou on 2016/1/15.
 */
public class CostListFragment extends ListFragment implements CostListDetailsActivity.OnDataSetChanged{

    public static final String TAG = CostListFragment.class.getSimpleName();

    public static CostListFragment getInstance(int type, String year, String month){
        CostListFragment listFragment = new CostListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
        arguments.putString("year", year);
        arguments.putString("month", month);
        listFragment.setArguments(arguments);
        return listFragment;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        int type = getArguments().getInt("type");
        if(type == 2){
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("data",  ((BaseCostAdapter) getListAdapter()).getData().get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent,100);
        }else{
            ((CostListDetailsActivity)getActivity()).onItemClick(position, type , (BaseCostAdapter) getListAdapter());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == 100){
            int options = data.getIntExtra("DoOptions", -1);
            BaseCostAdapter adapter = ((BaseCostAdapter) getListAdapter());
            if(options == 1){
                adapter.removeData(data.getIntExtra("position", -1));
                adapter.notifyDataSetChanged();
            }else if(options == 2){
                CostDBBean info = (CostDBBean) data.getSerializableExtra("Info");
                adapter.getData().set(data.getIntExtra("position", -1), info);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void queryCostYear(final BaseCostAdapter costAdapter){
        CostDBUtil.queryCostYear(getActivity(), new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        costAdapter.setData(data);
                        costAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void queryCostCurrentYearMonth(String year, final BaseCostAdapter adapter){
        CostDBUtil.queryCostMonth(getActivity(), year,new CostDBInterface.OnQueryCost() {
            @Override
            public void complete(final List<CostDBBean> data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onChange() {
        int type = getArguments().getInt("type");
        String year = getArguments().getString("year");
        BaseCostAdapter adapter = ((BaseCostAdapter) getListAdapter());
        if(type == 0){
            queryCostYear(adapter);
        }else if(type == 1){
            queryCostCurrentYearMonth(year, adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((CostListDetailsActivity)getActivity()).removeListener(this);
    }
}
