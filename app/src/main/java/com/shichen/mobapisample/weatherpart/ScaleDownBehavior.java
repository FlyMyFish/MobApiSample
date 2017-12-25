package com.shichen.mobapisample.weatherpart;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shichen.mobapisample.weatherview.WeatherImageView;

/**
 * Created by shichen on 2017/12/25.
 *
 * @author shichen 754314442@qq.com
 */

public class ScaleDownBehavior extends CoordinatorLayout.Behavior<WeatherImageView>{
    public ScaleDownBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, WeatherImageView child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, WeatherImageView child, View dependency) {
        if (dependency instanceof RecyclerView){
            RecyclerView dependencyT=(RecyclerView) dependency;
            Log.d("ScrollY",dependencyT.getScrollY()+"");
        }
        return true;
    }
}
