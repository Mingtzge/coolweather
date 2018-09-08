package com.example.jiangmignzhi.coolweather.gson;

/*
* 空气质量情况
* "aqi":{"city":{"aqi":"107","pm25":"80","qlty":"轻度污染"}}
* */

public class AQI {
    public AQIcity city;
    public class AQIcity{
        public String aqi;
        public String pm25;
        public String qlty;
    }
}

