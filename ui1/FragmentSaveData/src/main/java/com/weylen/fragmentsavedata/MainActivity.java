package com.weylen.fragmentsavedata;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 添加TextFragment
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, new TextFragment(), TextFragment.TAG)
                .commit();
    }

    public void replaceFragmentClick(View view){
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, new ButtonFragment(), ButtonFragment.TAG)
                .commit();
    }

    public void updateTextClick(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TextFragment.TAG);
        if (fragment != null){
            TextFragment textFragment = (TextFragment) fragment;
            textFragment.setText("修改的内容：123");
        }

    }
}
