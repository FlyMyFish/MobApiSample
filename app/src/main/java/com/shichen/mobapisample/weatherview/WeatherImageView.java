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
    private float density;
    private WeatherInfo weatherInfo;
    private WeatherConfig weatherConfig;
    private int parentTransitionX;
    private int parentTransitionY;

    public void setParentTransitionX(int parentTransitionX) {
        this.parentTransitionX = parentTransitionX;
    }

    public void setParentTransitionY(int parentTransitionY) {
        this.parentTransitionY = parentTransitionY;
    }

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
    }

    /**
     * 配置天气信息
     *
     * @param weatherInfo 天气信息
     */
    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        parseWeatherInfo();
    }

    private SunDraw sunDraw;
    private CloudDraw cloudDraw;
    private CityDraw cityDraw;

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
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.CIRCLE_SNOW:
                weatherConfig.haveCloud = true;
                weatherConfig.haveSun = true;
                weatherConfig.haveSnow = true;
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.HAZE:
                weatherConfig.haveHaze = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveSun = true;
                break;
            case WeatherType.MIDDLE_RAIN:
                weatherConfig.haveRain = true;
                weatherConfig.haveCloud = true;
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.NORMAL_RAIN:
                weatherConfig.haveRain = true;
                weatherConfig.haveCloud = true;
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.RAIN_ADD_SNOW:
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                weatherConfig.blackCloud = true;
                weatherConfig.haveSnow = true;
                break;
            case WeatherType.SMALL_CLOUD:
                weatherConfig.haveCloud = true;
                weatherConfig.haveSun = true;
                break;
            case WeatherType.SMALL_RAIN:
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                weatherConfig.blackCloud = true;
                break;
            case WeatherType.THUNDER_CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.blackCloud = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.ZERO_CIRCLE_RAIN:
                weatherConfig.haveSun = true;
                weatherConfig.blackCloud = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.ZERO_THUNDER_RAIN:
                weatherConfig.haveSun = true;

                weatherConfig.blackCloud = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveRain = true;
                break;
            case WeatherType.SMALL_SNOW:
                weatherConfig.blackCloud = true;
                weatherConfig.haveCloud = true;
                weatherConfig.haveSnow = true;
                break;
            default:
                break;
        }
        if (weatherConfig.haveSun) {
            if (sunDraw == null) {
                sunDraw = new SunDraw();
            }
        }
        if (weatherConfig.haveCloud) {
            if (cloudDraw == null) {
                cloudDraw = new CloudDraw();
            }
        }
        if (cityDraw == null) {
            cityDraw = new CityDraw();
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
            rainPointDrawList.add(new RainPointDraw());
            snowPointDrawList.add(new SnowPointDraw());
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
            if (mCanvas == null) {
                return;
            }
            mCanvas.translate(parentTransitionX, parentTransitionY);
            // SurfaceView背景
            mCanvas.drawColor(Color.parseColor("#3CA0D0"));
            if (weatherConfig.haveHaze) {
                mPaint.setColor(0xFFCCCCCC);
                if (cityDraw != null) {
                    cityDraw.setColorDark(true);
                }
            } else {
                mPaint.setColor(0xFFFFFFFF);
                if (cityDraw != null) {
                    cityDraw.setColorDark(false);
                }
            }
            if (weatherConfig.haveSun) {
                if (weatherConfig.blackCloud || weatherConfig.haveHaze) {
                    mPaint.setColor(0xFF44555F);
                    sunDraw.setColorDark(true);
                } else {
                    mPaint.setColor(0xFFFFFFFF);
                    sunDraw.setColorDark(false);
                }
                sunDraw.draw(mCanvas, getWidth(), rotate);
            }
            if (weatherConfig.haveRain) {
                drawRain(mCanvas);
            }
            if (weatherConfig.haveSnow) {
                drawSnow(mCanvas);
            }
            if (weatherConfig.haveCloud) {
                if (weatherConfig.blackCloud || weatherConfig.haveHaze) {
                    mPaint.setColor(0xFF44555F);
                    cloudDraw.setColorDark(true);
                } else {
                    mPaint.setColor(0xFFFFFFFF);
                    cloudDraw.setColorDark(false);
                }
                cloudDraw.draw(mCanvas, getWidth(), translateX, translateY);
            }
            if (cityDraw != null) {
                cityDraw.draw(mCanvas, getHeight(), -120);
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

    /**
     * 生成随机数，来表示雨滴或者雪花的x坐标位置
     */
    private List<Integer> rainX = new ArrayList<>();
    private List<RainPointDraw> rainPointDrawList = new ArrayList<>();
    private List<SnowPointDraw> snowPointDrawList = new ArrayList<>();

    private void drawRain(Canvas canvas) {
        int rainDropSpeed = 30;
        for (int i = 0; i < rainX.size(); i++) {
            rainPointDrawList.get(i).draw(canvas, getWidth() / 16, rainX.get(i), (timeTag - rainDropSpeed * i) * rainDropSpeed % getHeight());
        }
    }

    private void drawSnow(Canvas canvas) {
        int snowDropSpeed = 20;
        for (int i = 0; i < rainX.size(); i++) {
            snowPointDrawList.get(i).draw(canvas, getWidth() / 32, rainX.get(i), (timeTag - snowDropSpeed * i) * snowDropSpeed % getHeight(), density);
        }
    }

    private class WeatherConfig {
        private boolean haveCloud = false;
        private boolean haveSun = false;
        private boolean haveRain = false;
        private boolean haveSnow = false;
        private boolean haveHaze = false;
        private boolean blackCloud = false;

        private void reset() {
            this.haveHaze = false;
            this.haveCloud = false;
            this.haveSun = false;
            this.haveSnow = false;
            this.haveRain = false;
            this.blackCloud = false;
        }
    }

    private static class SunDraw {
        private Paint mPaint;
        private boolean colorDark = false;

        private void setColorDark(boolean colorDark) {
            this.colorDark = colorDark;
        }

        private SunDraw() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void draw(Canvas canvas, float widthF, double rotate) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(0xFFFFFFFF);
            }
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
    }

    private static class CloudDraw {
        private Paint mPaint;
        private boolean colorDark = false;

        private void setColorDark(boolean colorDark) {
            this.colorDark = colorDark;
        }

        private CloudDraw() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void draw(Canvas canvas, float widthF, float transLateX, float transLateY) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(0xFFFFFFFF);
            }
            canvas.drawCircle(0 + transLateX, 0 + transLateY, widthF / 8, mPaint);
            canvas.drawCircle(widthF / 16 + transLateX, 0 + transLateY, widthF / 9, mPaint);
            canvas.drawCircle(widthF / 4 + transLateX, 0 + transLateY, widthF / 6, mPaint);
            canvas.drawCircle(widthF / 12 * 5 + transLateX, 0 + transLateY, widthF / 7, mPaint);
            canvas.drawCircle(widthF / 2 + transLateX, widthF / 10 + transLateY, widthF / 16, mPaint);
            canvas.drawCircle(widthF / 12 * 5 + widthF / 10 + transLateX, 0 + transLateY, widthF / 10, mPaint);
            canvas.drawCircle(widthF / 4 * 3 - widthF / 16 + transLateX, -widthF / 32 + transLateY, widthF / 9, mPaint);
            canvas.drawCircle(widthF + transLateX, -widthF / 32 + transLateY, widthF / 4, mPaint);
        }
    }

    private static class RainPointDraw {
        private Paint mPaint;
        private boolean colorDark = false;

        private RainPointDraw() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAlpha(180);
        }

        private void draw(Canvas canvas, float totalH, float xF, float yF) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(0xFFFFFFFF);
            }
            float radiusF = totalH / 8;
            RectF rectF = new RectF();
            rectF.set(xF, yF + totalH - radiusF * 2, xF + radiusF * 2, yF + totalH);
            canvas.drawArc(rectF, 180, -180, false, mPaint);
            Path path = new Path();
            path.moveTo(xF + radiusF, yF);
            path.lineTo(xF, yF + totalH - radiusF);
            path.lineTo(xF + radiusF * 2, yF + totalH - radiusF);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    private static class SnowPointDraw {
        private Paint mPaint;
        private boolean colorDark = false;

        private SnowPointDraw() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAlpha(180);
        }

        private void draw(Canvas canvas, float totalH, float xF, float yF, float lineW) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(0xFFFFFFFF);
            }
            float r = totalH / 2;
            float cx = totalH / 2 + xF;
            float cy = totalH / 2 + yF;
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
    }

    private static class CityDraw {
        private Paint mPaint;
        private boolean colorDark = false;

        private void setColorDark(boolean colorDark) {
            this.colorDark = colorDark;
        }

        private CityDraw() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void draw(Canvas canvas, float heightF, int startX) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(0xFFFFFFFF);
            }
            Path path = new Path();
            path.moveTo(startX, heightF + 100.0f);
            path.lineTo(startX, heightF - 420.0f);
            path.lineTo(startX + 20, heightF - 420.0f);
            path.lineTo(startX + 20, heightF - 420.0f - 40.0f);
            path.lineTo(startX + 20 + 20, heightF - 420.0f - 40.0f);
            path.lineTo(startX + 20 + 20, heightF - 420.0f - 40.0f - 50.0f);
            path.lineTo(startX + 20 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f);
            path.lineTo(startX + 20 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f - 20.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f - 20.0f - 30.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40, heightF - 420.0f - 40.0f - 50.0f - 20.0f - 30.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20, heightF - 420.0f - 40.0f - 50.0f - 20.0f - 30.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f - 20.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20, heightF - 420.0f - 40.0f - 50.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20, heightF - 420.0f - 40.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20, heightF - 420.0f - 40.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20, heightF - 420.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20, heightF - 420.0f);
            path.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20, heightF + 100.0f);
            path.close();
            canvas.drawPath(path, mPaint);
            Path build2 = new Path();
            build2.moveTo(startX + 20 + 20 + 20 + 20 + 40 + 40, heightF + 100.0f);
            build2.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 40, heightF - 210.0f);
            build2.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 40 + 240, heightF - 210.0f);
            build2.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 40 + 240, heightF + 100.0f);
            build2.close();
            canvas.drawPath(build2, mPaint);
            Path build3 = new Path();
            build3.moveTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40, heightF + 100.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40, heightF - 240.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15, heightF - 240.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15, heightF - 260.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20, heightF - 260.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20, heightF - 280.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20 + 30, heightF - 280.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20 + 30, heightF - 260.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20 + 30 + 20, heightF - 260.0f);
            build3.lineTo(startX + 20 + 20 + 20 + 20 + 40 + 20 + 20 + 20 + 20 + 20 + 40 + 15 + 20 + 30 + 20, heightF + 100.0f);
            build3.close();
            canvas.drawPath(build3, mPaint);
            Path build4 = new Path();
            build4.moveTo(startX + 385, heightF + 100.0f);
            build4.lineTo(startX + 385, heightF - 240.0f);
            build4.lineTo(startX + 385 + 20, heightF - 240.0f);
            build4.lineTo(startX + 385 + 20, heightF - 240.0f - 120.0f);
            build4.lineTo(startX + 385 + 20 + 40, heightF - 240.0f - 120.0f);
            build4.lineTo(startX + 385 + 20 + 40, heightF - 240.0f - 120.0f - 80.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20, heightF - 240.0f - 120.0f - 80.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30, heightF - 240.0f - 120.0f - 80.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30, heightF - 240.0f - 120.0f - 80.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30, heightF - 240.0f - 120.0f - 80.0f - 30.0f - 40.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120, heightF - 240.0f - 120.0f - 80.0f - 30.0f - 40.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120, heightF - 240.0f - 120.0f - 80.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80, heightF - 240.0f - 120.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80, heightF - 240.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20, heightF - 240.0f - 30.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20, heightF - 250.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20 + 10, heightF - 250.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20 + 10, heightF - 150.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20 + 10 + 60, heightF - 150.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20 + 10 + 60, heightF - 120.0f);
            build4.lineTo(startX + 385 + 20 + 40 + 20 + 30 + 30 + 120 + 80 + 20 + 10 + 60, heightF + 100.0f);
            //815
            build4.close();
            canvas.drawPath(build4, mPaint);
            Path build5 = new Path();
            build5.moveTo(startX + 755, heightF + 100.0f);
            build5.lineTo(startX + 755, heightF - 100.0f);
            build5.lineTo(startX + 755 + 160, heightF - 100.0f);
            build5.lineTo(startX + 755 + 160, heightF + 100.0f);
            build2.close();
            canvas.drawPath(build5, mPaint);
            Path build6 = new Path();
            build6.moveTo(startX + 815 + 40, heightF + 100.0f);
            build6.lineTo(startX + 815 + 40, heightF - 135.0f);
            build6.lineTo(startX + 815 + 40 + 50, heightF - 135.0f);
            build6.lineTo(startX + 815 + 40 + 50, heightF - 135.0f - 450.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20, heightF - 135.0f - 450.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20, heightF - 135.0f - 450.0f - 90.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80, heightF - 135.0f - 450.0f - 90.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80, heightF - 135.0f - 450.0f - 90.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10, heightF - 135.0f - 450.0f - 90.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f - 15.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f - 15.0f - 15.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f - 15.0f - 15.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f - 15.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f - 15.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10, heightF - 135.0f - 450.0f - 90.0f - 20.0f - 20.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10, heightF - 135.0f - 450.0f - 90.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30, heightF - 135.0f - 450.0f - 90.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30, heightF - 135.0f - 450.0f - 90.0f - 120.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20, heightF - 135.0f - 450.0f - 90.0f - 120.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f - 40.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f - 40.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f - 30.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f - 70.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30 + 90, heightF - 135.0f - 450.0f - 90.0f - 120.0f - 200.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30 + 90, heightF - 135.0f - 450.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30 + 90 + 250, heightF - 135.0f - 450.0f);
            build6.lineTo(startX + 815 + 40 + 50 + 20 + 80 - 10 + 40 + 20 + 120 + 20 + 10 + 30 + 20 + 60 + 40 + 40 + 120 + 40 + 30 + 90 + 250, heightF + 100.0f);
            build6.close();
            canvas.drawPath(build6, mPaint);
        }
    }
}
