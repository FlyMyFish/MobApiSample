package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author shichen 754314442@qq.com
 *         Created by shichen on 2017/10/26.
 */

public class SnowView extends View implements Animatable {
    private Paint cloudPaint;
    private float density;

    public SnowView(Context context) {
        this(context, null);
    }

    public SnowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        initCloudPaint();
        initAnim();
        start();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
        cloudPaint.setStrokeWidth(density * 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawSnowPoint(canvas, getWidth(), 0, getHeight() * rainPosition-getWidth());
    }

    private void drawSnowPoint(Canvas canvas, float totalH, float xF, float yF) {
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(totalH / 2 + xF, yF, totalH / 2 + xF, totalH / 2 + yF, cloudPaint);
            canvas.rotate(60, totalH / 2 + xF, totalH / 2 + yF);
        }
    }

    private Animation rainDropAnim;
    private float rainPosition;

    private void initAnim() {
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                rainPosition = interpolatedTime;
                invalidate();
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (onRainPickRoad != null) {
                    onRainPickRoad.onPick();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setDuration(3000);
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new AccelerateInterpolator());
        rainDropAnim = animation;
    }

    public void setOnRainPickRoad(RainView.OnRainPickRoad onRainPickRoad) {
        this.onRainPickRoad = onRainPickRoad;
    }

    private RainView.OnRainPickRoad onRainPickRoad;

    public interface OnRainPickRoad {
        void onPick();
    }

    @Override
    public void start() {
        rainDropAnim.reset();
        startAnimation(rainDropAnim);
    }

    @Override
    public void stop() {
        clearAnimation();
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
