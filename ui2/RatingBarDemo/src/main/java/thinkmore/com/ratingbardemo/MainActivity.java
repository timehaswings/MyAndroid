package thinkmore.com.ratingbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    // RatingBar 星星进度条

    private RatingBar ratingBar;
    private RatingBar ratingBar02;
    private RatingBar ratingBar03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar02 = (RatingBar) findViewById(R.id.ratingBar02);
        ratingBar03 = (RatingBar) findViewById(R.id.ratingBar03);
        // 设置星星个数改变监听
        ratingBar.setOnRatingBarChangeListener(listener);
    }

    private RatingBar.OnRatingBarChangeListener listener = new RatingBar.OnRatingBarChangeListener() {
        @Override // 1.当前视图对象 2.当前星星个数 3.是否右用户引起
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            ratingBar02.setRating(rating);
            ratingBar03.setRating(rating);
        }
    };
}
