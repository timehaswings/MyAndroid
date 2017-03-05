package thinkmore.com.progressbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // ProgressBar 进度条 圆形的 长方形
    // 1.UI线程(Main线程，主线程)不能做耗时操作，0.1纳秒都不行，只要做耗时操作，进程就会阻塞，直到耗时结束。
    // 耗时过久，就有可能出现ANR(Application Not Respond)；
    // 在android里面只有一个主线程，其他通过new Thread().start,或者Timer等启动的线程都叫异步线程。
    // 所以，所有的耗时操作都应该放在异步线程完成。
    // 2.异步线程不能修改界面。包括像文本，颜色，图片。。。。除了ProgressBar,SeekBar

    // 异步线程与主线程的交互：
    // 1.Activity.runOnUiThread();
    // 2.View.post 或者View.postDelayed

    private ProgressBar progressBar;
    private static final int MAX = 100; // 假设下载的最大值为100
    private int progress; // 记录进度值
    private View buttonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void downloadClick(View view){
        this.buttonView = view;
        progressBar.setProgress(0); // 进度还原
        progress = 0;
        // 将按钮设置成不可用状态
        view.setEnabled(false);
        new DownloadThread().start();
    }

    private class DownloadThread extends Thread{
        @Override
        public void run() {
            // 每隔1秒增加9到11之间一个随机值
            Random random = new Random();
            try {
                while(progressBar.getProgress() < progressBar.getMax()){
                    Thread.sleep(1000);
                    // 计算出这一次下载的进度
                    int currentPro = random.nextInt(3) + 9;
                    progress += currentPro;
                    // 设置当前进度
//                    progressBar.setProgress(progress);
                    progressBar.incrementProgressBy(currentPro);// 在原有的进度值上增加多少
                }
                Log.d("zhou", "downloadClick: 下载完毕");
//                MainActivity.this.runOnUiThread(runnable);
                buttonView.post(runnable); // 立即执行
//                buttonView.postDelayed(runnable, 1000); // 延迟执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            buttonView.setEnabled(true);
        }
    };
}
