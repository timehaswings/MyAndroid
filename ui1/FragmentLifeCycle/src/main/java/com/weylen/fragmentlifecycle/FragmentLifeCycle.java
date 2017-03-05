package com.weylen.fragmentlifecycle;

import android.app.Activity;
import android.content.Context;
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
public class FragmentLifeCycle extends Fragment{

    // getSimpleName实际得到的就是此类的类名
    // getName 包+类名
    public static final String TAG = FragmentLifeCycle.class.getSimpleName();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("zhou", "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("zhou", "onCreate: ");
    }

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("zhou", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_lifecycle, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }

    public void updateText(String text){
        textView.setText(text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("zhou", "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("zhou", "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("zhou", "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("zhou", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("zhou", "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("zhou", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("zhou", "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("zhou", "onDetach");
    }
}
