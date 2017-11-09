package com.shichen.mobapisample.weatherpart;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.TargetCity;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.config.Config;
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

public class WeatherInfoActivity extends BaseActivity {
    private ActivityWeatherInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_info);
        sharePreferenceUtils = new SharePreferenceUtils(getApplicationContext());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_pick_city:
                        startActivity(new Intent(WeatherInfoActivity.this, PickTargetCityActivity.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private SharePreferenceUtils sharePreferenceUtils;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String targetCityStr = sharePreferenceUtils.getData(Config.TARGET_CITY);
        if (!TextUtils.isEmpty(targetCityStr)) {
            TargetCity targetCity = mGson.fromJson(targetCityStr, TargetCity.class);
            binding.setCity(targetCity);
            getWeatherInfo(targetCity);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.kindly_reminder))
                    .setMessage(getString(R.string.need_pick_city))
                    .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(WeatherInfoActivity.this, PickTargetCityActivity.class));
                        }
                    })
                    .create().show();
        }
    }

    private void getWeatherInfo(TargetCity targetCity) {
        showLoadingDialog(getString(R.string.weather_info));
        getRetrofit().create(IWeatherApi.class)
                .getWeatherInfoByCityAndProvince(targetCity.getCity(), targetCity.getProvince())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull WeatherInfo weatherInfo) {
                        binding.setWeatherInfo(weatherInfo);
                        binding.setWeatherBean(weatherInfo.getResult().get(0));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather_info, menu);
        return true;
    }
}
