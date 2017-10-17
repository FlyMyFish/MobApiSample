package com.shichen.mobapisample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shichen.mobapisample.databinding.ActivityTestApiBinding;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口测试页面
 * Created by Administrator on 2017/9/22.
 */

public class TestApiActivity extends AppCompatActivity{
    private Retrofit retrofit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestApiBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_test_api);
        binding.setMHandler(new TestApiHandler());
        retrofit=new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/v1/weather")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public class TestApiHandler{
        public void getCityList(){

        }
    }
}
