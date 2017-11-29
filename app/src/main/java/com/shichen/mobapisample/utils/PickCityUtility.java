package com.shichen.mobapisample.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shichen.mobapisample.bean.AirQuality;
import com.shichen.mobapisample.weatherpart.CityListAdapter;
import com.shichen.mobapisample.weatherpart.DateQualityAdapter;
import com.shichen.mobapisample.weatherpart.DistrictListAdapter;
import com.shichen.mobapisample.weatherpart.HourQualityAdapter;
import com.shichen.mobapisample.weatherpart.PickTargetCityActivity;
import com.shichen.mobapisample.weatherpart.ProvinceListAdapter;
import com.shichen.mobapisample.weatherpart.WeatherInfoActivity;
import com.shichen.mobapisample.weatherpart.WeatherInfoAdapter;
import com.shichen.mobapisample.weatherview.WeatherImageView;
import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.bean.WeatherInfo;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 * RecyclerView使用DataBinding时所需要的自定义Binding
 * 自定义View使用DataBinding时所需要的自定义Binding
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
    public static void setWeatherInfo(WeatherImageView weatherView, WeatherInfo weatherInfo) {
        weatherView.setWeatherInfo(weatherInfo);
    }

    @BindingAdapter({"weatherBean", "handler"})
    public static void setWeatherBean(RecyclerView recyclerView, WeatherInfo.WeatherBean weatherBean, WeatherInfoActivity.Handler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new WeatherInfoAdapter(recyclerView.getContext(), weatherBean, handler));
    }

    @BindingAdapter({"hourData"})
    public static void setHourData(RecyclerView recyclerView, AirQuality.AirData airQuality) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new HourQualityAdapter(recyclerView.getContext(), airQuality));
    }

    @BindingAdapter({"futureData"})
    public static void setFutureData(RecyclerView recyclerView, AirQuality.AirData airQuality) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new DateQualityAdapter(recyclerView.getContext(), airQuality));
    }
}
