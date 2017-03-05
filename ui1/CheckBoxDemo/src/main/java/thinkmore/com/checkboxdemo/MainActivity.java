package thinkmore.com.checkboxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox box1, box2, box3, box4, box5, box6;
    private CheckBox boxes[];
    private String oldText;
    private TextView messageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView textView = new TextView(this);
//        // 在控件的左上右下绘制图标 Intrinsic 内在 Bounds 边界
//        Drawable drawable = getResources().getDrawable(R.mipmap.menu_grzx);
//        // 1.左 2.上 3.右 4.下
////        textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
//        // 在控件的左上右下绘制图表，绘制的图标的大小不是由此方法决定。
//        // 需要在绘制图标之前先确定图标的大小
//        drawable.setBounds(0, 0, 200, 200);
//        textView.setCompoundDrawables(null, drawable, null, null);
//
//        setContentView(textView);
//        textView.setText("Hello");

        box1 = (CheckBox) findViewById(R.id.box1);
        box2 = (CheckBox) findViewById(R.id.box2);
        box3 = (CheckBox) findViewById(R.id.box3);
        box4 = (CheckBox) findViewById(R.id.box4);
        box5 = (CheckBox) findViewById(R.id.box5);
        box6 = (CheckBox) findViewById(R.id.box6);

        boxes = new CheckBox[]{box1, box2, box3, box4};

        box1.setOnCheckedChangeListener(listener);
        box2.setOnCheckedChangeListener(listener);
        box3.setOnCheckedChangeListener(listener);
        box4.setOnCheckedChangeListener(listener);
        box1.setOnClickListener(onClickListener);
        box2.setOnClickListener(onClickListener);
        box3.setOnClickListener(onClickListener);
        box4.setOnClickListener(onClickListener);
        box5.setOnClickListener(onClickListener);
        box6.setOnClickListener(onClickListener);

        messageView = (TextView) findViewById(R.id.message);
        oldText = messageView.getText().toString();
    }

    // 此方法内部设置的选中状态不会触发监听
    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override // 1.当前状态改变的CheckBox视图对象 2.当前视图对象的选中状态
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d("zhou", "onCheckedChanged: isChecked"+isChecked);
            String result = "";
            for (CheckBox box : boxes){
                // 如果CheckBox是选中状态
                if (box.isChecked()){
                    // 取出Box的文本
                    String boxName = box.getText().toString();
                    result += boxName +"、";
                }
            }
            // 判断字符串里面是有包含、
            if (result.indexOf("、") != -1){
                // 包含开始下标 不包含结束下标 [0, length -1}
                result = result.substring(0, result.length()-1);
            }
            messageView.setText(oldText + result);
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.box6){
                for (CheckBox box : boxes){
                    box.setChecked(!box.isChecked());
                }
            }else if (id == R.id.box5){
                for (CheckBox box : boxes){
                    box.setChecked(box5.isChecked());
                }
            }
        }
    };
}
