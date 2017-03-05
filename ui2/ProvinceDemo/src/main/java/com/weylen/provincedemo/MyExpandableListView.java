package com.weylen.provincedemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by zhou on 2016/4/5.
 */
public class MyExpandableListView extends ExpandableListView{

    public MyExpandableListView(Context context) {
        super(context);
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1.大小值 2.度量的模式 MeasureSpec.AT_MOST 最大值  MeasureSpec.EXACTLY 精确值 MeasureSpec.UNSPECIFIED 模糊值
        int height = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
