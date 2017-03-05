package com.weylen.fragmentwithviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhou on 2016/4/13.
 */
public class NumberFragment extends Fragment{

    public static final String TAG = NumberFragment.class.getSimpleName();

    private TextView textView;

    public static NumberFragment getInstance(int count){
        NumberFragment numberFragment = new NumberFragment();
        // 此方法只能在未被依附Activity之前调用
        Bundle bundle = new Bundle(); // 构建一个Bundle对象
        bundle.putInt("Number", count); // 封装数据
        numberFragment.setArguments(bundle); // 设置参数
        return numberFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d("zhou", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_number, container, false);
    }

    @Override // 视图被创建的时候调用 1.View对象既是onCreateView返回的视图
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.text1);
        Bundle bundle = getArguments(); // 取出保存的参数
        if (bundle != null){
            int number = bundle.getInt("Number", -1);
            textView.setText(String.valueOf(number));
        }else {
            textView.setText("未设置任何的数据");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Log.d("zhou", "onDestroyView: ");
    }
}
