package com.zyh.android.withthenotes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zyh.android.withthenotes.db.CostDBColumn;
import com.zyh.android.withthenotes.db.CostDBInterface;
import com.zyh.android.withthenotes.db.CostDBUtil;
import com.zyh.android.withthenotes.util.CostContentObserver;

public class HomeActivity extends AppCompatActivity {


    private TextView costTextView;
    private FloatingActionButton floatingActionButton;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 2){
                queryAllCost(false);
            }
        }
    };

    private CostContentObserver observer = new CostContentObserver(handler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton);
        floatingActionButton.setOnClickListener(addNewCostClick);
        floatingActionButton.setOnTouchListener(touchListener);

        costTextView = (TextView) findViewById(R.id.costText);

        getContentResolver().registerContentObserver(CostDBColumn.CONTENT_URI, true, observer);
        queryAllCost(false);
    }

    /**
     * 查询所有的消费
     */
    private void queryAllCost(boolean isShowProgress){
        if(isShowProgress){
            progressDialog = ProgressDialog.show(this, "", "查询中...");
        }
        CostDBUtil.getAllCost(this, new CostDBInterface() {
            @Override
            public void onQueryResult(final String cost) {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        costTextView.setText(cost+"￥");
                    }
                });
            }
        });
    }

    private View.OnClickListener addNewCostClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomeActivity.this, NewCostActivity.class));
        }
    };

    private float offsetX,offsetY;
    private float startX,startY;
    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            float x = event.getX(); // 此x,y值是相对于按钮的坐标
//            float y = event.getY();
//            int action = event.getAction();
//            switch (action){
//                case MotionEvent.ACTION_DOWN:
//                    offsetX = x;
//                    offsetY = y;
//                    startX = floatingActionButton.getLeft();
//                    startY = floatingActionButton.getTop();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    floatingActionButton.setX(startX + x - offsetX);
//                    floatingActionButton.setY(startY + y - offsetY);
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    break;
//            }

            return false;
        }
    };

    // 清除消费记录按钮的监听事件
    public void deleteViewClick(View view){
        showDeleteDialog();
    }

    private void showDeleteDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要清除所有的数据？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteAllCost();
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

    private void deleteAllCost(){
        progressDialog = ProgressDialog.show(this, "", "正在清除所有的数据");
        CostDBUtil.deleteAllCost(this, new CostDBInterface.OnDeleteAllCost() {
            @Override
            public void complete() {
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(progressDialog != null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        costTextView.setText("0.00￥");
                    }
                });
            }
        });
    }

    // 查询明细按钮的监听事件
    public void queryDetailsClick(View view){
        startActivity(new Intent(this, CostListDetailsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}
