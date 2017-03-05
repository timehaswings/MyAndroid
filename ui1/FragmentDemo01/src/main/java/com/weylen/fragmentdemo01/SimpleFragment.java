package com.weylen.fragmentdemo01;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhou on 2016/4/6.
 */
public class SimpleFragment extends Fragment{

    // getActivity(); 返回此Fragment所被依附的Activity对象

    public static final String TAG = "SimpleFragment";

    @Nullable
    @Override // 1.解析视图 2.此Fragment要被加载到哪一个容器里面 3.
    // 返回的视图对象既是此Fragment显示的内容
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("SimpleFragment");
        return textView;
    }
}
