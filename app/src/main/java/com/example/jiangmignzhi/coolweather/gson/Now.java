package com.example.jiangmignzhi.coolweather.gson;

/*
* 当前的天气质量情况
* "now":{"cloud":"74","cond_code":"104","cond_txt":"阴","fl":"28","hum":"69","pcpn":"0.0",
* "pres":"1004","tmp":"26","vis":"6","wind_deg":"263","wind_dir":"西风","wind_sc":"2",
* "wind_spd":"7","cond":{"code":"104","txt":"阴"}}
* */

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("cond_txt")//将JSON字段与java字段建立映射关系
    public String weatherDescription;

    @SerializedName("tmp")
    public String temperature;

    public String wind_dir;

}
