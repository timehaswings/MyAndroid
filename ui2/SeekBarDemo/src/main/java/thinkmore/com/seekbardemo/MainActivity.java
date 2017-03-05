package thinkmore.com.seekbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // SeekBar 拖动的进度条
    private TextView playTimeView, totalView;
    private SeekBar seekBar;
    private ImageButton imageButton;
    private boolean isPlaying = false; // 定义是否正在播放
    private int max = -1;// 记录歌曲的时间值
    private int playProgress = 0; // 记录歌曲的播放的进度值
    private int threadTime = 500; // 定义一个休眠的时间
    private boolean isTouchSeekBar;// 是否拖动进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playTimeView = (TextView) findViewById(R.id.playTime);
        totalView = (TextView) findViewById(R.id.totalTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        imageButton = (ImageButton) findViewById(R.id.playButton);
        // 设置按钮的点击事件
        imageButton.setOnClickListener(buttonClick);
        // 设置进度条的进度改变监听
        seekBar.setOnSeekBarChangeListener(listener);
    }

    private SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
        @Override // 进度值发生改变调用此方法 1.视图对象 2.当前进度值 3.进度值的改变是否由用户引起(是否拖动引起的)
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override // 开始触摸的时候调用
        public void onStartTrackingTouch(SeekBar seekBar) {
            isTouchSeekBar = true;
        }

        @Override // 停止触摸
        public void onStopTrackingTouch(SeekBar seekBar) {
            isTouchSeekBar = false;
            playProgress = seekBar.getProgress();
            seekBar.setProgress(playProgress);
            playTimeView.setText(formatString(playProgress));
        }
    };

    private int randomLength(){
        int milliseconds = 4 * 60 * 1000;
        int randomNum = new Random().nextInt(60 * 1000);
        return milliseconds + randomNum;
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 如果没有播放
            if (isPlaying == false){
                if (max == -1){ // 没有设定最大值的时候 获取一个随机值
                    max = randomLength();
                    seekBar.setMax(max);
                    totalView.setText(formatString(max));
                }
                isPlaying = true; // 记录正在播放
                imageButton.setImageResource(android.R.drawable.ic_media_pause);
                new UpdatePlayTimeThread().start();
                // 正在播放
            }else{
                isPlaying = false; // 记录暂停播放
                imageButton.setImageResource(android.R.drawable.ic_media_play);
            }
        }
    };


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            playProgress += threadTime;
            // 设置当前进度
            if (isTouchSeekBar == false){
                seekBar.setProgress(playProgress);
            }
            // 设置时间文本
            playTimeView.setText(formatString(playProgress));
        }
    };

    /**
     * 将毫秒数的时间转换成分+秒的形式
     * @param time
     * @return
     */
    private String formatString(int time){
        // 创建SimpleDateFormat 对象 1.模板 2.地区
        // y M d h H m s S yyyy-MM-dd HH:mm:ss:SSS
        SimpleDateFormat format = new SimpleDateFormat("mm:ss", Locale.CHINESE);
        return format.format(new Date(time));
    }

    private class UpdatePlayTimeThread extends Thread{
        @Override
        public void run() {
            // 当歌曲正在播放并且当前进度值小于最大值的时候进行循环
            while(isPlaying == true && playProgress < max){
                // 修改当前进度的文本,修改进度条的值
                MainActivity.this.runOnUiThread(runnable);
                try {
                    Thread.sleep(threadTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
