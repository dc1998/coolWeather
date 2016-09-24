package com.example.administrator.coolweather2.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.administrator.coolweather2.activity.WeatherActivity;
import com.example.administrator.coolweather2.receiver.ClockReceiver;
import com.example.administrator.coolweather2.util.HttpCallbackListener;
import com.example.administrator.coolweather2.util.HttpUtil;
import com.example.administrator.coolweather2.util.Util;

/**
 * Created by Administrator on 2016/9/3.
 */
public class LongRunService extends Service {
    @Override
    public IBinder onBind(Intent intent){
        return null;

    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){


        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long triggerTime= SystemClock.elapsedRealtime()+8*60*60*1000;
        Intent intent1=new Intent(LongRunService.this,ClockReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(LongRunService.this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerTime,pendingIntent);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){

    }
}