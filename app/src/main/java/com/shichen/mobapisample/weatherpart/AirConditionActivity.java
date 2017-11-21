package com.shichen.mobapisample.weatherpart;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.AirQuality;
import com.shichen.mobapisample.bean.TargetCity;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.config.Config;
import com.shichen.mobapisample.databinding.ActivityAirConditionBinding;
import com.shichen.mobapisample.utils.SharePreferenceUtils;

/**
 * Created by shichen on 2017/11/20.
 *
 * @author shichen 754314442@qq.com
 */

public class AirConditionActivity extends BaseActivity {
    private ActivityAirConditionBinding binding;
    private SharePreferenceUtils sharePreferenceUtils;
    public static final String AIR_QUALITY_DATA = "airQualityData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_air_condition);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(AIR_QUALITY_DATA)) {
                binding.setAirQuality(((AirQuality) getIntent().getExtras().getSerializable(AIR_QUALITY_DATA)).getResult().get(0));
            }
        }
        sharePreferenceUtils = new SharePreferenceUtils(getApplicationContext());
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.kindly_reminder))
                    .setMessage(getString(R.string.city_data_null))
                    .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create().show();
        }
    }

}
