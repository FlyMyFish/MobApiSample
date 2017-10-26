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

public class SunDrawable extends Drawable {
    private Context context;
    private View parent;
    private Paint cloudPaint;

    public SunDrawable(Context context, View parent) {
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
        drawSun(canvas, rectF.width(), 0, 0);
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
            canvas.drawPath(path, cloudPaint);
            canvas.rotate(30, widthF / 8 + transLateX, widthF / 4 + transLateY);
        }
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
