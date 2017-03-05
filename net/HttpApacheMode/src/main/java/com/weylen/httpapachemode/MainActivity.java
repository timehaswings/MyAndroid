package com.weylen.httpapachemode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Apache 6.0完全移除

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * get请求按钮点击
     * @param view
     */
    public void apacheClientGetRequest(View view){
        new ApacheClientGetRequest().start();
    }

    /**
     * Post请求按钮
     * @param view
     */
    public void apacheClientPostRequest(View view){
        new PostRequest().start();
    }

    private class ApacheClientGetRequest extends Thread{
        @Override
        public void run() {
            // 请求地址
            String requestUrl = "http://192.168.1.100:8080/webapps/login.action?account=admin&password=admin";
            // 1.创建连接对象
            HttpClient client = new DefaultHttpClient();
            // 2.创建请求方法对象 参数：请求地址
            HttpGet httpGet = new HttpGet(requestUrl);
            try {
                // 3.执行请求 得到服务器的响应对象
                HttpResponse resp = client.execute(httpGet);
                // 状态行  状态码
                int code = resp.getStatusLine().getStatusCode();
                Log.d("zhou", "run: code:"+code);
                if (code == HttpStatus.SC_OK){
                    HttpEntity entity = resp.getEntity(); // 得到响应的实体类
                    // InputStream inputStream = entity.getContent();
                    // ...
                    // 以平台默认编码进行转换
                    String result = EntityUtils.toString(entity, "GBK");
                    Log.d("zhou", "run: result:"+result);
                    // 以指定编码进行转换
//                    EntityUtils.toString(entity, "GBK");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("zhou", "run: e.message:"+e.getMessage());
            }
        }
    }

    private class PostRequest extends Thread{

        public HttpClient getClient(){
            HttpParams httpParams = new BasicHttpParams();
            httpParams.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
            httpParams.setParameter(HttpConnectionParams.SO_TIMEOUT, 10000);
            // 第二种设置参数方式就通过传递参数
            HttpClient client = new DefaultHttpClient(httpParams);
            // 第一种设置参数方式
//            client.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);
//            client.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 30000);
            return client;
        }

        @Override
        public void run() {
            String requestUri = "http://192.168.1.100:8080/webapps/login.action";
            // 1.创建Client对象
            HttpClient client = getClient();
            // 2.创建请求方法对象
            HttpPost post = new HttpPost(requestUri);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("account", "admin"));
            params.add(new BasicNameValuePair("password", "admin"));
            try {
                UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(params);
                post.setEntity(encodedFormEntity); // 设置向服务器写入的参数
                // 3.执行请求
                HttpResponse response = client.execute(post);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                    String result = EntityUtils.toString(response.getEntity(), "GBK");
                    Log.d("zhou", "run: result:"+result);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
