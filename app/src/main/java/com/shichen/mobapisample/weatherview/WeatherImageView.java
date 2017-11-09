package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.bean.WeatherType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by shichen on 2017/11/8.
 *
 * @author shichen 754314442@qq.com
 *         <p>
 *         使用SurfaceView是为了提升动画性能，曾经使用过FrameLayout以及Drawable，最终效果都不尽如人意，卡顿并且容易崩溃
 *         原因是View的绘制在主线程，而SurfaceView的绘制是在子线程
 */

public class WeatherImageView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    /**
     * SurfaceHolder是SurfaceView控制器，用来操作SurfaceView
     */
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Paint mPaint;
    private float radius;
    private int timeTag;
    private Paint rainPaint;
    private float density;
    private WeatherInfo weatherInfo;
    private WeatherConfig weatherConfig;

    public WeatherImageView(Context context) {
        this(context, null);
    }

    public WeatherImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        weatherConfig = new WeatherConfig();
        density = context.getResources().getDisplayMetrics().density;
        radius = density * 4;
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        rainPaint = new Paint();
        rainPaint.setAntiAlias(true);
        rainPaint.setColor(Color.WHITE);
        rainPaint.setStyle(Paint.Style.FILL);
        rainPaint.setAlpha(180);
    }

    /**
     * 配置天气信息
     * @param weatherInfo 天气信息
     */
    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        parseWeatherInfo();
    }

    /**
     * 解析天气信息结果用WeatherInfo表现
     */
    private void parseWeatherInfo() {
        if (weatherInfo == null) {
            return;
        }
        weatherConfig.reset();
        String weatherStr =
                weatherInfo.getResult().get(0).getWeather();
        switch (weatherStr) {
            case WeatherType.BIG_CLOUD:
                weatherConfig.haveCloud = true;
                break;
            case WeatherType.BIG_SUN:
                weatherConfig.haveSun = true;
                break;
            case WeatherType.BLACK_CLOUD:
                weatherConfig.haveCloud = true;
                break;
            case WeatherType.CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.CIRCLE_SNOW:
                weatherConfig.haveCloud = true;
                weatherConfig.haveSun = true;
                weatherConfig.haveSnow = true;
                break;
            case WeatherType.HAZE:
                weatherConfig.haveHaze = true;
                break;
            case WeatherType.MIDDLE_RAIN:
                weatherConfig.haveRain = true;
                weatherConfig.haveCloud = true;
                break;
            case WeatherType.NORMAL_RAIN:
                weatherConfig.haveRain = true;
                weatherConfig.haveCloud = true;
                break;
            case WeatherType.RAIN_ADD_SNOW:
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                weatherConfig.haveSnow = true;
                break;
            case WeatherType.SMALL_CLOUD:
                weatherConfig.haveCloud = true;
                weatherConfig.haveSun = true;
                break;
            case WeatherType.SMALL_RAIN:
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.THUNDER_CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.ZERO_CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.ZERO_THUNDER_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.SMALL_SNOW:
                weatherConfig.haveCloud = true;
                weatherConfig.haveSnow = true;
                break;
            default:
                break;
        }
    }

    private ExecutorService singleThreadPool;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        //雨雪密度 rainDensity
        int rainDensity = 40;
        Random rand = new Random();
        rainX.clear();
        for (int i = 0; i < rainDensity; i++) {
            rainX.add(rand.nextInt(getWidth()));
        }
        ThreadFactory weatherThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("WeatherViewUpdate").build();
        singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), weatherThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
        singleThreadPool.shutdown();
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
            timeTag++;
            rotate = rotate + 0.005;
            translateX = radius - Float.valueOf(String.valueOf(Math.cos((Math.PI * 2 * timeTag / 300)) * radius));
            translateY = Float.valueOf(String.valueOf(Math.sin(Math.PI * 2 * timeTag / 300) * radius));
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            // SurfaceView背景
            mCanvas.drawColor(Color.parseColor("#3CA0D0"));
            if (weatherConfig.haveHaze) {
                mPaint.setColor(0xFFCCCCCC);
            }
            if (weatherConfig.haveSun) {
                drawSun(mCanvas, getWidth());
            }
            if (weatherConfig.haveRain) {
                drawRain(mCanvas);
            }
            if (weatherConfig.haveSnow) {
                drawSnow(mCanvas);
            }
            if (weatherConfig.haveCloud) {
                drawCloud(mCanvas, getWidth(), translateX, translateY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private float translateX;
    private float translateY;
    private double rotate;

    private void drawSun(Canvas canvas, float widthF) {
        final int sunLineCount = 12;
        canvas.drawCircle(widthF / 8, widthF / 4, widthF / 12, mPaint);
        float lineW = widthF / 12 / 12;
        float lineH = widthF / 4 / 8;
        float cx = widthF / 8;
        float cy = widthF / 4;
        float rb = widthF / 8;
        float rs = widthF / 8 - lineH;
        for (int i = 0; i < sunLineCount; i++) {
            Path path = new Path();
            path.moveTo(Float.valueOf(String.valueOf(cx + rb * Math.sin(rotate) - lineW / 2 * Math.cos(rotate))), Float.valueOf(String.valueOf(cy - rb * Math.cos(rotate) - lineW / 2 * Math.sin(rotate))));
            path.lineTo(Float.valueOf(String.valueOf(cx + rb * Math.sin(rotate) + lineW / 2 * Math.cos(rotate))), Float.valueOf(String.valueOf(cy - rb * Math.cos(rotate) + lineW / 2 * Math.sin(rotate))));
            path.lineTo(Float.valueOf(String.valueOf(cx + rs * Math.sin(rotate) + lineW / 2 * Math.cos(rotate))), Float.valueOf(String.valueOf(cy - rs * Math.cos(rotate) + lineW / 2 * Math.sin(rotate))));
            path.lineTo(Float.valueOf(String.valueOf(cx + rs * Math.sin(rotate) - lineW / 2 * Math.cos(rotate))), Float.valueOf(String.valueOf(cy - rs * Math.cos(rotate) - lineW / 2 * Math.sin(rotate))));
            path.close();
            canvas.rotate(30, cx, cy);
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 生成随机数，来表示雨滴或者雪花的x坐标位置
     */
    private List<Integer> rainX = new ArrayList<>();

    private void drawRain(Canvas canvas) {
        int rainDropSpeed = 30;
        for (int i = 0; i < rainX.size(); i++) {
            drawRainPoint(canvas, getWidth() / 16, rainX.get(i), (timeTag - rainDropSpeed * i) * rainDropSpeed % getHeight());
        }
    }

    private void drawRainPoint(Canvas canvas, float totalH, float xF, float yF) {
        float radiusF = totalH / 8;
        RectF rectF = new RectF();
        rectF.set(xF, yF + totalH - radiusF * 2, xF + radiusF * 2, yF + totalH);
        canvas.drawArc(rectF, 180, -180, false, rainPaint);
        Path path = new Path();
        path.moveTo(xF + radiusF, yF);
        path.lineTo(xF, yF + totalH - radiusF);
        path.lineTo(xF + radiusF * 2, yF + totalH - radiusF);
        path.close();
        canvas.drawPath(path, rainPaint);
    }

    private void drawSnow(Canvas canvas) {
        int snowDropSpeed = 20;
        for (int i = 0; i < rainX.size(); i++) {
            drawSnowPoint(canvas, getWidth() / 32, rainX.get(i), (timeTag - snowDropSpeed * i) * snowDropSpeed % getHeight());
        }
    }

    private void drawSnowPoint(Canvas canvas, float totalH, float xF, float yF) {
        float r = totalH / 2;
        float cx = totalH / 2 + xF;
        float cy = totalH / 2 + yF;
        float lineW = density;
        int angle = 45;
        canvas.drawRect(cx - lineW, cy - r, cx + lineW, cy + r, mPaint);
        Path pathR = new Path();
        pathR.moveTo(Float.valueOf(String.valueOf(cx + r * Math.sin(angle) - lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy - r * Math.cos(angle) - lineW * Math.sin(angle))));
        pathR.lineTo(Float.valueOf(String.valueOf(cx + r * Math.sin(angle) + lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy - r * Math.cos(angle) + lineW * Math.sin(angle))));
        pathR.lineTo(Float.valueOf(String.valueOf(cx - r * Math.sin(angle) + lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy + r * Math.cos(angle) + lineW * Math.sin(angle))));
        pathR.lineTo(Float.valueOf(String.valueOf(cx - r * Math.sin(angle) - lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy + r * Math.cos(angle) - lineW * Math.sin(angle))));
        pathR.close();
        Path pathL = new Path();
        pathL.moveTo(Float.valueOf(String.valueOf(cx - r * Math.sin(angle) - lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy - r * Math.cos(angle) + lineW * Math.sin(angle))));
        pathL.lineTo(Float.valueOf(String.valueOf(cx - r * Math.sin(angle) + lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy - r * Math.cos(angle) - lineW * Math.sin(angle))));
        pathL.lineTo(Float.valueOf(String.valueOf(cx + r * Math.sin(angle) + lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy + r * Math.cos(angle) - lineW * Math.sin(angle))));
        pathL.lineTo(Float.valueOf(String.valueOf(cx + r * Math.sin(angle) - lineW * Math.cos(angle))), Float.valueOf(String.valueOf(cy + r * Math.cos(angle) + lineW * Math.sin(angle))));
        pathL.close();
        canvas.drawPath(pathR, mPaint);
        canvas.drawPath(pathL, mPaint);
    }

    private void drawCloud(Canvas canvas, float widthF, float transLateX, float transLateY) {
        canvas.drawCircle(0 + transLateX, 0 + transLateY, widthF / 8, mPaint);
        canvas.drawCircle(widthF / 16 + transLateX, 0 + transLateY, widthF / 9, mPaint);
        canvas.drawCircle(widthF / 4 + transLateX, 0 + transLateY, widthF / 6, mPaint);
        canvas.drawCircle(widthF / 12 * 5 + transLateX, 0 + transLateY, widthF / 7, mPaint);
        canvas.drawCircle(widthF / 2 + transLateX, widthF / 10 + transLateY, widthF / 16, mPaint);
        canvas.drawCircle(widthF / 12 * 5 + widthF / 10 + transLateX, 0 + transLateY, widthF / 10, mPaint);
        canvas.drawCircle(widthF / 4 * 3 - widthF / 16 + transLateX, -widthF / 32 + transLateY, widthF / 9, mPaint);
        canvas.drawCircle(widthF + transLateX, -widthF / 32 + transLateY, widthF / 4, mPaint);
    }

    private class WeatherConfig {
        private boolean haveCloud = false;
        private boolean haveSun = false;
        private boolean haveRain = false;
        private boolean haveSnow = false;
        private boolean haveHaze = false;

        private void reset() {
            this.haveHaze = false;
            this.haveCloud = false;
            this.haveSun = false;
            this.haveSnow = false;
            this.haveRain = false;
        }
    }
}
