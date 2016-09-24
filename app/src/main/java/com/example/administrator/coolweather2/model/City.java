package com.example.administrator.coolweather2.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class City {
    private List<CityInfo> city_info;

    public List<CityInfo> getCityInfo(){
        return city_info;
    }
    public void setCityInfo(List<CityInfo> city_info){
        this.city_info=city_info;
    }

    public static class CityInfo  {

        public String city;
        public String id;
        public String prov;
        public String provId;
        public String cityId;

        public String getCityId(){
            return cityId;
        }
        public void setCityId(String cityId){
            this.cityId=cityId;
        }

        public String getProvId(){
            return provId;
        }
        public void setProvId(String provId){
            this.provId=provId;

        }


        public String getCity() {
            return city;
        }

        public String getId() {
            return id;
        }

        public String getProv() {
            return prov;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setId(int id) {

        }

        public void setProv(String prov) {
            this.prov = prov;
        }



    }
}
