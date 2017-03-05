package com.weylen.autocompleteviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    // AutoCompleteTextView 自动完成视图
    // MultiAutoCompleteTextView  多次自动完成

    private AutoCompleteTextView autoCompleteTextView;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.text1);
        String[] strs = {"1234", "123", "12", "234", "34"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                strs
                );
        // 设置的适配器就是提示的选项
        autoCompleteTextView.setAdapter(arrayAdapter);
        // 设置自动完成提示的字符个数
        autoCompleteTextView.setThreshold(1);

        multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.text2);
        multiAutoCompleteTextView.setAdapter(arrayAdapter);
        // 必须要设置分词符 逗号分词符
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
//        multiAutoCompleteTextView.setTokenizer(new CustomTokenizer());
    }


    public static class CustomTokenizer implements MultiAutoCompleteTextView.Tokenizer {
        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != ';') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }

            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == ';') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && text.charAt(i - 1) == ';') {
                return text;
            } else {
                if (text instanceof Spanned) {
                    SpannableString sp = new SpannableString(text + "; ");
                    TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                            Object.class, sp, 0);
                    return sp;
                } else {
                    return text + "; ";
                }
            }
        }
    }
}
