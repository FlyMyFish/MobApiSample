package com.shichen.mobapisample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.bean.WeatherType;

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
    public WeatherView(@NonNull Context context) {
        this(context, null);
    }

    public WeatherView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        weatherConfig = new WeatherConfig();
    }

    private void addWeatherPart() {
        removeAllViews();
        if (weatherConfig.haveRain) {
            addRainView();
        } else if (weatherConfig.haveSnow) {

        } else if (weatherConfig.haveSun) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setImageDrawable(new SunDrawable(getContext(), imageView));
            addView(imageView);
        } else if (weatherConfig.haveCloud) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);
            imageView.setImageDrawable(new CloudDrawable(getContext(), imageView));
            addView(imageView);
        } else if (weatherConfig.haveHaze) {

        }
    }

    private WeatherInfo weatherInfo;
    private WeatherConfig weatherConfig;

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
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
        private boolean haveRain = true;
        private boolean haveSnow = false;
        private boolean haveHaze = false;

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
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        final RainView rainView = new RainView(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, ViewGroup.LayoutParams.MATCH_PARENT);
                        lp.setMargins((int) (Math.random() * getWidth()), 0, 0, 0);
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
}
