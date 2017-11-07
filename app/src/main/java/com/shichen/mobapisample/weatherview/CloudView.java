package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by shichen on 2017/11/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CloudView extends View implements Animatable {
    private Paint cloudPaint;
    private float radius;

    public CloudView(Context context) {
        this(context, null);
    }

    public CloudView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        radius = context.getResources().getDisplayMetrics().density * 4;
        initCloudPaint();
        setupAnim();
        start();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int color){
        cloudPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCloud(canvas, getWidth(), translateX, translateY);
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

    private Animation floatAnim;
    private float translateX;
    private float translateY;

    private void setupAnim() {
        Log.d("CloudView", "setupAnim");
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                translateX = radius - Float.valueOf(String.valueOf(Math.cos((Math.PI * 2 * interpolatedTime)) * radius));
                translateY = Float.valueOf(String.valueOf(Math.sin(Math.PI * 2 * interpolatedTime) * radius));
                invalidate();
            }
        };
        animation.setDuration(5000);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        floatAnim = animation;
    }

    @Override
    public void start() {
        floatAnim.reset();
        startAnimation(floatAnim);
    }

    @Override
    public void stop() {
        clearAnimation();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}
