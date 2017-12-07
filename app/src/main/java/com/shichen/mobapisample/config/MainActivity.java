package com.shichen.mobapisample.config;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.utils.SharePreferenceUtils;
import com.shichen.mobapisample.weatherapi.IWeatherApi;
import com.shichen.mobapisample.weatherpart.WeatherInfoActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author shichen 754314442@qq.com
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCityData();
    }

    /**
     * 初始化省市县信息
     */
    private void initCityData() {
        final SharePreferenceUtils sharePreferenceUtils = new SharePreferenceUtils(getApplicationContext());
        getWeatherRetrofit().create(IWeatherApi.class)
                .getApiSupportCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SupportCity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(@NonNull SupportCity supportCity) {
                        sharePreferenceUtils.saveData(Config.CITY_LIST, supportCity.toString());
                        startActivity(new Intent(MainActivity.this, WeatherInfoActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        disMissLoadingDialog();
                    }
                });
    }
}
