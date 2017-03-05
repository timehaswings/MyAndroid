package com.weylen.fragmentwithfragmentdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhou on 2016/4/6.
 */
public class TextFragment extends Fragment{

    public static final String TAG = TextFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_fragment, container, false);
    }

    private TextView textView;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.textView);
    }

    public void setText(String text){
        if (textView != null){
            textView.setText(text);
        }
    }
}
