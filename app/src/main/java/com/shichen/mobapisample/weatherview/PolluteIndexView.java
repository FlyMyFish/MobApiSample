package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.shichen.mobapisample.R;

/**
 * Created by shichen on 2017/11/20.
 *
 * @author shichen 754314442@qq.com
 */

public class PolluteIndexView extends View {
    private Context context;
    private String chineseName;
    private String englishName;

    public PolluteIndexView(Context context) {
        this(context, null);
    }

    public PolluteIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PolluteIndexView);
        maxIndex = ta.getInt(R.styleable.PolluteIndexView_maxIndex, 100);
        currentIndex = ta.getInt(R.styleable.PolluteIndexView_currentIndex, 0);
        chineseName = ta.getString(R.styleable.PolluteIndexView_chineseName);
        englishName = ta.getString(R.styleable.PolluteIndexView_englishName);
        ta.recycle();
        initPaint();
    }

    private int maxIndex = 100;
    //最大值
    private int currentIndex;
    //当前值

    private Paint mPaint;
    private Paint bgPaint;
    private Paint textPaint;

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(context, R.color.polluteStageOne));
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(ContextCompat.getColor(context, R.color.colorGray));
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(ContextCompat.getColor(context, R.color.colorTextGray));
    }

    public void setMaxIndex(int maxIndex) {
        this.maxIndex = maxIndex;
        invalidate();
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        drawName(canvas);
        drawIndex(canvas, width);
        drawBg(canvas, width, height);
    }

    /**
     * 指数的中文英文名称
     * @param canvas
     */
    private void drawName(Canvas canvas) {
        int nameSize = getMeasuredHeight() / 6;
        if (chineseName != null) {
            textPaint.setTextSize(nameSize);
            Rect textBound = new Rect();
            textPaint.getTextBounds(chineseName, 0, chineseName.length() - 1, textBound);
            canvas.drawText(chineseName, 0, textBound.height() + nameSize, textPaint);
        }
        if (englishName != null) {
            textPaint.setTextSize(nameSize);
            Rect textBound = new Rect();
            textPaint.getTextBounds(englishName, 0, englishName.length() - 1, textBound);
            canvas.drawText(englishName, 0, textBound.height() * 3 + nameSize, textPaint);
        }
    }

    /**
     * 指数的数值
     * @param canvas
     * @param width
     */
    private void drawIndex(Canvas canvas, int width) {
        int nameSize = getMeasuredHeight() / 6;
        int indexSize = getMeasuredHeight() / 9 * 4;
        textPaint.setTextSize(indexSize);
        Rect textBound = new Rect();
        textPaint.getTextBounds(String.valueOf(currentIndex), 0, String.valueOf(currentIndex).length(), textBound);
        canvas.drawText(String.valueOf(currentIndex), width - textPaint.measureText(String.valueOf(currentIndex)), textBound.height() + nameSize, textPaint);
    }

    /**
     * 所有指数的颜色
     */
    private int[] arcColors = new int[]{0xFF0289c3,
            0xFF76c0ef,
            0xFF74c141,
            0xFFf09837,
            0xFFfe4502,
            0xFFbf0d1d};

    /**
     * 背景以及数值控件
     * @param canvas
     * @param width
     * @param height
     */
    private void drawBg(Canvas canvas, int width, int height) {
        int nameSize = getMeasuredHeight() / 6;
        int indexViewSize = nameSize / 3;
        RectF rectBg = new RectF(0, height - indexViewSize * 5, width, height - indexViewSize * 4);
        canvas.drawRoundRect(rectBg, indexViewSize / 2, indexViewSize / 2, bgPaint);
        RectF rectIndex = new RectF();
        rectIndex.left = 0;
        rectIndex.top = height - indexViewSize * 5;
        float percent = Float.valueOf(currentIndex) / Float.valueOf(maxIndex);
        rectIndex.right = width * percent;
        rectIndex.bottom = height - indexViewSize * 4;
        LinearGradient linearGradient = new LinearGradient(0, height - indexViewSize * 5, width, height - indexViewSize * 4, arcColors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        canvas.drawRoundRect(rectIndex, indexViewSize / 2, indexViewSize / 2, mPaint);
    }
}
