package com.weylen.propertyanimationdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhou on 2016/4/25.
 */
public class Cycle extends View{

    private float radius = 30;
    private float x = 50, y = 50;
    public Cycle(Context context) {
        super(context);
        initPaint();
    }

    public Cycle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public Cycle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private Paint paint;
    private void initPaint(){
        paint = new Paint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate(); // 更新UI 表示重新调用onDraw方法
    }

    public void setPoint(PointF point){
        x = point.x;
        y = point.y;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        // 1.2. 圆心坐标 3.半径 4.画笔
//        canvas.drawCircle(w/2, h/2, radius, paint);
        canvas.drawCircle(x, y, radius, paint);
    }

    private int w, h;
    @Override // 视图大小发生改变时调用
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }
}
