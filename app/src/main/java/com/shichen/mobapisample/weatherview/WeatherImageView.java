package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by shichen on 2017/11/8.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherImageView extends SurfaceView implements SurfaceHolder.Callback{
    public WeatherImageView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
