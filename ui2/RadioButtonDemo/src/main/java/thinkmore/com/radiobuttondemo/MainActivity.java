package thinkmore.com.radiobuttondemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // RadioGroup和RadioButton
    // 只有在同一个单选按钮组里面的单选按钮才有单选效果
    // 如果不在同一个组里面，单选按钮就只拥有选中效果。如果要取消选中就只能通过代码的方式取消，不能手动取消。

    private RadioGroup radioGroup;
    private RelativeLayout layout1, layout2, layout3;
    private static final int[] IDS = {R.id.layout1, R.id.layout2, R.id.layout3};
    private View[] views = null;
    private TextView[] textViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        // 设置组选择改变监听
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        layout3 = (RelativeLayout) findViewById(R.id.layout3);
        layout1.setOnClickListener(onClickListener);
        layout2.setOnClickListener(onClickListener);
        layout3.setOnClickListener(onClickListener);
        views = new View[]{layout1, layout2, layout3};

        TextView textView1 = (TextView) findViewById(R.id.text1);
        TextView textView2 = (TextView) findViewById(R.id.text2);
        TextView textView3 = (TextView) findViewById(R.id.text3);
        textViews = new TextView[]{textView1, textView2, textView3};

        drawable1 = getResources().getDrawable(android.R.drawable.ic_media_next);
        drawable2 = getResources().getDrawable(android.R.drawable.ic_media_previous);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override // 1.RadioGroup组对象 2.当前被选中的RadioButton的id值
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.male:
                    Log.d("zhou", "onCheckedChanged: 选中的是男");
                    break;
                case R.id.female:
                    Log.d("zhou", "onCheckedChanged: 选中的是女");
                    break;
            }
        }
    };

    private Drawable drawable1 = null;
    private Drawable drawable2 = null;
    private int lastChoiceId = -1;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (lastChoiceId == id){
                return;
            }
            for (int i = 0; i < IDS.length; i++){
                if (id == IDS[i]){
                    views[i].setBackgroundResource(android.R.color.holo_orange_dark);
                    textViews[i].setTextColor(Color.RED);
                    textViews[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable1, null, null);
                }else{
                    views[i].setBackgroundResource(android.R.color.holo_blue_dark);
                    textViews[i].setTextColor(Color.WHITE);
                    textViews[i].setCompoundDrawablesWithIntrinsicBounds(null, drawable2, null, null);
                }
            }
            lastChoiceId = id;
        }
    };
}
