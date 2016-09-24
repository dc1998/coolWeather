package com.example.administrator.coolweather2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class WeatherInfo {
    @SerializedName("HeWeather data service 3.0")
    private List<HeWeather> heWeather;
    public void setHeWeather(List<HeWeather> heWeather){
        this.heWeather=heWeather;

    }
    public List<HeWeather> getHeWeather(){
        return heWeather;
    }
    public static class HeWeather{
        private Basic basic;
        public void setBasic(Basic basic){
            this.basic=basic;
        }
        public Basic getBasic(){
            return basic;
        }
        public static class Basic{
            private String city;
            public void setCity(String city){
                this.city=city;
            }
            public String getCity(){
                return city;
            }
            private String id;
            public void setId(String id){
                this.id=id;
            }
            public String getId(){
                return id;
            }
        }
        private List<Daily_Forecast> daily_forecast;
        public void setDaily_forecast(List<Daily_Forecast> daily_forecast){
            this.daily_forecast=daily_forecast;
        }
        public List<Daily_Forecast> getDaily_forecast(){
            return daily_forecast;
        }
        public static class Daily_Forecast{
            private String date;
            public void setDate(String date){
                this.date=date;
            }
            public String getDate(){
                return date;
            }
            private  Cond cond;
            public void setCond(Cond cond){
                this.cond=cond;

            }
            public Cond getCond(){
                return cond;
            }
            public static class Cond{
                public String txt_d;
                public String txt_n;
            }
            public Tmp tmp;
            public void setTmp(Tmp tmp){
                this.tmp=tmp;
            }
            public Tmp getTmp(){
                return tmp;
            }
            public static class Tmp{
                public String max;
                public void setMax(String max){
                    this.max=max;
                }
                public String getMax(){
                    return max;
                }
                public String min;
                public void setMin(String min){
                    this.min=min;
                }
                public String getMin(){
                    return min;
                }
            }
        }

    }

}