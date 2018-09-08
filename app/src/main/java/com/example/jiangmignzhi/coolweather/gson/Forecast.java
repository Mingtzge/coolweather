package com.example.jiangmignzhi.coolweather.gson;

/*
* 未来天气的预测
*"daily_forecast":[{"date":"2018-09-06","cond":{"txt_d":"多云"},"tmp":{"max":"34","min":"23"}},
* {"date":"2018-09-07","cond":{"txt_d":"阵雨"},"tmp":{"max":"26","min":"21"}},
* {"date":"2018-09-08","cond":{"txt_d":"多云"},"tmp":{"max":"26","min":"20"}}]
* */

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String date;

    @SerializedName("cond")
    public WeatherDis weatherDes;
    public class WeatherDis{
        @SerializedName("txt_d")
        public String info;
    }

    @SerializedName("tmp")
    public Temperature temperature;
    public class Temperature{
        public String max;
        public String min;
    }
}
