package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherTypeDrawable extends Drawable implements Animatable {
    private Context context;
    private View parent;
    private Paint cloudPaint;
    private Paint linePaint;

    public WeatherTypeDrawable(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        initCloudPaint();
        initLinePaint();
        initRainDownAnim();
        start();
    }

    private void initLinePaint() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.FILL);
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void start() {
        rainDownAnim.reset();
        parent.startAnimation(rainDownAnim);
    }

    @Override
    public void stop() {
        parent.clearAnimation();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        drawCloud(canvas, rectF.width(), 0, 0);
        drawSun(canvas, rectF.width(), 0, 0);
        drawRain(canvas, rectF.width(), 0, 0);
    }

    private void drawRain(Canvas canvas, float widthF, float transLateX, float transLateY) {
        drawRainPoint(canvas, widthF / 16, widthF * widthPer, widthF * rainPosition);
    }

    private void drawRainPoint(Canvas canvas, float totalH, float xF, float yF) {
        float radiusF = totalH / 8;
        RectF rectF = new RectF();
        rectF.set(xF, yF + totalH - radiusF * 2, xF + radiusF * 2, yF + totalH);
        canvas.drawArc(rectF, 180, -180, false, linePaint);
        Path path = new Path();
        path.moveTo(xF + radiusF, yF);
        path.lineTo(xF, yF + totalH - radiusF);
        path.lineTo(xF + radiusF * 2, yF + totalH - radiusF);
        path.close();
        canvas.drawPath(path, linePaint);
    }

    private void drawSun(Canvas canvas, float widthF, float transLateX, float transLateY) {
        canvas.drawCircle(widthF / 8 + transLateX, widthF / 4 + transLateY, widthF / 12, cloudPaint);
        float lineW = widthF / 12 / 12;
        float lineH = widthF / 4 / 8;
        for (int i = 0; i < 12; i++) {
            Path path = new Path();
            path.moveTo(widthF / 8 - lineW / 2 + transLateX, widthF / 8 + transLateY);
            path.lineTo(widthF / 8 + lineW / 2 + transLateX, widthF / 8 + transLateY);
            path.lineTo(widthF / 8 + lineW / 2 + transLateX, widthF / 8 + lineH + transLateY);
            path.lineTo(widthF / 8 - lineW / 2 + transLateX, widthF / 8 + lineH + transLateY);
            path.close();
            canvas.drawPath(path, linePaint);
            canvas.rotate(30, widthF / 8 + transLateX, widthF / 4 + transLateY);
        }
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

    private Animation rainDownAnim;

    private float rainPosition;

    private float widthPer = 0.5f;

    private void initRainDownAnim() {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                rainPosition = interpolatedTime;
                invalidateSelf();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                widthPer = Float.valueOf(String.valueOf(Math.random()));
            }
        });
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateInterpolator());
        rainDownAnim = animation;
    }
}
