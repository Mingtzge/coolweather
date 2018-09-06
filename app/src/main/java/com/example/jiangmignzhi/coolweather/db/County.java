package com.example.jiangmignzhi.coolweather.db;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {
    private int id;
    private String cityName;
    private String weatherName;
    private int cityId;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }
}

