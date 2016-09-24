package com.example.administrator.coolweather2.model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.administrator.coolweather2.db.MyDatabaseHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class CoolWeatherDB {
    private static final String NAME="cool_weather";
    private static final int VERSION=1;
    private Context context;
    private static CoolWeatherDB coolWeatherDB;
    private static SQLiteDatabase db;
    private CoolWeatherDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,name,factory,version);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static CoolWeatherDB getDBInstance(Context context){
        if(coolWeatherDB==null)
            coolWeatherDB=new CoolWeatherDB(context,NAME,null,VERSION);
        return coolWeatherDB;
    }
    public void dropTable(){
        db.execSQL("DROP TABLE prov");
        db.execSQL("DROP TABLE city");
        db.execSQL("DROP TABLE country");
    }
    public void deleteTable(){
        db.delete("prov",null,null);
        db.delete("city",null,null);
        db.delete("country",null,null);
    }
    public void deleteTable1(){
        db.delete("prov",null,null);

    }
    public void saveCity(String cityName,String cityId,String prov){
        ContentValues values=new ContentValues();
        values.put("name",cityName);
        values.put("id",cityId);
        values.put("prov",prov);
        db.insert("city",null,values);
        /**changed prov*/
    }
    public void saveProvince(String cityName,String cityId){
        ContentValues values=new ContentValues();
        values.put("name",cityName);
        values.put("provId",cityId);
        db.insert("prov",null,values);
    }

    public void saveCountry(String countryName,String countryId,String provName,String sonId){
        ContentValues values=new ContentValues();
        values.put("name",countryName);
        values.put("countryId",countryId);
        values.put("provName",provName);
        values.put("sonId",sonId);
        db.insert("country",null,values);

    }
    public List<Province> loadProvince(){
        List<Province> provinceList=new ArrayList<Province>();
        Cursor cursor=db.rawQuery("select * from prov",null);
        if(cursor.moveToFirst()){
            do{
                Province province=new Province();
                province.provName=cursor.getString(1);
                province.provId=cursor.getString(2);
                provinceList.add(province);
                Log.d("CoolWeatherDB","loadProvince is been excute------------------------------");
            }while(cursor.moveToNext());}
        cursor.close();

        return provinceList;
    }

    public List<City.CityInfo> loadCity(String prov){

        List<City.CityInfo> cityList=new ArrayList<City.CityInfo>();
        Cursor cursor=db.rawQuery("select * from city where prov like ?",new String[] {String.valueOf(prov)});
        /** changed prov*/
        if(cursor.moveToFirst()){
            do{
                City.CityInfo newCity=new City.CityInfo();
                newCity.city=cursor.getString(1);
                newCity.id=cursor.getString(2);
                cityList.add(newCity);
                Log.d("CoolWeatherDB","new city added ---------------------------");
            }while(cursor.moveToNext());
            cursor.close();
        }
        return cityList;

    }
    public List<Country> loadCountry(String cityId){
        String newCityId=cityId.substring(0,10);
        List<Country> countryList=new ArrayList<Country>();
        Cursor cursor=db.rawQuery("select * from country where sonId like ?",new String[] {newCityId});
        if(cursor.moveToFirst()){
            do{
                Country country=new Country();
                country.countryName=cursor.getString(1);
                country.countryId=cursor.getString(2);
                countryList.add(country);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return countryList;
    }

}
