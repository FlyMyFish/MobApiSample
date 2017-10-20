package com.shichen.mobapisample;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.shichen.mobapisample.bean.TargetCity;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.databinding.ActivityWeatherInfoBinding;
import com.shichen.mobapisample.utils.SharePreferenceUtils;
import com.shichen.mobapisample.weatherapi.IWeatherApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherInfoActivity extends BaseActivity{
    private ActivityWeatherInfoBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_weather_info);
        sharePreferenceUtils=new SharePreferenceUtils(getApplicationContext());
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private SharePreferenceUtils sharePreferenceUtils;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        String targetCityStr=sharePreferenceUtils.getData(Config.TARGET_CITY);
        if (!TextUtils.isEmpty(targetCityStr)){
            TargetCity targetCity=mGson.fromJson(targetCityStr,TargetCity.class);
            binding.setCity(targetCity);
            getWeatherInfo(targetCity);
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.kindly_reminder))
                    .setMessage(getString(R.string.need_pick_city))
                    .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(WeatherInfoActivity.this,PickTargetCityActivity.class));
                        }
                    })
                    .create().show();
        }
    }

    private void getWeatherInfo(TargetCity targetCity){
        showLoadingDialog(getString(R.string.weather_info));
        getRetrofit().create(IWeatherApi.class)
                .getWeatherInfoByCityAndProvince(targetCity.getCity(),targetCity.getProvince())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WeatherInfo weatherInfo) {
                        binding.setWeatherInfo(weatherInfo.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        disMissLoadingDialog();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        disMissLoadingDialog();
                    }
                });
    }
}
