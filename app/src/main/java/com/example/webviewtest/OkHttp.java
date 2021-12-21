package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.http.Request;
import android.os.Bundle;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import com.example.webviewtest.R;


public class OkHttp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp);
        sendRequestWithOkHttp();//开启一个子线程
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request=new Request.Builder().url("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0").build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView responseText = (TextView) findViewById(R.id.response_text);
                responseText.setText(response);
            }
        });
    }
}