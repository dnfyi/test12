package com.example.webviewtest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class WeatherHttpClient extends AppCompatActivity {
    public static final int SHOW_RESPONSE = 0;
    private Button button_sendRequest;
    private TextView textView_response;

    //新建Handler的对象，在这里接受Message，然后更新TextView控件的内容
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case SHOW_RESPONSE:
                    String response = (String) message.obj;
                   textView_response.setText(response);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weaher_httpclient);
        textView_response = (TextView) findViewById(R.id.TextView1);
        button_sendRequest=(Button) findViewById(R.id.button1);
        button_sendRequest.setOnClickListener(new OnClickListener() {
            //点击按钮时，执行sendRequestWithHttpClient()方法里面的线程
            @Override
            public void onClick(View v){
                sendRequestWithHttpClient();
            }



        });

    }

    //方法：发送网络请求，获取网页数据，在里面开启线程
    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {//开启线程来发起网络请求
            @Override
            public void run() {
                //创建HttpClient对象
                HttpClient httpClient = new DefaultHttpClient();
                //创建代表请求的对象，参数是访问的服务器地址
                HttpGet httpGet = new HttpGet("http://php.weather.sina.com.cn/xml.php?city=%B1%B1%BE%A9&password=DJOYnieT8234jlsK&day=0");
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");//将entity当中的数据转换为字符串
                        //在子线程中将Message对象发出去
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        message.obj = response.toString();
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
}





