package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.bean.WeatherType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shichen on 2017/10/26.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherView extends FrameLayout {
    private SunView sunView;
    private CloudView cloudView;

    public WeatherView(@NonNull Context context) {
        this(context, null);
    }

    public WeatherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        weatherConfig = new WeatherConfig();
        sunView = new SunView(getContext());
        ViewGroup.LayoutParams sunLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        sunView.setLayoutParams(sunLp);
        cloudView = new CloudView(getContext());
        ViewGroup.LayoutParams cloudLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cloudView.setLayoutParams(cloudLp);
        Log.d("WeatherView", "CreateWeatherView");
    }

    private void addWeatherPart() {
        if (weatherInfo == null) {
            return;
        }
        Log.d("WeatherView", "addWeatherPart");
        if (sunView != null) {
            sunView.clearAnimation();
        }
        if (cloudView != null) {
            cloudView.clearAnimation();
        }
        removeAllViews();
        Log.d("WeatherView", "ChildViewCount=" + getChildCount());
        if (weatherConfig.haveRain) {
            addRainView();
        }
        if (weatherConfig.haveSnow) {
            addSnowView();
        }
        if (weatherConfig.haveHaze) {
            addHazeView();
        }
        if (weatherConfig.haveSun) {
            sunView.setColor(Color.WHITE);
            sunView.start();
            addView(sunView);
        }
        if (weatherConfig.haveCloud) {
            cloudView.setColor(Color.WHITE);
            cloudView.start();
            addView(cloudView);
        }
    }

    private WeatherInfo weatherInfo;
    private WeatherConfig weatherConfig;

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        Log.d("WeatherView", "setWeatherInfo");
        parseWeatherInfo();
        addWeatherPart();
    }

    private void parseWeatherInfo() {
        if (weatherInfo == null) {
            return;
        }
        weatherConfig.reset();
        String weatherStr =
                weatherInfo.getResult().get(0).getWeather();
        switch (weatherStr) {
            case WeatherType.BIG_CLOUD:
                weatherConfig.setHaveCloud(true);
                break;
            case WeatherType.BIG_SUN:
                weatherConfig.setHaveSun(true);
                break;
            case WeatherType.BLACK_CLOUD:
                weatherConfig.setHaveCloud(true);
                break;
            case WeatherType.CIRCLE_RAIN:
                weatherConfig.setHaveSun(true);
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                break;
            case WeatherType.CIRCLE_SNOW:
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveSun(true);
                weatherConfig.setHaveSnow(true);
                break;
            case WeatherType.HAZE:
                weatherConfig.setHaveHaze(true);
                break;
            case WeatherType.MIDDLE_RAIN:
                weatherConfig.setHaveRain(true);
                weatherConfig.setHaveCloud(true);
                break;
            case WeatherType.NORMAL_RAIN:
                weatherConfig.setHaveRain(true);
                weatherConfig.setHaveCloud(true);
                break;
            case WeatherType.RAIN_ADD_SNOW:
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                weatherConfig.setHaveSnow(true);
                break;
            case WeatherType.SMALL_CLOUD:
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveSun(true);
                break;
            case WeatherType.SMALL_RAIN:
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                break;
            case WeatherType.THUNDER_CIRCLE_RAIN:
                weatherConfig.setHaveSun(true);
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                break;
            case WeatherType.ZERO_CIRCLE_RAIN:
                weatherConfig.setHaveSun(true);
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                break;
            case WeatherType.ZERO_THUNDER_RAIN:
                weatherConfig.setHaveSun(true);
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveRain(true);
                break;
            case WeatherType.SMALL_SNOW:
                weatherConfig.setHaveCloud(true);
                weatherConfig.setHaveSnow(true);
                break;
            default:
                break;
        }
    }

    private class WeatherConfig {
        private boolean haveCloud = false;
        private boolean haveSun = false;
        private boolean haveRain = false;
        private boolean haveSnow = false;
        private boolean haveHaze = true;

        public boolean isHaveCloud() {
            return haveCloud;
        }

        public void setHaveCloud(boolean haveCloud) {
            this.haveCloud = haveCloud;
        }

        public boolean isHaveSun() {
            return haveSun;
        }

        public void setHaveSun(boolean haveSun) {
            this.haveSun = haveSun;
        }

        public boolean isHaveRain() {
            return haveRain;
        }

        public void setHaveRain(boolean haveRain) {
            this.haveRain = haveRain;
        }

        public boolean isHaveSnow() {
            return haveSnow;
        }

        public void setHaveSnow(boolean haveSnow) {
            this.haveSnow = haveSnow;
        }

        public boolean isHaveHaze() {
            return haveHaze;
        }

        public void setHaveHaze(boolean haveHaze) {
            this.haveHaze = haveHaze;
        }

        public void reset() {
            this.haveHaze = false;
            this.haveCloud = false;
            this.haveSun = false;
            this.haveSnow = false;
            this.haveRain = false;
        }
    }

    private void addRainView() {
        Observable.interval(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        final RainView rainView = new RainView(getContext());
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(20, ViewGroup.LayoutParams.MATCH_PARENT);
                        int marginLeft = (int) (Math.random() * getWidth());
                        lp.setMargins(marginLeft, 0, 0, 0);
                        rainView.setLayoutParams(lp);
                        rainView.setOnRainPickRoad(new RainView.OnRainPickRoad() {
                            @Override
                            public void onPick() {
                                removeView(rainView);
                            }
                        });
                        addView(rainView);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposableList.size() > 0) {
            for (int i = 0; i < disposableList.size(); i++) {
                if (!disposableList.get(i).isDisposed()) {
                    disposableList.get(i).dispose();
                }
            }
        }
    }

    private void addSnowView() {
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        final SnowView snowView = new SnowView(getContext());
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(40, ViewGroup.LayoutParams.MATCH_PARENT);
                        int marginLeft = (int) (Math.random() * getWidth());
                        lp.setMargins(marginLeft, 0, 0, 0);
                        snowView.setLayoutParams(lp);
                        snowView.setOnRainPickRoad(new RainView.OnRainPickRoad() {
                            @Override
                            public void onPick() {
                                removeView(snowView);
                            }
                        });
                        addView(snowView);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addHazeView() {
        sunView.setColor(0xFF999999);
        sunView.start();
        addView(sunView);
        cloudView.setColor(0xFF999999);
        cloudView.start();
        addView(cloudView);
        HazeView hazeView = new HazeView(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        hazeView.setLayoutParams(lp);
        addView(hazeView);
    }

    private List<Disposable> disposableList = new ArrayList<>();

    public void onPause() {
        if (disposableList.size() > 0) {
            for (int i = 0; i < disposableList.size(); i++) {
                if (!disposableList.get(i).isDisposed()) {
                    disposableList.get(i).dispose();
                }
            }
        }
    }
}
