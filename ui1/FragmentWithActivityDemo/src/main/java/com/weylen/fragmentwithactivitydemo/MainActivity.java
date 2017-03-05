package com.weylen.fragmentwithactivitydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextFragment.OnButtonClickListener{

    // Fragment的交互：
    // 1.Fragment与Activity的交互。
    // 2.Fragment与Fragment的交互。

    private TextView textView;
    private TextFragment textFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textFragment = new TextFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, textFragment, TextFragment.TAG)
                .commit();

        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TextFragment.TAG);
//                if (fragment != null){
//                    TextFragment textFragment = (TextFragment) fragment;
//                    textFragment.setText("修改的内容：234");
//                }
                if (textFragment != null){
                    textFragment.setText("修改的内容：345");
                }
            }
        });
    }

    public void setTextViewText(String text){
        if (textView != null){
            textView.setText(text);
        }
    }

    @Override
    public void onButtonClick(String text) {
        if (textView != null){
            textView.setText(text);
        }
    }
}
