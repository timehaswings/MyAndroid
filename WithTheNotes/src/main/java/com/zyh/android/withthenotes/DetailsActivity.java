package com.zyh.android.withthenotes;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zyh.android.withthenotes.db.CostDBBean;
import com.zyh.android.withthenotes.db.CostDBInterface;
import com.zyh.android.withthenotes.db.CostDBUtil;
import com.zyh.android.withthenotes.dialog.CustomListDialog;
import com.zyh.android.withthenotes.dialog.StringInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity{

    private EditText eventTitle,eventDesc,eventCost;
    private TextView eventDate;
    private int id, position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcost_layout);

        eventTitle = (EditText) findViewById(R.id.cost_title);
        eventCost = (EditText) findViewById(R.id.cost_money);
        eventDate = (TextView) findViewById(R.id.cost_date);
        eventDesc = (EditText) findViewById(R.id.cost_desc);
        eventDate.setOnClickListener(chooseDateClick);

        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText("消费详情");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.this.finish();
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

        Intent intent = getIntent();
        CostDBBean bean = (CostDBBean) intent.getSerializableExtra("data");
        id = bean.getId();
        position = intent.getIntExtra("position", -1);

        updateUiStatus(false);

        queryCostUseId();
    }

    private void queryCostUseId(){
        CostDBUtil.queryCostUseId(this, id, new CostDBInterface.OnQueryCostUseId() {
            @Override
            public void complete(CostDBBean bean) {
                eventCost.setText(bean.getEventCost());
                eventDate.setText(bean.getEventDate());
                eventTitle.setText(bean.getEventName());
                eventDesc.setText(bean.getEventDesc());
            }
        });
    }

    private void updateUiStatus(boolean isEditable){
        if(!isEditable){
            eventTitle.setEnabled(false);
            eventCost.setEnabled(false);
            eventDate.setEnabled(false);
            eventDesc.setEnabled(false);
            findViewById(R.id.submite).setVisibility(View.GONE);
        }else{
            eventTitle.setEnabled(true);
            eventCost.setEnabled(true);
            eventDate.setEnabled(true);
            eventDesc.setEnabled(true);
            findViewById(R.id.submite).setVisibility(View.VISIBLE);
        }
    }

    private void doItemSelected(int position){
        if(position == 0){
            updateUiStatus(true);
        }else{
            showDeleteDialog();
        }
    }

    private void deleteCost(){
        progressDialog = ProgressDialog.show(this, "" , "删除中...");
        CostDBUtil.deleteCost(this, id, new CostDBInterface.OnAddNewCost() {
            @Override
            public void complete(final boolean isSuccess) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        if(isSuccess){
                            Toast.makeText(DetailsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("DoOptions",1);
                            intent.putExtra("position", position);
                            setResult(RESULT_OK, intent);
                            DetailsActivity.this.finish();
                        }else{
                            Toast.makeText(DetailsActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void showDeleteDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要删除当条数据？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteCost();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showListPopupWindow(View anchor){
        final List<StringInfo> data = new ArrayList<>();
        data.add(new StringInfo("编辑", false));
        data.add(new StringInfo("删除", false));
        final CustomListDialog listDialog = new CustomListDialog(this, R.style.Dialog_NoTitle);
        listDialog.setListData(data);
        listDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listDialog.dismiss();
                doItemSelected(position);
            }
        });
        listDialog.setAnchor(anchor);
        listDialog.show();
    }

    private View.OnClickListener chooseDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog pickerDialog = new DatePickerDialog(DetailsActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if(monthOfYear >= 9){
                                eventDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }else{
                                eventDate.setText(year+"-0"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        }
                    },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            pickerDialog.show();
        }
    };

    private ProgressDialog progressDialog;
    // 提交按钮的监听器
    public void submitClick(View view){
        String title = eventTitle.getText().toString();
        if(title.length() >= 10){
            Toast.makeText(this, "标题的字符个数只能在10个以内", Toast.LENGTH_SHORT).show();
            return;
        }

        String cost = eventCost.getText().toString();
        if(cost.equals("")){
            Toast.makeText(this, "请输入消费金额", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateStr = eventDate.getText().toString();
        if(dateStr.equals("")){
            Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            calendar.setTime(date);

            final CostDBBean info = new CostDBBean(title, eventDesc.getText().toString(), cost,
                    dateStr, String.valueOf(calendar.get(Calendar.YEAR)),
                    String.valueOf(calendar.get(Calendar.MONTH)),
                    String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),id);

            progressDialog = ProgressDialog.show(this, "", "录入中...");
            CostDBUtil.updateCost(this, info, new CostDBInterface.OnAddNewCost() {
                @Override
                public void complete(final boolean isSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(progressDialog != null && progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            if(isSuccess){
                                Toast.makeText(DetailsActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("DoOptions",2);
                                intent.putExtra("position", position);
                                info.setEventName(info.getEventName()+"\n"+info.getEventDate());
                                intent.putExtra("Info", info);
                                setResult(RESULT_OK, intent);
                                DetailsActivity.this.finish();

                            }else{
                                Toast.makeText(DetailsActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "日期格式出现问题", Toast.LENGTH_SHORT).show();
        }

    }
}
