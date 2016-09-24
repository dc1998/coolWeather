package com.example.administrator.coolweather2.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coolweather2.R;
import com.example.administrator.coolweather2.db.MyDatabaseHelper;
import com.example.administrator.coolweather2.model.City;
import com.example.administrator.coolweather2.model.CoolWeatherDB;
import com.example.administrator.coolweather2.model.Country;
import com.example.administrator.coolweather2.model.Province;
import com.example.administrator.coolweather2.util.HttpCallbackListener;
import com.example.administrator.coolweather2.util.HttpUtil;
import com.example.administrator.coolweather2.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class ChooseAeraActivity extends Activity {
    private List<String> dataList=new ArrayList<String>();
    final int COUNTRY_LEVEL=1;
    final int CITY_LEVEL=2;
    final int PROVINCE_LEVEL=3;

    private  List<Country> countryList;
    private   List<City.CityInfo> cityList;
    private List<Province> provinceList;
    public static int currentLevel;
    private ListView listView;
    boolean isProvinceSaved=false;
    boolean isCitySaved=false;
    boolean isCountrySaved=false;

    private CoolWeatherDB coolWeatherDB;
    private ArrayAdapter<String> adapter;
    private TextView titleText;

    Province selectedProvince;
    City.CityInfo selectedCity;
    Country selectedCountry;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choosearea_layout);
        coolWeatherDB=CoolWeatherDB.getDBInstance(ChooseAeraActivity.this);
        titleText=(TextView)findViewById(R.id.chooseText_view);
        listView=(ListView)findViewById(R.id.chooseList_view);
        coolWeatherDB.deleteTable1();
        QueryServer(PROVINCE_LEVEL);




        adapter=new ArrayAdapter<String>(ChooseAeraActivity.this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == PROVINCE_LEVEL) {
                    selectedProvince = provinceList.get(position);
                    Log.d("ChooseAeraActivity",selectedProvince.toString()+"——————————————————————————");
                   QueryCity();
                } else if (currentLevel == CITY_LEVEL) {
                    selectedCity = cityList.get(position);
                    Log.d("ChooseAeraActivity",selectedCity.toString()+"——————————————————————————");
                    QueryCountry();
                }
                else if (currentLevel==COUNTRY_LEVEL){

                    selectedCountry=countryList.get(position);

                 Intent intent=new Intent(ChooseAeraActivity.this,WeatherActivity.class);
                    intent.putExtra("id",selectedCountry.getCountryId());
                    startActivity(intent);
                }
                else {
                    QueryProvince();

                }
            }
        });}

    private void QueryProvince(){

        provinceList=coolWeatherDB.loadProvince();

        if(provinceList.size()>0){
            dataList.clear();
            for(Province province:provinceList){
                dataList.add(province.getProvName());
            }
            currentLevel=PROVINCE_LEVEL;
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
        }
        else
            QueryServer(PROVINCE_LEVEL);
    }
    private void QueryCity() {
        cityList = coolWeatherDB.loadCity(String.valueOf(selectedProvince.getProvName()));
        if (cityList.size() > 0) {
            dataList.clear();
            for(City.CityInfo cityInfo:cityList){
                dataList.add(cityInfo.getCity());
            }
            currentLevel=CITY_LEVEL;
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            String title=selectedProvince.getProvName();
            titleText.setText(title);
        }
        else{
            QueryServer(CITY_LEVEL);
        }
    }
    private void QueryCountry(){
        countryList=coolWeatherDB.loadCountry(selectedCity.getId());
        if(countryList.size()>0){
            dataList.clear();

            for(Country country:countryList){
                dataList.add(country.getCountryName());
            }
            currentLevel=COUNTRY_LEVEL;
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            String title=selectedCity.getCity();
            titleText.setText(title);
        }
        else{
            QueryServer(COUNTRY_LEVEL);
        }
    }
    public void QueryServer(final int type){
        String address="https://api.heweather.com/x3/citylist?search=allchina&key=644f6c595d1c47098b514d624b3fcc8d";
        HttpUtil.sendHttpRequest(address,new HttpCallbackListener(){
            @Override
            public void onFinish(String response){

                if(type==PROVINCE_LEVEL){
                    Util.handleProvince(coolWeatherDB,response);
                    Log.d("ChooseAeraActivity","QueryServer(PROVINCE_LEVEL) is been excute");
                }
                else if(type==CITY_LEVEL){
                    Util.handleCity(coolWeatherDB,response);
                    Log.d("ChooseAeraActivity","QueryServer(CITY_LEVEL) is been excute");
                }
                else if(type==COUNTRY_LEVEL){
                    Util.handleCountry(coolWeatherDB,response);
                    Log.d("ChooseAeraActivity","QueryServer(COUNTRY_LEVEL) is been excute");

                }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (type == PROVINCE_LEVEL) {
                                currentLevel = PROVINCE_LEVEL;
                                QueryProvince();

                                Log.d("ChooseAeraActivity","QueryProvince in runOnUiThread is been excute----------------");
                            } else if (type == CITY_LEVEL) {
                                currentLevel = CITY_LEVEL;
                                QueryCity();
                                Log.d("ChooseAeraActivity","QueryCity in runOnUiThread is been excute----------------");

                            } else if (type == COUNTRY_LEVEL) {
                                currentLevel=COUNTRY_LEVEL;
                                QueryCountry();
                                Log.d("ChooseAeraActivity","QueryCountry in runOnUiThread is been excute----------------");
                            }
                        }
                    });

            }

            @Override
            public void onError(Exception ex){
                runOnUiThread(new Runnable(){
                    public void run(){
                        closeProgressDialog();
                        Toast.makeText(ChooseAeraActivity.this,"加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("request from Server...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed(){
        if (currentLevel==COUNTRY_LEVEL){
            QueryCity();
        }
        else if(currentLevel==CITY_LEVEL){
            QueryProvince();
        }
        else{
            finish();
        }
    }
}



