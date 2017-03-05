package com.weylen.fragmentwithactivitydemo;

import android.app.Activity;
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

    private TextView textView;

    private OnButtonClickListener onButtonClickListener;

    public interface OnButtonClickListener{
        void onButtonClick(String text);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnButtonClickListener){
            onButtonClickListener = (OnButtonClickListener) activity;
        }
    }

    @Nullable
    @Override // 创建视图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override // 在视图创建之后调用 1.所创建的视图 就是onCreateView返回的视图
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.fragment_text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
//                // 判断activity是否为MainActivity的实例
//                if (activity instanceof MainActivity){
//                    MainActivity mainActivity = (MainActivity) activity;
//                    mainActivity.setTextViewText("Hello 你好吗");
//                }

//                if (activity instanceof TwoActivity){
//                    TwoActivity twoActivity = (TwoActivity) activity;
//                }
                if (onButtonClickListener != null){
                    onButtonClickListener.onButtonClick("Hello 123");
                }
            }
        });
    }

    public void setText(String text){
        if (textView != null){
            textView.setText(text);
        }
    }
}
