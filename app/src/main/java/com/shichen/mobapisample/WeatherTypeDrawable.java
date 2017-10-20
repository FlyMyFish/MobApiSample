package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherTypeDrawable extends Drawable implements Animatable {
    private Context context;
    private View parent;
    private Paint cloudPaint;

    public WeatherTypeDrawable(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        initCloudPaint();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        drawCloud(canvas, rectF.width(), 0, 0);
    }

    private void drawCloud(Canvas canvas, float widthF, float transLateX, float transLateY) {
        canvas.drawCircle(0 + transLateX, 0 + transLateY, widthF / 8, cloudPaint);
        canvas.drawCircle(widthF / 16 + transLateX, 0 + transLateY, widthF / 9, cloudPaint);
        canvas.drawCircle(widthF / 4 + transLateX, 0 + transLateY, widthF / 6, cloudPaint);
        canvas.drawCircle(widthF / 12 * 5 + transLateX, 0 + transLateY, widthF / 7, cloudPaint);
        canvas.drawCircle(widthF / 2 + transLateX, widthF / 10 + transLateY, widthF / 16, cloudPaint);
        canvas.drawCircle(widthF / 12 * 5 + widthF / 10 + transLateX, 0 + transLateY, widthF / 10, cloudPaint);
        canvas.drawCircle(widthF / 4 * 3 - widthF / 16 + transLateX, -widthF / 32 + transLateY, widthF / 9, cloudPaint);
        canvas.drawCircle(widthF + transLateX, -widthF / 32 + transLateY, widthF / 4, cloudPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
