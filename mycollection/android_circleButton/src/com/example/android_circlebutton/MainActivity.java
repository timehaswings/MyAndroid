package com.example.android_circlebutton;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.lennon.view.CircleView;
import com.lennon.view.CircleView.Dir;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircleView cv = (CircleView) findViewById(R.id.cv);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        cv.dir = Dir.UP;
                        cv.postInvalidate();
                        Thread.sleep(1000);
                        
                        cv.dir = Dir.RIGHT;
                        cv.postInvalidate();
                        Thread.sleep(1000);
                   
                        cv.dir = Dir.DOWN;
                        cv.postInvalidate();
                        Thread.sleep(1000);
                    
                        cv.dir = Dir.LEFT;
                        cv.postInvalidate();
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();;

    }

}
