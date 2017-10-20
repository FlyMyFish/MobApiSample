package com.shichen.mobapisample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.utils.SharePreferenceUtils;
import com.shichen.mobapisample.weatherapi.IWeatherApi;

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
        setContentView(R.layout.activity_main);
        initCityData();
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

    public void testApi(View view){
        startActivity(new Intent(this,TestApiActivity.class));
    }

    public void weatherInfo(View view){
        startActivity(new Intent(this,WeatherInfoActivity.class));
    }

    private void initCityData(){
        showLoadingDialog(getString(R.string.init_city_list));
        final SharePreferenceUtils sharePreferenceUtils=new SharePreferenceUtils(getApplicationContext());
        getRetrofit().create(IWeatherApi.class)
                .getApiSupportCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SupportCity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(@NonNull SupportCity supportCity) {
                        sharePreferenceUtils.saveData(Config.CITY_LIST,supportCity.toString());
                        disMissLoadingDialog();
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
