package com.shichen.mobapisample.weatherpart;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.AirQuality;
import com.shichen.mobapisample.bean.TargetCity;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.config.Config;
import com.shichen.mobapisample.databinding.ActivityWeatherInfoBinding;
import com.shichen.mobapisample.utils.SharePreferenceUtils;
import com.shichen.mobapisample.weatherapi.IAirQualityApi;
import com.shichen.mobapisample.weatherapi.IWeatherApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherInfoActivity extends BaseActivity implements SensorEventListener {
    private ActivityWeatherInfoBinding binding;
    private SensorManager sensorManager;

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
        binding.setHandler(new Handler());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    private SharePreferenceUtils sharePreferenceUtils;

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
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
                        disposableList.add(d);
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

    public class Handler {
        public void airConditionClick(View view) {
            getAirCondition(view);
        }
    }

    private void getAirCondition(final View view) {
        String targetCityStr = sharePreferenceUtils.getData(Config.TARGET_CITY);
        if (!TextUtils.isEmpty(targetCityStr)) {
            showLoadingDialog("获取空气质量信息");
            TargetCity targetCity = mGson.fromJson(targetCityStr, TargetCity.class);
            new Retrofit.Builder()
                    .baseUrl("http://apicloud.mob.com/environment/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(IAirQualityApi.class)
                    .getAirQuality(targetCity.getCity(), targetCity.getProvince())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<AirQuality>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposableList.add(d);
                        }

                        @Override
                        public void onNext(AirQuality airQuality) {
                            Intent intent = new Intent(WeatherInfoActivity.this, AirConditionActivity.class);
                            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(WeatherInfoActivity.this
                                    ,new Pair<>(view,"tv_air_condition_"));
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(AirConditionActivity.AIR_QUALITY_DATA, airQuality);
                            intent.putExtras(bundle);
                            startActivity(intent,activityOptionsCompat.toBundle());
                            //tv_air_condition
                        }

                        @Override
                        public void onError(Throwable e) {
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

    /**
     * 定义水平仪能处理的最大倾斜角，超过该角度，直接位于边界。
     */
    private final static int MAX_ANGLE = 60;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // 获取触发event的传感器类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                // 获取与Y轴的夹角
                float yAngle = values[1];
                // 获取与Z轴的夹角
                float zAngle = values[2];
                // 如果与Z轴的倾斜角还在最大角度之内
                if (Math.abs(zAngle) <= MAX_ANGLE) {
                    int scrollX = (int) (50 * zAngle / MAX_ANGLE);
                    binding.wvMain.setParentTransitionX(scrollX);
                } else if (zAngle > MAX_ANGLE) {
                    binding.wvMain.scrollTo(50, 0);
                }
                if (Math.abs(yAngle) <= MAX_ANGLE) {
                    int scrollY = (int) (50 * yAngle / MAX_ANGLE);
                    binding.wvMain.setParentTransitionY(scrollY);
                } else if (yAngle > MAX_ANGLE) {
                    binding.wvMain.scrollTo(0, 50);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
