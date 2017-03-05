package com.weylen.spinnerdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Spinner

    public static final String[] COLORS = {"红色", "橙色", "黄色", "绿色", "蓝色", "青色", "紫色"};

    private TextView provinceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, COLORS);
        spinner.setAdapter(arrayAdapter);

        spinner.setPrompt("选择颜色");

        provinceView = (TextView) findViewById(R.id.chooseProvince);
        provinceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
    }

    private void showListDialog(){
        final String provinces[] = new String[]{"北京", "上海", "四川", "河南", "湖北", "山东"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择省份");
        builder.setItems(provinces, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                provinceView.setText(provinces[which]);
            }
        });
//        builder.show();

        // ListPopupWindow
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, provinces);
        listPopupWindow.setAdapter(arrayAdapter);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                provinceView.setText(provinces[position]);
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setWidth(600);
        listPopupWindow.setAnchorView(provinceView);// 设置固定视图
        listPopupWindow.show();
    }
}
