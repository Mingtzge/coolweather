package com.example.jiangmignzhi.coolweather.util;

import android.text.TextUtils;

import com.example.jiangmignzhi.coolweather.db.City;
import com.example.jiangmignzhi.coolweather.db.Province;
import com.example.jiangmignzhi.coolweather.db.County;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /*
     *解析和处理服务器传回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvince=new JSONArray(response);
                for(int i=0;i<allProvince.length();i++){
                    JSONObject provinceObject=allProvince.getJSONObject(i);
                    Province province=new Province();
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

    /*
     *解析和处理服务器传回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allcity=new JSONArray(response);
                for(int i=0;i<allcity.length();i++){
                    JSONObject cityObject=allcity.getJSONObject(i);
                    City city=new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /*
     *解析和处理服务器传回的县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allcounty=new JSONArray(response);
                for(int i=0;i<allcounty.length();i++){
                    JSONObject countyObject=allcounty.getJSONObject(i);
                    County county=new County();
                    county.setWeatherID(countyObject.getString("weather_id"));
                    county.setCityName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
