package com.weylen.fragmentsavedata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhou on 2016/4/6.
 */
public class TextFragment extends Fragment{

    private TextView textView;

    public static final String TAG = TextFragment.class.getSimpleName();

    private String text;
    // 单实例

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView = new TextView(getActivity());
        textView.setText("这是初始化的内容");
        text = DataUtil.getInstance().getText();
        if (text != null){
            Log.d("zhou", "onCreateView: text:"+text);
            textView.setText(text);
        }
        return textView;
    }

    public void setText(String text){
        if (textView != null){
            textView.setText(text);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        text = textView.getText().toString();
        DataUtil.getInstance().setText(text);
    }
}
