package com.weylen.fragmentwithfragmentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Fragment与Fragment交互

    private TextFragment textFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textFragment = new TextFragment();

        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container1, new ButtonFragment(), ButtonFragment.TAG)
            .replace(R.id.container2, textFragment, TextFragment.TAG)
            .commit();
    }

    public void setText(String text){
        if (textFragment != null){
            textFragment.setText(text);
        }

//        Fragment fragment = getSupportFragmentManager()
//                        .findFragmentByTag(TextFragment.TAG);
//        if (fragment != null && fragment instanceof TextFragment){
//            TextFragment textFragment = (TextFragment) fragment;
//            textFragment.setText("修改的文本：123");
//        }
    }
}
