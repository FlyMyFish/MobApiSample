package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.shichen.mobapisample.R;

/**
 * Created by shichen on 2017/11/21.
 *
 * @author shichen 754314442@qq.com
 */

public class VerticalAirPolluteView extends View {
    /**
     * 所有指数的颜色
     */
    private int[] arcColors = new int[]{0xFF0289c3,
            0xFF76c0ef,
            0xFF74c141,
            0xFFf09837,
            0xFFfe4502,
            0xFFbf0d1d};

    private Context context;

    public VerticalAirPolluteView(Context context) {
        this(context, null);
    }

    public VerticalAirPolluteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VerticalAirPolluteView);
        maxPolluteIndex = ta.getInt(R.styleable.VerticalAirPolluteView_maxPolluteIndex, 300);
        currentPolluteIndex = ta.getInt(R.styleable.VerticalAirPolluteView_currentPolluteIndex, 300);
        msg = ta.getString(R.styleable.VerticalAirPolluteView_msg);
    }

    private Paint textPaint;
    private Paint linePaint;
    private Paint mainPaint;
    private Paint bgPaint;
    private int strokeWidth = 1;
    private String msg;

    private int maxPolluteIndex;
    private int currentPolluteIndex;

    public void setMsg(String msg) {
        this.msg = msg;
        invalidate();
    }

    public void setMaxPolluteIndex(int maxPolluteIndex) {
        this.maxPolluteIndex = maxPolluteIndex;
        invalidate();
    }

    public void setCurrentPolluteIndex(int currentPolluteIndex) {
        this.currentPolluteIndex = currentPolluteIndex;
        invalidate();
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setAntiAlias(true);
        textPaint.setColor(ContextCompat.getColor(context, R.color.colorTextGray));
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setColor(ContextCompat.getColor(context, R.color.colorTextGray));
        linePaint.setStrokeWidth(strokeWidth);
        mainPaint = new Paint();
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setAntiAlias(true);
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int textSize = width / 3;
        int indexViewSize = width / 8;
        int blankSize = width / 2;
        float textWidth;
        Rect textBound = new Rect();
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(msg, 0, msg.length(), textBound);
        textWidth = textPaint.measureText(msg);
        canvas.drawText(msg, width / 2 - textWidth / 2, height, textPaint);
        canvas.drawLine(0, height - blankSize, width, height - blankSize, linePaint);
        bgPaint.setStrokeWidth(indexViewSize);
        bgPaint.setColor(ContextCompat.getColor(context, R.color.colorGray));
        canvas.drawLine(width / 2, height - strokeWidth - blankSize, width / 2, 0, bgPaint);
        LinearGradient linearGradient = new LinearGradient(width / 2, height - strokeWidth - blankSize, width / 2, 0, arcColors, null, Shader.TileMode.CLAMP);
        mainPaint.setStrokeWidth(indexViewSize);
        mainPaint.setShader(linearGradient);
        float percent = Float.valueOf(currentPolluteIndex) / Float.valueOf(maxPolluteIndex);
        canvas.drawLine(width / 2, height - strokeWidth - blankSize, width / 2, height - strokeWidth - (height - strokeWidth - blankSize) * percent - blankSize, mainPaint);
    }
}
