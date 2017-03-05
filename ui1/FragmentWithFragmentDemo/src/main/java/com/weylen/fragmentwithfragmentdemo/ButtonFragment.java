package com.weylen.fragmentwithfragmentdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhou on 2016/4/6.
 */
public class ButtonFragment extends Fragment{

    public static final String TAG = ButtonFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.button_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = getActivity().getSupportFragmentManager()
//                        .findFragmentByTag(TextFragment.TAG);
//                if (fragment != null && fragment instanceof TextFragment){
//                    TextFragment textFragment = (TextFragment) fragment;
//                    textFragment.setText("修改的文本：123");
//                }

                Activity activity = getActivity();
                if (activity instanceof MainActivity){
                    MainActivity mainActivity = (MainActivity) activity;
                    mainActivity.setText("修改的文本：234");
                }
            }
        });
    }
}
