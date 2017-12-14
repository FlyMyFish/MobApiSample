package com.shichen.mobapisample.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shichen 754314442@qq.com
 *         Created by shichen on 2017/10/18.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
    }

    private Retrofit retrofit;

    protected Retrofit getWeatherRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/v1/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    protected Retrofit getAirConditionRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/environment/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    protected Retrofit getCookBookRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/v1/cook/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private LoadingDialog loadingDialog;

    protected void showLoadingDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        if (!loadingDialog.isAdded()) {
            loadingDialog.setMsg(msg);
            //该方法解决FragmentDialog引发异常
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(loadingDialog, LoadingDialog.class.getSimpleName());
            //重要的方法.commitAllowingStateLoss()
            transaction.commitAllowingStateLoss();
        }
    }

    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        if (!loadingDialog.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(loadingDialog, LoadingDialog.class.getSimpleName());
            transaction.commitAllowingStateLoss();
        }
    }

    protected void disMissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * RxJava订阅列表
     */
    protected List<Disposable> disposableList = new ArrayList<>();

    /**
     * Activity暂停时取消所有订阅，以免内存泄漏
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (disposableList.size() > 0) {
            for (int i = 0; i < disposableList.size(); i++) {
                if (!disposableList.get(i).isDisposed()) {
                    disposableList.get(i).dispose();
                }
            }
        }
    }

    protected Gson mGson;
}
