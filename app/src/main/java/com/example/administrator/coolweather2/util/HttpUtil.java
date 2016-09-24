package com.example.administrator.coolweather2.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/9/1.
 */
public class HttpUtil  {

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable(){
            @Override
            public void run(){
                HttpURLConnection connection=null;
                try{

                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        sb.append(line);

                    }

                        listener.onFinish(sb.toString());
                        Log.d("HttpURLConnection",sb.toString());


                }
                catch(Exception ex){

                        listener.onError(ex);

                }
                finally{

                        connection.disconnect();

                }
            }

        }).start();

    }
}

