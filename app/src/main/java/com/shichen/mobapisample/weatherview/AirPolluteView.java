package com.shichen.mobapisample.weatherview;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.shichen.mobapisample.R;

/**
 * Created by shichen on 2017/11/20.
 *
 * @author shichen 754314442@qq.com
 */

public class AirPolluteView extends View {
    private Paint fillArcPaint;
    private Context context;
    private Paint bgPaint;
    private Paint textPaint;
    private int[] arcColors = new int[]{0xFF0289c3,
            0xFF76c0ef,
            0xFF74c141,
            0xFFf09837,
            0xFFfe4502,
            0xFFbf0d1d,
            0xFFbf0d1d,
            0xFF0289c3,
            0xFF0289c3};
    private BlurMaskFilter mBlur;

    public AirPolluteView(Context context) {
        this(context, null);
    }

    public AirPolluteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        mBlur = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AirPolluteView);
        maxPollute = ta.getInt(R.styleable.AirPolluteView_maxPollute, 500);
        currentPollute = ta.getInt(R.styleable.AirPolluteView_currentPollute, 500);
        qualityStr = ta.getString(R.styleable.AirPolluteView_qualityStr);
    }

    private int maxPollute;
    private int currentPollute;
    private String qualityStr;

    public void setMaxPollute(int maxPollute) {
        this.maxPollute = maxPollute;
        invalidate();
    }

    public void setCurrentPollute(int currentPollute) {
        this.currentPollute = currentPollute;
        invalidate();
    }

    public void setQualityStr(String qualityStr) {
        this.qualityStr = qualityStr;
        invalidate();
    }

    private void initPaint() {
        fillArcPaint = new Paint();
        // 设置是否抗锯齿
        fillArcPaint.setAntiAlias(true);
        // 帮助消除锯齿
        fillArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 设置中空的样式
        fillArcPaint.setStyle(Paint.Style.STROKE);
        fillArcPaint.setDither(true);
        fillArcPaint.setStrokeJoin(Paint.Join.ROUND);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        // 帮助消除锯齿
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 设置中空的样式
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setDither(true);
        bgPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        drawBg(canvas, width, height);
        drawQuality(canvas, width, height);
    }

    /**
     * 画背景以及渐变的圆弧进度
     *
     * @param canvas
     * @param width
     * @param height
     */
    private void drawBg(Canvas canvas, int width, int height) {
        int size = Math.min(height, width) / 5 * 4;
        bgPaint.setMaskFilter(mBlur);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStrokeWidth(size / 8);
        bgPaint.setColor(ContextCompat.getColor(context, R.color.colorGray));
        // 模糊效果
        fillArcPaint.setMaskFilter(mBlur);
        // 设置线的类型,边是圆的
        fillArcPaint.setStrokeCap(Paint.Cap.ROUND);
        fillArcPaint.setStrokeWidth(size / 8);
        RectF rectF = new RectF();
        rectF.set(width / 2 - size / 2, height / 2 - size / 2, width / 2 + size / 2, height / 2 + size / 2);
        canvas.rotate(-210, width / 2, height / 2);
        canvas.drawArc(rectF, 0, 240, false, bgPaint);
        SweepGradient sweepGradient = new SweepGradient(width / 2, height / 2, arcColors, null);
        fillArcPaint.setShader(sweepGradient);
        float percent = Float.valueOf(currentPollute) / Float.valueOf(maxPollute);
        int targetArg = (int) (percent * 240.0f);
        canvas.drawArc(rectF, 0, targetArg, false, fillArcPaint);
        canvas.saveLayer(rectF, bgPaint, Canvas.ALL_SAVE_FLAG);
        canvas.rotate(210, width / 2, height / 2);
    }

    private void drawQuality(Canvas canvas, int width, int height) {
        //测量
        int textSize = Math.min(height, width) / 8;
        textPaint.setTextSize(textSize);
        textPaint.setColor(getColor(maxPollute, currentPollute));
        Rect rect = new Rect();
        if (!TextUtils.isEmpty(qualityStr)) {
            textPaint.getTextBounds(qualityStr, 0, qualityStr.length(), rect);
            //文字高度
            float textHeight = rect.height();
            float textWidth = textPaint.measureText(qualityStr);

            //空气质量文字描述
            //canvas.drawText(qualityStr, width / 2 - textWidth / 2, height / 2 + textHeight / 2, textPaint);
            //以下是空气质量数值
            textPaint.setTextSize(textSize / 2 * 3);
            canvas.drawText(String.valueOf(currentPollute), width / 2 - textPaint.measureText(String.valueOf(currentPollute)) / 2, height / 2 - textHeight, textPaint);
        }
    }

    /**
     * 计算文字颜色
     *
     * @return 通过渐变计算得到的颜色值
     */
    public static int getColor(int maxPollute, int currentPollute) {
        int[] arcColors = new int[]{0xFF0289c3,
                0xFF76c0ef,
                0xFF74c141,
                0xFFf09837,
                0xFFfe4502,
                0xFFbf0d1d};
        //currentPollute,maxPollute
        int unit = maxPollute / 6;
        int startColor, endColor;
        switch (currentPollute / unit) {
            case 0:
                startColor = arcColors[0];
                endColor = arcColors[1];
                break;
            case 1:
                startColor = arcColors[1];
                endColor = arcColors[2];
                break;
            case 2:
                startColor = arcColors[2];
                endColor = arcColors[3];
                break;
            case 3:
                startColor = arcColors[3];
                endColor = arcColors[4];
                break;
            case 4:
                startColor = arcColors[4];
                endColor = arcColors[5];
                break;
            default:
                startColor = arcColors[0];
                endColor = arcColors[1];
                break;
        }
        int realIndex = currentPollute % unit;
        float percent = Float.valueOf(realIndex) / Float.valueOf(unit);
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        return (int) argbEvaluator.evaluate(percent, startColor, endColor);
    }
}
