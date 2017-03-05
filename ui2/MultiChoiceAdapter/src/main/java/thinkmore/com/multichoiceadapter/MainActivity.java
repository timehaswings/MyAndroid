package thinkmore.com.multichoiceadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private MultiChoiceAdapter adapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new MultiChoiceAdapter(this, initData());
        gridView.setAdapter(adapter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonClick);
    }

    private List<ProductInfo> initData(){
        List<ProductInfo> data = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductName((i+1) + "妮维雅 清润舒爽沐浴露");
            productInfo.setProductPrice("￥ 33.9");
            data.add(productInfo);
        }
        return data;
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 取出按钮的文本
            String text = button.getText().toString();
            if ("删除".equals(text)){
                button.setText("确定");
                // 显示CheckBox
                adapter.setVisible(true);
            }else {
                adapter.removeChoose();
                button.setText("删除");
                // 隐藏CheckBox
                adapter.setVisible(false);
            }
        }
    };
}
