package com.example.jiangmignzhi.coolweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiangmignzhi.coolweather.db.City;
import com.example.jiangmignzhi.coolweather.db.County;
import com.example.jiangmignzhi.coolweather.db.Province;
import com.example.jiangmignzhi.coolweather.util.HttpUtil;
import com.example.jiangmignzhi.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment{
    public static final int LEVEL_PROVINCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;
    private TextView title_text;
    private Button back_button;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> datalist=new ArrayList<>();
    private List<Province> provinceList;//省列表
    private List<City> cityList;//市列表
    private List<County> countyList;//县列表
    private Province selectedProvince;
    private City selectedCity;
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.choose_area,container,false);
        title_text=(TextView) view.findViewById(R.id.titil_text);
        back_button=(Button) view.findViewById(R.id.back);
        listView=(ListView) view.findViewById(R.id.list_view);
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,datalist);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel==LEVEL_PROVINCE){
                    selectedProvince=provinceList.get(i);
                    queryCity();
                }else if(currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(i);
                    queryCounty();
                }
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_COUNTY){
                    queryCity();
                }else if(currentLevel==LEVEL_CITY){
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    /*
    *查询全国所有的省，优先从数据库中查询，如果没有查询到再去服务器上查询
    * */
    private void queryProvinces(){
        title_text.setText("中国");
        back_button.setVisibility(View.GONE);
        provinceList= DataSupport.findAll(Province.class);
        if(provinceList.size()>0){
            datalist.clear();
            for(Province province:provinceList){
                datalist.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_PROVINCE;
        }else {
            String address="http://guolin.tech/api/china";
            queryFromServer(address,"province");
        }
    }

    /*
     *查询全省所有的市，优先从数据库中查询，如果没有查询到再去服务器上查询
     * */
    private void queryCity(){
        title_text.setText(selectedProvince.getProvinceName());
        back_button.setVisibility(View.VISIBLE);
        cityList=DataSupport.where("provinceid=?",String.valueOf(selectedProvince.getId())).find(City.class);
        if(cityList.size()>0){
            datalist.clear();
            for(City city:cityList){
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_CITY;
        }else {
            String address="http://guolin.tech/api/china/"+selectedProvince.getProvinceCode();
            queryFromServer(address,"city");
        }
    }

    /*
     *查询全市所有的县，优先从数据库中查询，如果没有查询到再去服务器上查询
     * */
    private void queryCounty(){
        title_text.setText(selectedCity.getCityName());
        back_button.setVisibility(View.VISIBLE);
        countyList=DataSupport.where("cityid=?",String.valueOf(selectedCity.getId())).find(County.class);
        if(countyList.size()>0){
            datalist.clear();
            for(County county:countyList){
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel=LEVEL_COUNTY;
        }else {
            String address="http://guolin.tech/api/china/"+selectedProvince.getProvinceCode()
                    +"/"+selectedCity.getCityCode();
            queryFromServer(address,"county");
        }
    }

    /*
    * 根据传入的地址和类型从服务器上查询省市县数据
    * */

    private void queryFromServer(String address,final String type ){
        showProgressDialog();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call,Response response) throws IOException{
                String responseText=response.body().string();
                boolean result=false;
                if("province".equals(type)){
                    result= Utility.handleProvinceResponse(responseText);
                }else if("city".equals(type)){
                    result=Utility.handleCityResponse(responseText,selectedProvince.getId());
                }else if("county".equals(type)){
                    result=Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("province".equals(type)){
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCity();
                            }else if("county".equals(type)){
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法会要主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*
    * 显示进度对话框
    * */
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /*
    * 关闭进度对话框
    * */
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
