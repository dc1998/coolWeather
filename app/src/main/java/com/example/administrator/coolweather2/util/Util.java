package com.example.administrator.coolweather2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.coolweather2.model.City;
import com.example.administrator.coolweather2.model.CoolWeatherDB;
import com.example.administrator.coolweather2.model.WeatherInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Util {
    static  String address="https://api.heweather.com/x3/citylist?search=allchina&key=644f6c595d1c47098b514d624b3fcc8d";
    static final int SHOW_RESPONSE=1;
    public static synchronized void handleCountry(CoolWeatherDB coolWeatherDB,String jsonResponse) {
int x=0;
        Gson gson=new Gson();
        Type type=new TypeToken<City>(){}.getType();
        City city=gson.fromJson(jsonResponse,type);
        List<City.CityInfo> cityInfo=city.getCityInfo();
        for (City.CityInfo element:cityInfo) {
            String countryName=element.getCity();
            String countryId=element.getId();
            String provName=element.getProv();
            String sonId=(element.getId()).substring(0,10);
            coolWeatherDB.saveCountry(countryName,countryId,provName,sonId);
            Log.d("Util","saveCountry is excute ----------------------------------"+x++);
    }}
    public synchronized static void handleCity(CoolWeatherDB coolWeatherDB,String jsonResponse) {
      try {
          int x=0;
          JSONObject jsonObject=new JSONObject(jsonResponse);
          JSONArray cityInfo=jsonObject.getJSONArray("city_info");
          String ab="01";
          for (int i=0;i<cityInfo.length();i++) {
              String id = cityInfo.getJSONObject(i).getString("id");
              String name = cityInfo.getJSONObject(i).getString("city");
              String prov = cityInfo.getJSONObject(i).getString("prov");
              if (id.regionMatches(9, ab, 0, 2)) {
                  coolWeatherDB.saveCity(name, id,prov);
                  Log.d("Util","saveCity is excute ----------------------------------"+x++);
              } else
                  continue;
          }
      }
      catch(Exception ex){
          Log.d("Util",ex.toString());
      }
    }
    public synchronized static void  handleProvince(CoolWeatherDB coolWeatherDB,String jsonResponse){
        try{JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray cityInfo=jsonObject.getJSONArray("city_info");
            String ab="0101";
            for(int i=0;i<cityInfo.length();i++){
                String id=cityInfo.getJSONObject(i).getString("id");
                String name=cityInfo.getJSONObject(i).getString("prov");
                if(id.regionMatches(7,ab,0,4)){
                    coolWeatherDB.saveProvince(name,id);
                    Log.d("Util","saveProvince is excute ----------------------------------");
                }
                else
                    continue;
            }
        }
        catch (Exception ex){
            Log.d("Util","handleover province is NOT excute-----------------------------");
        }
    }

    public static void handleWeatherResponse(Context context,String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray heWeatherData = jsonObject.getJSONArray("HeWeather data service 3.0");
            JSONObject basic = heWeatherData.getJSONObject(0).getJSONObject("basic");
            String city = basic.getString("city");
            JSONArray dailyForecast = heWeatherData.getJSONObject(0).getJSONArray("daily_forecast");
            String date = dailyForecast.getJSONObject(0).getString("date");
            JSONObject cond = dailyForecast.getJSONObject(0).getJSONObject("cond");
            String weatherDay = cond.getString("txt_d");
            String weatherNight = cond.getString("txt_n");
            JSONObject tmp = dailyForecast.getJSONObject(0).getJSONObject("tmp");
            String maxTmp = tmp.getString("max");
            String minTmp = tmp.getString("min");
            saveWeatherInfo(context, city, weatherDay, weatherNight, date, maxTmp, minTmp);


        } catch (Exception ex) {

        }
    }
        public static void saveWeatherInfo(Context context,String city,String weatherDay,String weatherNight,String data,String maxTmp,String minTmp){
            SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString("city",city);
            editor.putString("weatherDay",weatherDay);
            editor.putString("weatherNight",weatherNight);
            editor.putString("data",data);
            editor.putString("maxTmp",maxTmp);
            editor.putString("minTmp",minTmp);
            editor.commit();
            Log.d("Util","saveWeathaerInfo is been execut------------------------------------------------------");
        }




       /** Gson gson=new Gson();
        Type type=new TypeToken<WeatherInfo>(){}.getType();
        WeatherInfo weatherInfo=gson.fromJson(response,type);
        List<WeatherInfo.HeWeather> heWeather=weatherInfo.getHeWeather();
        WeatherInfo.HeWeather.Basic basic=heWeather.get(0).getBasic();
        String city=basic.getCity();
        String id=basic.getId();

        List<WeatherInfo.HeWeather.Daily_Forecast> daily_forecast=heWeather.get(0).getDaily_forecast();
        String date=daily_forecast.get(0).getDate();
        WeatherInfo.HeWeather.Daily_Forecast.Cond cond=daily_forecast.get(0).getCond();
        String weatherDay=cond.txt_d;
        String weatherNight=cond.txt_d;

        WeatherInfo.HeWeather.Daily_Forecast.Tmp tmp=daily_forecast.get(0).getTmp();
        String maxTmp=tmp.getMax();
        String minTmp=tmp.getMin();
        saveWeatherInfo(context,city,weatherDay,weatherNight,date,maxTmp,minTmp);
    }
        */





}
