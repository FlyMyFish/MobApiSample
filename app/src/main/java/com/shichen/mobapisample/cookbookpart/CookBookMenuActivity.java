package com.shichen.mobapisample.cookbookpart;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.databinding.ActivityCookBookMenuBinding;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBookMenuActivity extends BaseActivity {
    private ActivityCookBookMenuBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cook_book_menu);
    }
}
