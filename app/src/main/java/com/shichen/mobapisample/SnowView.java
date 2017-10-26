package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.view.View;

/**
 * @author shichen 754314442@qq.com
 *         Created by shichen on 2017/10/26.
 */

public class SnowView extends View implements Animatable{
    private Paint cloudPaint;
    public SnowView(Context context) {
        super(context);
        initCloudPaint();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    private void drawSnowPoint(Canvas canvas, float totalH, float xF, float yF){

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
