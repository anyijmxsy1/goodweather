package com.geiliweike.administrator.goodweather.util;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.geiliweike.administrator.goodweather.LogUtil;
import com.geiliweike.administrator.goodweather.MyApplication;
import com.geiliweike.administrator.goodweather.db.City;
import com.geiliweike.administrator.goodweather.db.County;
import com.geiliweike.administrator.goodweather.db.Province;
import com.geiliweike.administrator.goodweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/7/31.
 */
public class Utility {

           /**
         * 解析和处理服务器返回的省级数据
         */
        public static boolean handleProvinceResponse(String response){
            if (!TextUtils.isEmpty(response)){
                try {
                    JSONArray allProvinces = new JSONArray(response);
                    for (int i = 0; i < allProvinces.length(); i++){
                        JSONObject provinceObject = allProvinces.getJSONObject(i);
                        Province province = new Province();
                        province.setProvinceName(provinceObject.getString("name"));
                        province.setProvinceCode(provinceObject.getInt("id"));
                        province.save();
                    }

                    return true;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return false;
        }

        /**
         * 解析和处理服务器返回的市级数据
         */
        public static boolean handleCityResponse(String response,int provinceId){
            if (! TextUtils.isEmpty(response)){
                try {
                    JSONArray allCitis = new JSONArray(response);
                    for (int i = 0; i < allCitis.length(); i++){
                        JSONObject cityObject = allCitis.getJSONObject(i);
                        City city = new City();
                        city.setCityName(cityObject.getString("name"));
                        city.setCityCode(cityObject.getInt("id"));
                        city.setProvinceId(provinceId);
                        city.save();
                    }
                    LogUtil.i("TAG","handlecityresponse()"+allCitis.getJSONObject(1));
                    LogUtil.i("TAG","handlecityresponse()"+allCitis.getJSONObject(1).getString("name"));
                    return true;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return false;
        }

        /**
         * 解析与处理服务返回的县级数据
         */
        public static boolean handleCountyResponse(String response,int cityId){
            if (! TextUtils.isEmpty(response)){
                try {
                    JSONArray allCounties = new JSONArray(response);
                    for (int i = 0; i < allCounties.length(); i++){
                        JSONObject countyObject = allCounties.getJSONObject(i);
                        County county = new County();
                        county.setCountyName(countyObject.getString("name"));
                        county.setWeatherId(countyObject.getString("weather_id"));
                        county.setCityId(cityId);
                        county.save();
                    }
                    LogUtil.i("TAG","handleCountyresponse()"+allCounties.getJSONObject(1));
                    return true;
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return false;
        }

    /**
     * 将返回的JSON数据解析成WEATHER实体类
     */
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            LogUtil.i("TAG","Utility.handleWeatherResponse(response):"+jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            LogUtil.i("TAG","WeatherActivity():jsonArray"+jsonArray);
            String weatherContent = jsonArray.getJSONObject(0).toString();
            LogUtil.i("TAG","WeatherActivity():weatherContent"+weatherContent);
            return new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
