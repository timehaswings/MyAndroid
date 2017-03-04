package com.weylen.propertyanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // 属性动画的最大父类：Animator
    // ValueAnimator AnimatorSet
    // ObjectAnimator

    private Button button;
    private Cycle cycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startValueAnimator();
            }
        });

        cycle = (Cycle) findViewById(R.id.cycle);
    }

    private void startValueAnimator(){
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);// 设置动画持续时间

        PointF startValue = new PointF(50, 50);
        valueAnimator.setObjectValues(startValue, new PointF(720, 720));
        // 必须在这是数据之后才能设置自定义的属性值计算方式
        valueAnimator.setEvaluator(new PointFEvaluatorTest());
        // 添加属性值的更新监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF currentValue = (PointF) animation.getAnimatedValue();// 得到当前的属性值
                Log.d("zhou", "onAnimationUpdate: currentValue:"+currentValue);
                cycle.setPoint(currentValue);
            }
        });
//        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    private class PointFEvaluatorTest implements TypeEvaluator<PointF>{

        private PointF pointF;

        @Override // 属性计算方法：1.时间因子 2. 开始值 3.结束值
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            Log.d("zhou", "evaluate: startValue:"+startValue+",endValue:"+endValue);
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * fraction *  (endValue.x - startValue.x) / 2;
            if (pointF != null){
                pointF.set(x, y);
            }else{
                pointF = new PointF(x, y);
            }
            return pointF;
        }
    }

    /**
     * ObjectAnimator
     */
    public void useObjectAnimator(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 2.0f, 0.5f);

        // 添加一个动画
        PropertyValuesHolder holder01 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2.0f, 0.5f);
        PropertyValuesHolder holder02 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f);
        animator = ObjectAnimator.ofPropertyValuesHolder(view, holder01, holder02);
        animator.setDuration(3000);
//        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 2.0f, 0.5f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.5f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        // 这样写是错误的
//        animatorSet.play(animator1).with(animator2).before(animator3);
        // 最多有2个动画关系
        animatorSet.play(animator1).with(animator2);
        animatorSet.play(animator1).after(animator3);
//        animatorSet.start();
//        animatorSet.playSequentially(); // 顺序播放
//        animatorSet.playTogether(); // 同时播放

        Animator animatorXml = AnimatorInflater.loadAnimator(this, R.animator.xml_animator);
        animatorXml.setDuration(3000);
        animatorXml.setTarget(view);// 设置完成动画的对象
        animatorXml.start();
    }
}
