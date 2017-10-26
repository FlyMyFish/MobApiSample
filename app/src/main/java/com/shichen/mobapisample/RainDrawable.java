package com.shichen.mobapisample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by shichen on 2017/10/26.
 *
 * @author shichen 754314442@qq.com
 */

public class RainDrawable extends Drawable {
    private Context context;
    private View parent;
    private Paint cloudPaint;

    public RainDrawable(Context context, View parent) {
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
    public void draw(@NonNull Canvas canvas) {
        RectF rectF = new RectF(getBounds());
        drawRainPoint(canvas, rectF.height(), 0, 0);
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

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
