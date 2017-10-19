package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by shichen on 2017/10/19.
 *
 * @author shichen 754314442@qq.com
 */

public class MoonDrawable extends Drawable {
    private Context context;
    private View parent;
    private Paint mPaint;

    public MoonDrawable(Context context, View parent) {
        this.context = context;
        this.parent = parent;
        initPaint();
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
        float widthF = rectF.width();
        float heightF = rectF.height();
        drawBase(canvas, widthF / 2, heightF, heightF / 8);
        drawMoon(canvas,widthF,heightF,30);
    }

    private void drawBase(Canvas canvas, float cx, float cy, float radiusSize) {
        canvas.drawCircle(cx, cy, radiusSize, mPaint);
    }

    private void drawMoon(Canvas canvas, float widthF, float heightF, float angle) {
        RectF blockBound = new RectF(widthF / 2 - heightF / 16, heightF - heightF / 8 - heightF / 8 / 4 * 3, widthF / 2 + heightF / 16, heightF - heightF / 8 - heightF / 8 / 4);
        canvas.rotate(angle, widthF / 2, heightF);
        canvas.drawRect(blockBound, mPaint);
        RectF moonBody=new RectF(widthF/4,heightF/4,widthF/4*3,heightF/4*3);
        canvas.drawArc(moonBody,0,180,true,mPaint);

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
