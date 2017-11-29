package com.shichen.mobapisample.config;

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
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by shichen on 2017/10/19.
 *
 * 卫星旋转加载动画
 * @author shichen 754314442@qq.com
 */

public class MoonDrawable extends Drawable implements Animatable{
    private Context context;
    private View parent;
    private Paint mPaint;

    public MoonDrawable(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        initPaint();
        setupAnimation();
        start();
    }

    private Animation moonAnim;
    private float rotate =15;

    private void setupAnimation() {
        Animation animation=new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                rotate =15+45*interpolatedTime;
                invalidateSelf();
            }
        };
        animation.setDuration(1000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        moonAnim=animation;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        float widthF = rectF.height();
        float heightF = rectF.height();
        drawBase(canvas, widthF / 2, heightF, heightF / 8);
        drawMoon(canvas,widthF,heightF, rotate);
    }

    /**
     * 基座
     * @param canvas
     * @param cx
     * @param cy
     * @param radiusSize
     */
    private void drawBase(Canvas canvas, float cx, float cy, float radiusSize) {
        canvas.drawCircle(cx, cy, radiusSize, mPaint);
    }

    private void drawMoon(Canvas canvas, float widthF, float heightF, float angle) {
        //锅与基座的链接块
        RectF blockBound = new RectF(widthF / 2 - heightF / 16, heightF - heightF / 8 - heightF / 8 / 4 * 3, widthF / 2 + heightF / 16, heightF - heightF / 8 - heightF / 8 / 4);
        canvas.rotate(angle, widthF / 2, heightF);
        canvas.drawRect(blockBound, mPaint);
        //锅
        RectF moonBody=new RectF(widthF/4,heightF/4,widthF/4*3,heightF/4*3);
        canvas.drawArc(moonBody,0,180,true,mPaint);
        //左侧支架
        Path leftLine=new Path();
        leftLine.moveTo(widthF/4,heightF/2-heightF/64);
        leftLine.lineTo(widthF/4+widthF/32,heightF/2-heightF/64);
        leftLine.lineTo(widthF/2+widthF/64,heightF/4-heightF/64);
        leftLine.lineTo(widthF/2-widthF/64,heightF/4-heightF/64);
        leftLine.close();
        canvas.drawPath(leftLine,mPaint);
        //右侧支架
        Path rightLine=new Path();
        rightLine.moveTo(widthF/4*3,heightF/2-heightF/64);
        rightLine.lineTo(widthF/4*3-widthF/32,heightF/2-heightF/64);
        rightLine.lineTo(widthF/2-widthF/64,heightF/4-heightF/64);
        rightLine.lineTo(widthF/2+widthF/64,heightF/4-heightF/64);
        rightLine.close();
        canvas.drawPath(rightLine,mPaint);
        //中间支架
        Path middleLine=new Path();
        middleLine.moveTo(widthF/2-widthF/128,heightF/4-heightF/64);
        middleLine.lineTo(widthF/2+widthF/128,heightF/4-heightF/64);
        middleLine.lineTo(widthF/2+widthF/128,heightF/2-heightF/64);
        middleLine.lineTo(widthF/2-widthF/128,heightF/2-heightF/64);
        middleLine.close();
        canvas.drawPath(middleLine,mPaint);
        //连接支架的线
        Path holderLine=new Path();
        holderLine.moveTo(widthF/2-widthF/16,heightF/4+heightF/32);
        holderLine.lineTo(widthF/2-widthF/16,heightF/4+heightF/16);
        holderLine.lineTo(widthF/2,heightF/4+heightF/16+heightF/16);
        holderLine.lineTo(widthF/2+widthF/16,heightF/4+heightF/16);
        holderLine.lineTo(widthF/2+widthF/16,heightF/4+heightF/32);
        holderLine.lineTo(widthF/2,heightF/4+heightF/16);
        canvas.drawPath(holderLine,mPaint);
    }

    @Override
    public void start() {
        moonAnim.reset();
        parent.startAnimation(moonAnim);
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
