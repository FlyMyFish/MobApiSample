package com.shichen.mobapisample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.bean.TargetCity;
import com.shichen.mobapisample.databinding.ActivityPickTargetCityBinding;
import com.shichen.mobapisample.utils.SharePreferenceUtils;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class PickTargetCityActivity extends BaseActivity {
    private SupportCity supportCity;
    private ActivityPickTargetCityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pick_target_city);
        String supportCityStr = new SharePreferenceUtils(getApplicationContext()).getData(Config.CITY_LIST);
        supportCity = mGson.fromJson(supportCityStr, SupportCity.class);
        binding.setProvinceList(supportCity.getResult());
        binding.setHandler(new PickTargetCityHandler());
        targetCity = new TargetCity();
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

    private List<SupportCity.ResultBean.CityBean> cityList;
    private List<SupportCity.ResultBean.CityBean.DistrictBean> districtList;
    private TargetCity targetCity;

    public class PickTargetCityHandler {
        public void pickProvince(String province) {
            cityList = supportCity.getCityListByProvince(province);
            if (districtList!=null){
                districtList.clear();
            }
            binding.setDistrictList(districtList);
            binding.setCityList(cityList);
            targetCity.setProvince(province);
        }

        public void pickCity(String city) {
            districtList=supportCity.getDistrictListByCity(cityList, city);
            binding.setDistrictList(districtList);
            targetCity.setCity(city);
        }

        public void pickDistrict(String district) {
            targetCity.setDistrict(district);
            new SharePreferenceUtils(getApplicationContext()).saveData(Config.TARGET_CITY, targetCity.toString());
            finish();
        }
    }
}
