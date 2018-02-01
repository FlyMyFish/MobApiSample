package com.shichen.mobapisample.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shichen.mobapisample.bean.AirQuality;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.bean.CookBookTab;
import com.shichen.mobapisample.cookbookpart.CookBookListAdapter;
import com.shichen.mobapisample.cookbookpart.CookBookMenuActivity;
import com.shichen.mobapisample.cookbookpart.CookBookMenuAdapter;
import com.shichen.mobapisample.cookbookpart.CookStepAdapter;
import com.shichen.mobapisample.weatherpart.CityListAdapter;
import com.shichen.mobapisample.weatherpart.DateQualityAdapter;
import com.shichen.mobapisample.weatherpart.DistrictListAdapter;
import com.shichen.mobapisample.weatherpart.HourQualityAdapter;
import com.shichen.mobapisample.weatherpart.PickTargetCityActivity;
import com.shichen.mobapisample.weatherpart.ProvinceListAdapter;
import com.shichen.mobapisample.weatherpart.WeatherInfoActivity;
import com.shichen.mobapisample.weatherpart.WeatherInfoAdapter;
import com.shichen.mobapisample.weatherview.WeatherImageSurfaceView;
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
    public static void setWeatherInfo(WeatherImageSurfaceView weatherView, WeatherInfo weatherInfo) {
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

    @BindingAdapter({"childsBeanX", "handler"})
    public static void setChildsBeanX(RecyclerView recyclerView, CookBookTab.CookBookBean.ChildsBeanX childsBeanX, CookBookMenuActivity.Handler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CookBookMenuAdapter(childsBeanX, recyclerView.getContext(), handler));
    }

    @BindingAdapter({"cookBook", "handler"})
    public static void setCookBook(RecyclerView recyclerView, CookBook cookBook, CookBookMenuActivity.Handler handler) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CookBookListAdapter(recyclerView.getContext(), cookBook, handler));
    }

    @BindingAdapter({"cookStep"})
    public static void setCookStep(RecyclerView recyclerView, CookBook.ResultBean.ListBean.RecipeBean recipeBean) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CookStepAdapter(recipeBean, recyclerView.getContext()));
    }
}
