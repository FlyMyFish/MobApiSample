package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by shichen on 2017/11/7.
 *
 * @author shichen 754314442@qq.com
 */

public class SunView extends View implements Animatable {
    private Paint cloudPaint;

    public SunView(Context context) {
        this(context, null);
    }

    public SunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        drawSun(canvas, getWidth(), 0, 0);
    }

    private void drawSun(Canvas canvas, float widthF, float transLateX, float transLateY) {
        canvas.rotate(sunRotate, widthF / 8 + transLateX, widthF / 4 + transLateY);
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
            canvas.drawPath(path, cloudPaint);
            canvas.rotate(30, widthF / 8 + transLateX, widthF / 4 + transLateY);
        }
    }

    private Animation circleAnim;
    private float sunRotate;

    private void setupAnim() {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                sunRotate = 360 * interpolatedTime;
                invalidate();
            }
        };
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(50000);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(-1);
        circleAnim = animation;
    }

    @Override
    public void start() {
        circleAnim.reset();
        startAnimation(circleAnim);
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
