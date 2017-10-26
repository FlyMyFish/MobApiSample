package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by shichen on 2017/10/26.
 *
 * @author shichen 754314442@qq.com
 */

public class RainView extends View implements Animatable {
    private Paint cloudPaint;

    public RainView(Context context) {
        super(context);
        initCloudPaint();
        initAnim();
        start();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setColor(Color.WHITE);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawRainPoint(canvas, getWidth() * 4, 0, getHeight() * rainPosition);
    }

    private void drawRainPoint(Canvas canvas, float totalH, float xF, float yF) {
        float radiusF = totalH / 8;
        RectF rectF = new RectF();
        rectF.set(xF, yF + totalH - radiusF * 2, xF + radiusF * 2, yF + totalH);
        canvas.drawArc(rectF, 180, -180, false, cloudPaint);
        Path path = new Path();
        path.moveTo(xF + radiusF, yF);
        path.lineTo(xF, yF + totalH - radiusF);
        path.lineTo(xF + radiusF * 2, yF + totalH - radiusF);
        path.close();
        canvas.drawPath(path, cloudPaint);
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
        animation.setDuration(2000);
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.RESTART);
        rainDropAnim = animation;
    }

    public void setOnRainPickRoad(OnRainPickRoad onRainPickRoad) {
        this.onRainPickRoad = onRainPickRoad;
    }

    private OnRainPickRoad onRainPickRoad;

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