package com.weylen.mediarecorderdemo;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // MediaRecorder 录音器

    private File RECORDER_ROOT_FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    private LinearLayout container;
    private Button recorderButton;
    private boolean isStartRecorder;
    private MediaRecorder recorder;
    private MediaPlayer mediaPlayer;
    private String outputFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.container);
        recorderButton = (Button) findViewById(R.id.recorderButton);
        recorderButton.setOnLongClickListener(longListener);
        recorderButton.setOnTouchListener(touchListener);
    }

    private View.OnLongClickListener longListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            recorderButton.setText("松开结束录音");
            startRecorder();
            return false;
        }
    };

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction(); // 获取当前的事件动作
            if (action == MotionEvent.ACTION_UP){
                stopRecorder();
            }
            return false;
        }
    };

    /**
     * 录音按钮的监听
     * @param view
     */
    public void recorderButtonClick(View view){

    }

    private void resetRecorder(){
        if (recorder != null){
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    private static String getCurrentTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
    }

    /**
     * 开始录音
     */
    private void startRecorder(){
        resetRecorder();
        recorder = new MediaRecorder();
        // 设置录音的属性
        // Call this only before setOutputFormat().
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置录音来源为麦克风
        // Call this after setAudioSource()/setVideoSource() but before prepare().
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB); // 设置录音文件的格式
        // Call this after setOutputFormat() but before prepare().
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 设置音频格式
        outputFilePath = RECORDER_ROOT_FILE + File.separator + getCurrentTime() + ".amr";
        recorder.setOutputFile(outputFilePath);// 设置输出文件的地址
        try {
            recorder.prepare(); // 准备录音
            recorder.start(); // 开始录音
            isStartRecorder = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    private void stopRecorder(){
        if (isStartRecorder == true && recorder != null){
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
            isStartRecorder = false;
            recorderButton.setText("按住开始录音");
            // 添加此录音对应的视图
            container.addView(createRecorderText());
        }
    }

    private void resetPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private TextView createRecorderText(){
        final TextView textView = new TextView(this);
        textView.setText(outputFilePath);
        textView.setPadding(10, 10, 10, 10);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zhou", "onClick: 点击了文本");
                play(textView.getText().toString());
            }
        });
        return textView;
    }

    private void play(String source){
        resetPlayer();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(source);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
