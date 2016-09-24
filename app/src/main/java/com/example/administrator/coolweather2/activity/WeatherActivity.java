package com.example.administrator.coolweather2.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coolweather2.R;
import com.example.administrator.coolweather2.service.LongRunService;
import com.example.administrator.coolweather2.util.HttpCallbackListener;
import com.example.administrator.coolweather2.util.HttpUtil;
import com.example.administrator.coolweather2.util.Util;

/**
 * Created by Administrator on 2016/9/3.
 */
public class WeatherActivity extends Activity implements View.OnClickListener{
    TextView city;
    TextView weatherDay;
    TextView weatherNight;

    TextView date;
    TextView maxTmp;
    TextView minTmp;
    public String address;
    private String dataResponse;
    Button home;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
         city=(TextView)findViewById(R.id.title);
         weatherDay=(TextView)findViewById(R.id.weather);
         date=(TextView)findViewById(R.id.today_date);
         minTmp=(TextView)findViewById(R.id.min_temp);
         maxTmp=(TextView)findViewById(R.id.max_temp);
        Button home=(Button)findViewById(R.id.home);
        Button refresh=(Button)findViewById(R.id.refresh);

        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        Toast.makeText(WeatherActivity.this,id+"abcdefghijklmn", Toast.LENGTH_LONG).show();
        address="https://api.heweather.com/x3/weather?cityid="+id+"&key=644f6c595d1c47098b514d624b3fcc8d";

        HttpUtil.sendHttpRequest(address,new HttpCallbackListener(){
            @Override
            public void onFinish(String response){
                Util.handleWeatherResponse(WeatherActivity.this,response);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        showWeather();
                    }
                });
            }
            @Override
            public void onError(Exception ex){
            }
        });

        home.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                Intent intent=new Intent(WeatherActivity.this,ChooseAeraActivity.class);
                ChooseAeraActivity.currentLevel=0;
                startActivity(intent);
                finish();
                break;
            case R.id.refresh:
                Intent i=new Intent(WeatherActivity.this,LongRunService.class);
                startService(i);
                HttpUtil.sendHttpRequest(address,new HttpCallbackListener(){
                    @Override
                    public void onFinish(String response){
                        Util.handleWeatherResponse(WeatherActivity.this,response);
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                showWeather();
                            }
                        });
                    }
                    @Override
                    public void onError(Exception ex){
                    }
                });


            default:
                break;
        }
    }
    public void showWeather(){
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        city.setText(prefs.getString("city",""));
        weatherDay.setText(prefs.getString("weatherDay",""));
        date.setText(prefs.getString("data",""));
        maxTmp.setText(prefs.getString("maxTmp",""));
        minTmp.setText(prefs.getString("minTmp",""));

    }
}

