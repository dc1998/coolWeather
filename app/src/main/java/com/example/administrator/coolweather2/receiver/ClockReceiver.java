package com.example.administrator.coolweather2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.administrator.coolweather2.service.LongRunService;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ClockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Intent i=new Intent(context,LongRunService.class);
        context.startService(i);
    }
}

