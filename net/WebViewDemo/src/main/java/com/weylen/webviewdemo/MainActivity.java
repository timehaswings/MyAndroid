package com.weylen.webviewdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    // WebView 用来加载网页 webkit

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        // 添加一个js接口 1、接口对象 2、接口对象的名字
        webView.addJavascriptInterface(new OnJSCount(), "demo");

        WebSettings settings = webView.getSettings(); // 得到WebSettings
        // 设置JS是否可以自动打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置JS可用
        settings.setJavaScriptEnabled(true);
        // 是否显示缩放按钮
        settings.setDisplayZoomControls(true);
        // 设置是否自动加载图片
        settings.setLoadsImagesAutomatically(true);

        webView.loadUrl("file:///android_asset/html/test.html");

        // 设置WebView加载的回调
        webView.setWebViewClient(webViewClient);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override // 在JS弹窗的时候响应
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Log.d("zhou", "onJsAlert: message："+message);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage(message)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        }).show();
                return true; // true表示此client接收对话框(不默认显示对话框) false不接收
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d("zhou", "onDownloadStart: 下载地址："+url);
            }
        });
    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override // 页面加载完成
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override // 页面开始加载
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override // 跳转
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("zhou", "shouldOverrideUrlLoading: url:"+url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    private Handler handler = new Handler();

    private class OnJSCount{
        @android.webkit.JavascriptInterface
        public void count(int num1, int num2){
            Log.d("zhou", "count: num1:"+num1 +",num2:"+num2);
            final int result = num1 + num2;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:result("+result+")");
                }
            });
        }
    }
}
