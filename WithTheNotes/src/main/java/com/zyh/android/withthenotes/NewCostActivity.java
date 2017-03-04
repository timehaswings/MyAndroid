package com.zyh.android.withthenotes;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zyh.android.withthenotes.db.CostDBBean;
import com.zyh.android.withthenotes.db.CostDBInterface;
import com.zyh.android.withthenotes.db.CostDBUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhou on 2016/1/15.
 * 新增消费页面
 */
public class NewCostActivity extends AppCompatActivity{

    private EditText eventTitle,eventDesc,eventCost;
    private TextView eventDate;

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
        textView.setText("新增消费");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewCostActivity.this.finish();
            }
        });
    }

    private View.OnClickListener chooseDateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog pickerDialog = new DatePickerDialog(NewCostActivity.this,
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

            CostDBBean info = new CostDBBean(title, eventDesc.getText().toString(), cost,
                    dateStr, String.valueOf(calendar.get(Calendar.YEAR)),
                    String.valueOf(calendar.get(Calendar.MONTH)),
                    String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),0);

            progressDialog = ProgressDialog.show(this, "", "录入中...");
            CostDBUtil.addNewCost(this, info, new CostDBInterface.OnAddNewCost() {
                @Override
                public void complete(final boolean isSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(progressDialog != null && progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            if(isSuccess){
                                Toast.makeText(NewCostActivity.this, "录入成功", Toast.LENGTH_SHORT).show();
                                NewCostActivity.this.finish();
                            }else{
                                Toast.makeText(NewCostActivity.this, "录入失败", Toast.LENGTH_SHORT).show();
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
