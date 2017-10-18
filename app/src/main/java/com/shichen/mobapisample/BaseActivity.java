package com.shichen.mobapisample;

import android.support.v7.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shichen 754314442@qq.com
 *         Created by shichen on 2017/10/18.
 */

public class BaseActivity extends AppCompatActivity {
    private Retrofit retrofit;

    protected Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://apicloud.mob.com/v1/weather/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
