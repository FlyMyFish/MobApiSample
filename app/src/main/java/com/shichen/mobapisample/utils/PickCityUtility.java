package com.shichen.mobapisample.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shichen.mobapisample.CityListAdapter;
import com.shichen.mobapisample.DistrictListAdapter;
import com.shichen.mobapisample.PickTargetCityActivity;
import com.shichen.mobapisample.ProvinceListAdapter;
import com.shichen.mobapisample.WeatherView;
import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.bean.WeatherInfo;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class PickCityUtility {
    @BindingAdapter({"provinceList", "handler"})
    public static void setProvinceList(RecyclerView recyclerView, List<SupportCity.ResultBean> provinceList, PickTargetCityActivity.PickTargetCityHandler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new ProvinceListAdapter(provinceList, recyclerView.getContext(), handler));
    }

    @BindingAdapter({"cityList", "handler"})
    public static void setCityList(RecyclerView recyclerView, List<SupportCity.ResultBean.CityBean> cityList, PickTargetCityActivity.PickTargetCityHandler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CityListAdapter(cityList, recyclerView.getContext(), handler));
    }

    @BindingAdapter({"districtList", "handler"})
    public static void setDistrictList(RecyclerView recyclerView, List<SupportCity.ResultBean.CityBean.DistrictBean> districtList, PickTargetCityActivity.PickTargetCityHandler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DistrictListAdapter(districtList, recyclerView.getContext(), handler));
    }

    @BindingAdapter({"weatherInfo"})
    public static void setWeatherInfo(WeatherView weatherView, WeatherInfo weatherInfo) {
        weatherView.setWeatherInfo(weatherInfo);
    }
}
