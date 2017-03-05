package com.weylen.fragmentwithactivitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TwoActivity extends AppCompatActivity implements TextFragment.OnButtonClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TextFragment(), TextFragment.TAG)
                .commit();
    }

    @Override
    public void onButtonClick(String text) {
        Log.d("zhou", "onButtonClick: 点击了TextFragment的文本控件");
    }
}
