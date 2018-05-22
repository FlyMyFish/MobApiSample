package com.shichen.mobapisample.cookbookpart;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.databinding.ActivityViewCookBookBinding;
import com.shichen.mobapisample.utils.SharePreferenceUtils;

/**
 * Created by shichen on 2017/12/13.
 *
 * @author shichen 754314442@qq.com
 */

public class ViewCookBookActivity extends BaseActivity {
    private final String TAG="ViewCookBookActivity";
    public static final String VIEW_COOK_BOOK = "viewCookBook";
    private CookBook.ResultBean.ListBean listBean;
    private ActivityViewCookBookBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cook_book);
        listBean = mGson.fromJson(new SharePreferenceUtils(getApplicationContext()).getData(VIEW_COOK_BOOK), CookBook.ResultBean.ListBean.class);
        Log.d(TAG,listBean.toString());
        binding.setListBean(listBean);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //判断当前设备版本号是否为4.4以上，如果是，则通过调用setTranslucentStatus让状态栏变透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        int titleHeight = getStatusBarHeight();
        if (toolbar.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            p.setMargins(0, titleHeight, 0, 0);
            toolbar.requestLayout();
        }
        if (listBean != null) {
            Glide.with(this).load(listBean.getRecipe().getImg()).into(binding.imgViewCook);
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
