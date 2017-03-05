package thinkmore.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Button与TextView不同点
    // Button自带有一个间距(padding)和背景
    // View的事件-->点击事件 长按事件 触摸事件
    // 触摸事件-->3种：ACTION_DOWN ACTION_MOVE ACTION_UP

    // 快速修复：alt+enter

    // selector 选择器：给视图在不同的状态下设置不同的图片
    // color 给视图在不同的状态下设置不同的颜色 主要是文本的颜色
    // 在res资源包下面定义的名字只能包括 a,z 0-9 _ . 不能是纯数字，
    // int 111 = 222;
    // 具体做法：
    //  1.在drawable下面创建selector xml文件。
    // 颜色选择器
    //  1.先在res资源下面创建color文件夹
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        // 设置按钮的触摸事件
//        button.setOnTouchListener(touchListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override // 1.当前触发这个事件的视图对象 2.动作事件对象
        public boolean onTouch(View v, MotionEvent event) {
            // 获取当前的动作
            // ACTION_DOWN ACTION_MOVE ACTION_UP
            int action = event.getAction();
            switch (action){
                case MotionEvent.ACTION_DOWN: // 按下事件
                    // 设置按钮的背景资源(资源可以是颜色也可以是图片资源)
                    button.setBackgroundResource(android.R.color.holo_purple);
                    break;
                case MotionEvent.ACTION_UP: // 松开事件
                    button.setBackgroundResource(android.R.color.holo_blue_bright);
                    break;
            }
            // 返回值：是否拦截后续的事件 返回true表示拦截
            return false;
        }
    };
}
