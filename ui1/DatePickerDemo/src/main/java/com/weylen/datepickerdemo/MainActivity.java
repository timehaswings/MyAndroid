package com.weylen.datepickerdemo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // DatePicker TimePicker Calendar

    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    datePicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.GONE);
                }else {
                    datePicker.setVisibility(View.GONE);
                    timePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        // 是获取默认的时区的日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d("zhou", "onCreate: weekDay:"+weekday);

        // Calendar的月是从0开始的
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("zhou", "onDateChanged: date:"+(year+"-"+(monthOfYear+1)+"-"+dayOfMonth));
            }
        });

        timePicker.setIs24HourView(true);
        // Calendar.HOUR_OF_DAY 是24小时制 Calendar.HOUR是12小时制
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        // 时间改变的监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("zhou", "onTimeChanged: time:"+hourOfDay+"："+minute);
            }
        });

        ToggleButton toggleButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    showDatePickerDialog();
                }else {
                    showTimePickerDialog();
                }
            }
        });
    }

    private void showDatePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(MainActivity.this, formatDate(year, monthOfYear, dayOfMonth), Toast.LENGTH_SHORT).show();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(MainActivity.this, hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
                    }
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    public String formatDate(int year, int month, int day){
//        String monthStr = String.valueOf(month);
//        if (month < 10){
//            monthStr = "0" + month;
//        }
//        String dayStr = String.valueOf(day);
//        if (day < 10){
//            dayStr = "0"+dayStr;
//        }
//        return year+"-"+monthStr+"-"+dayStr;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(new Date(calendar.getTimeInMillis()));
    }
}
