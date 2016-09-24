package com.example.administrator.coolweather2.util;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception ex);
}
