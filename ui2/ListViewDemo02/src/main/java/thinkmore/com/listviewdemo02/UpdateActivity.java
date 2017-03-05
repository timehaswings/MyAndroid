package thinkmore.com.listviewdemo02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        editText = (EditText) findViewById(R.id.input);
        // 取出传递的字符串
        String str = getIntent().getStringExtra("Data");
        editText.setText(str); // 设置默认显示的文本
    }

    public void saveClick(View view){
        // 取出输入框的内容
        String str = editText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("Data", str);
        intent.putExtra("Position", getIntent().getIntExtra("Position", -1));
        // 设置返回的数据
        setResult(RESULT_OK, intent);
        this.finish();// 关闭页面
    }
}
