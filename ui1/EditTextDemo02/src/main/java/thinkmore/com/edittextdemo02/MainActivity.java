package thinkmore.com.edittextdemo02;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputAccountEdit, inputPwdEdit;

    private Drawable errorIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorIcon = getResources().getDrawable(android.R.drawable.ic_menu_call);
        // 初始化控件
        inputAccountEdit = (EditText) findViewById(R.id.inputAccount);
        inputPwdEdit = (EditText) findViewById(R.id.inputPassword);
        // 设置编辑动作监听(软键盘上面的回车按钮监听)
        inputAccountEdit.setOnEditorActionListener(onEditorActionListener);
        inputPwdEdit.setOnEditorActionListener(onEditorActionListener);
        // 添加字符改变监听
        inputAccountEdit.addTextChangedListener(textWatcher);
        inputPwdEdit.addTextChangedListener(textWatcher);
        // 设置输入框的焦点改变监听
        inputAccountEdit.setOnFocusChangeListener(onFocusChangeListener);
        inputPwdEdit.setOnFocusChangeListener(onFocusChangeListener);
    }

    // 输入框的编辑动作监听 方法或者属性的解释：ctrl+Q
    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override // 当点击回车按钮 1.当前触发此事件的输入框对象  2.回车键类型的id值
        // 返回true 表示拦截此监听，父类里面对它的操作将被拦截
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d("zhou", "onEditorAction: accountId:"+inputAccountEdit.getId()+",pwdId:"+inputPwdEdit.getId());
            Log.d("zhou", "onEditorAction: v.getId:"+v.getId()+",actionId:" +actionId);
            // 点击的是账号输入框里面的搜索键
            if(v.getId() == R.id.inputAccount && EditorInfo.IME_ACTION_SEARCH == actionId){
                inputPwdEdit.requestFocus(); // 请求焦点
                return true;
                // 点击的是密码框的发送键
            }else if(v.getId() == R.id.inputPassword && EditorInfo.IME_ACTION_SEND == actionId){
                String account = inputAccountEdit.getText().toString();
                String password = inputPwdEdit.getText().toString();
                Log.d("zhou", "onEditorAction: account:"+account+", password:"+password);
            }

            return false;
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override // 字符改变之前
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override // 字符改变
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override // 字符改变之后 1.s: 就是输入框里面的内容
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.length() >= 6){
                inputAccountEdit.setError("你输入的金额超过了限定值");
            }
        }
    };

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override // 1.当前视图对象 2.是否拥有焦点
        public void onFocusChange(View v, boolean hasFocus) {
            if(v.getId() == R.id.inputAccount && hasFocus == true){
                inputAccountEdit.setText("获取到焦点");
            }else if(v.getId() == R.id.inputAccount && hasFocus == false){
                inputAccountEdit.setText("失去焦点");
            }
        }
    };
}
