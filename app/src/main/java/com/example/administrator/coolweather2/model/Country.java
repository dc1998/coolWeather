package com.example.administrator.coolweather2.model;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Country {
    public String countryName;
    public String countryId;
    public String cityId;

    public String getCityId(){
        return cityId;
    }
    public void setCityId(String cityId){
        this.cityId=cityId;


    }

    public void setCountryName(String countryName){
        this.countryName=countryName;
    }
    public void setCountryId(String countryId){
        this.countryId=countryId;
    }

    public String getCountryName(){
        return countryName;
    }
    public String getCountryId(){
        return countryId;
    }

}
