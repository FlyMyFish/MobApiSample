package com.shichen.mobapisample;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.databinding.ActivityTestApiBinding;
import com.shichen.mobapisample.weatherapi.IWeatherApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 接口测试页面
 *
 * @author shichen 754314442@qq.com
 *         Created by Administrator on 2017/9/22.
 */

public class TestApiActivity extends BaseActivity {
    private IWeatherApi weatherApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestApiBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test_api);
        binding.setMHandler(new TestApiHandler());
        weatherApi = getRetrofit().create(IWeatherApi.class);
    }

    public class TestApiHandler {
        public void getCityList() {
            weatherApi.getApiSupportCity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<SupportCity>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable disposable) {

                        }

                        @Override
                        public void onNext(@NonNull SupportCity supportCity) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TestApiActivity.this);
                            builder.setTitle(R.string.api_result)
                                    .setMessage(supportCity.toString())
                                    .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create().show();
                        }

                        @Override
                        public void onError(@NonNull Throwable throwable) {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
                            throwable.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
