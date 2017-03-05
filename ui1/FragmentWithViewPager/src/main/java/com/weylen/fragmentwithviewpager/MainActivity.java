package com.weylen.fragmentwithviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRbClick();

        replaceFragment(new FragmentViewPager(), FragmentViewPager.TAG);
    }

    public void setupRbClick(){
        findViewById(R.id.rb01).setOnClickListener(rbClick);
        findViewById(R.id.rb02).setOnClickListener(rbClick);
    }

    private View.OnClickListener rbClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = null;
            String tag = null;
            switch (v.getId()){
                case R.id.rb01:
                    tag = FragmentViewPager.TAG;
                    fragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (fragment == null){
                        fragment = new FragmentViewPager();
                    }
                    break;
                case R.id.rb02:
                    tag = ButtonFragment.TAG;
                    fragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (fragment == null){
                        fragment = new ButtonFragment();
                    }
                    break;
            }
            replaceFragment(fragment, tag);
        }
    };

    private void replaceFragment(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }
}
