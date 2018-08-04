package com.geiliweike.administrator.goodweather.gson;

/**
 * Created by Administrator on 2018/8/3.
 */

public class AQI {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
