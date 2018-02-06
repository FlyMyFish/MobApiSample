package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.bean.WeatherType;

import java.util.ArrayList;
import java.util.Calendar;
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

public class WeatherImageSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    /**
     * SurfaceHolder是SurfaceView控制器，用来操作SurfaceView
     */
    private SurfaceHolder mHolder;
    private boolean mIsDrawing;
    private Paint mPaint;
    private float radius;
    private int timeTag;
    private float density;
    private WeatherConfig weatherConfig;
    private int parentTransitionX;
    private int parentTransitionY;

    private int airPolluteColor = 0xFFFFFFFF;

    private float screenScale = 0.4f;
    private float screenHeight;
    Calendar c = Calendar.getInstance();

    public void setParentTransitionX(int parentTransitionX) {
        this.parentTransitionX = parentTransitionX;
    }

    public void setParentTransitionY(int parentTransitionY) {
        this.parentTransitionY = parentTransitionY;
    }

    public WeatherImageSurfaceView(Context context) {
        this(context, null);
    }

    public WeatherImageSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        weatherConfig = new WeatherConfig();
        density = context.getResources().getDisplayMetrics().density;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        radius = density * 4;
    }

    private void initView() {
        setZOrderOnTop(true);
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
        parseWeatherInfo(weatherInfo, weatherConfig);
        if (weatherInfo == null) {
            return;
        }
        airPolluteColor = parseColor(weatherInfo.getResult().get(0).getAirCondition());
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
        if (weatherConfig.haveHaze) {
            if (hazeDraw == null) {
                hazeDraw = new HazeDraw(getWidth(), getHeight());
            }
        }
        if (cityDraw == null) {
            cityDraw = new CityDraw(parseSunColor(c.get(Calendar.HOUR_OF_DAY)));
        }
        skyDraw = new SkyDraw(parseSunColor(c.get(Calendar.HOUR_OF_DAY)));
    }

    private SkyDraw skyDraw;
    private SunDraw sunDraw;
    private CloudDraw cloudDraw;
    private CityDraw cityDraw;
    private HazeDraw hazeDraw;

    /**
     * 解析天气信息结果用WeatherInfo表现
     */
    private void parseWeatherInfo(WeatherInfo weatherInfo, WeatherConfig weatherConfig) {
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
        weatherConfig.haveHaze = !(weatherInfo.getResult().get(0).getAirCondition().equals(WeatherInfo.WeatherBean.AIR_CONDITION_YOU) ||
                weatherInfo.getResult().get(0).getAirCondition().equals(WeatherInfo.WeatherBean.AIR_CONDITION_LIANG));
    }

    /**
     * 所有指数的颜色
     */
    public int[] arcColors = new int[]{0xFF0289c3,
            0xFF36BBCE,
            0xFF36BBCE,
            0xFF698dc3,
            0xFF698dc3,
            0xFF8fa3c2};

    public int parseColor(String airCondition) {
        if (airCondition.equals(WeatherInfo.WeatherBean.AIR_CONDITION_YOU)) {
            return arcColors[1];
        } else if (airCondition.equals(WeatherInfo.WeatherBean.AIR_CONDITION_LIANG)) {
            return arcColors[2];
        } else if (airCondition.equals(WeatherInfo.WeatherBean.AIR_CONDITION_MILD_POLLUTE)) {
            return arcColors[3];
        } else if (airCondition.equals(WeatherInfo.WeatherBean.AIR_CONDITION_MODERATE_POLLUTE)) {
            return arcColors[4];
        } else if (airCondition.equals(WeatherInfo.WeatherBean.AIR_CONDITION_SERIOUS_POLLUTE)) {
            return arcColors[5];
        } else {
            return 0xFFFFFFFF;
        }
    }

    public int parseColor(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            if (weatherInfo.getResult() != null) {
                if (weatherInfo.getResult().get(0) != null) {
                    WeatherConfig weatherConfigN = new WeatherConfig();
                    parseWeatherInfo(weatherInfo, weatherConfigN);
                    if (weatherConfigN.blackCloud || weatherConfigN.haveHaze) {
                        return 0xFF44555F;
                    } else {
                        if (!TextUtils.isEmpty(weatherInfo.getResult().get(0).getAirCondition())) {
                            return parseColor(weatherInfo.getResult().get(0).getAirCondition());
                        } else {
                            return 0xFFFFFFFF;
                        }
                    }
                } else {
                    return 0xFFFFFFFF;
                }
            } else {
                return 0xFFFFFFFF;
            }
        } else {
            return 0xFFFFFFFF;
        }
    }

    private ExecutorService singleThreadPool;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenScale = getHeight() / screenHeight;
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
        try {
            Thread.sleep(20);
            while (mIsDrawing) {
                draw();
                timeTag++;
                rotate = rotate + 0.005;
                translateX = radius - Float.valueOf(String.valueOf(Math.cos((Math.PI * 2 * timeTag / 300)) * radius));
                translateY = Float.valueOf(String.valueOf(Math.sin(Math.PI * 2 * timeTag / 300) * radius));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Canvas mCanvas;

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        try {
            if (mCanvas == null) {
                return;
            }
            mCanvas.translate(parentTransitionX, parentTransitionY);
            // SurfaceView背景
            skyDraw.draw(mCanvas, getWidth(), getHeight());
            if (weatherConfig.blackCloud || weatherConfig.haveHaze) {
                skyDraw.setColorDark(true);
            } else {
                skyDraw.setColorDark(false);
            }
            if (weatherConfig.haveSun) {
                if (weatherConfig.blackCloud || weatherConfig.haveHaze) {
                    mPaint.setColor(0xFF44555F);
                    sunDraw.setColorDark(true);
                } else {
                    mPaint.setColor(0xFFFFFFFF);
                    sunDraw.setColorDark(false);
                }
                sunDraw.setAirPaintColor(airPolluteColor);
                sunDraw.draw(mCanvas, getWidth(), rotate);
            }
            {
                if (weatherConfig.blackCloud || weatherConfig.haveHaze) {
                    mPaint.setColor(0xFF44555F);
                    cityDraw.setColorDark(true);
                } else {
                    mPaint.setColor(0xFFFFFFFF);
                    cityDraw.setColorDark(false);
                }
                cityDraw.setAirPaintColor(airPolluteColor);
                cityDraw.draw(mCanvas, getHeight() + 20, -120, screenScale);
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
                cloudDraw.setAirPaintColor(airPolluteColor);
                cloudDraw.draw(mCanvas, getWidth(), translateX, translateY);
            }
            if (weatherConfig.haveHaze) {
                hazeDraw.draw(mCanvas, timeTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mHolder.unlockCanvasAndPost(mCanvas);
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
            rainPointDrawList.get(i).setAirPaintColor(airPolluteColor).draw(canvas, getWidth() / 16, rainX.get(i), (timeTag - rainDropSpeed * i) * rainDropSpeed % getHeight());
        }
    }

    private void drawSnow(Canvas canvas) {
        int snowDropSpeed = 20;
        for (int i = 0; i < rainX.size(); i++) {
            snowPointDrawList.get(i).setAirPaintColor(airPolluteColor).draw(canvas, getWidth() / 32, rainX.get(i), (timeTag - snowDropSpeed * i) * snowDropSpeed % getHeight(), density);
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
        private int airPaintColor = 0xFFFFFFFF;
        Calendar c;

        public void setAirPaintColor(int airPaintColor) {
            this.airPaintColor = airPaintColor;
        }

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
                mPaint.setColor(airPaintColor);
            }
            c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            mPaint.setColor(parseSunColor(hour));
            final int sunLineCount = 12;
            float lineW = widthF / 12 / 12;
            float lineH = widthF / 4 / 8;
            float cx = widthF / 8 + widthF / 3 * 2 * (Math.abs((float) hour - 7.0f) / 12);
            Log.d("SunDraw", "cx=" + cx);
            float cy = widthF / 8 + widthF / 4 * (Math.abs(12.0f - (float) hour) / 6);
            float rb = widthF / 8;
            float rs = widthF / 8 - lineH;
            canvas.drawCircle(cx, cy, widthF / 12, mPaint);
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


    private static int parseSunColor(int hour) {
        if (hour > 7 && hour <= 10) {
            return 0xFFFFD467;
        } else if (hour > 10 && hour < 17) {
            return 0xFFFEE895;
        } else {
            return 0xFFFF6633;
        }
    }

    private static class CloudDraw {
        private Paint mPaint;
        private boolean colorDark = false;
        private int airPaintColor = 0xFFFFFFFF;

        public void setAirPaintColor(int airPaintColor) {
            this.airPaintColor = airPaintColor;
        }

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
                mPaint.setColor(airPaintColor);
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
        private int airPaintColor = 0xFFFFFFFF;

        public RainPointDraw setAirPaintColor(int airPaintColor) {
            this.airPaintColor = airPaintColor;
            return this;
        }

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
                mPaint.setColor(airPaintColor);
            }
            float radiusF = totalH / 12;
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
        private int airPaintColor = 0xFFFFFFFF;

        public SnowPointDraw setAirPaintColor(int airPaintColor) {
            this.airPaintColor = airPaintColor;
            return this;
        }

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
        private int airPaintColor = 0xFFFFFFFF;
        private int hourColor;

        public void setAirPaintColor(int airPaintColor) {
            this.airPaintColor = airPaintColor;
        }

        private void setColorDark(boolean colorDark) {
            this.colorDark = colorDark;
        }

        private CityDraw(int hourColor) {
            this.hourColor = hourColor;
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.WHITE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void draw(Canvas canvas, float heightF, int startX, float screenScale) {
            if (colorDark) {
                mPaint.setColor(0xFF44555F);
            } else {
                mPaint.setColor(airPaintColor);
            }
            Path path = new Path();
            path.moveTo(startX, heightF + 100.0f * screenScale);
            path.lineTo(startX, heightF - 420.0f * screenScale);
            path.lineTo(startX + 20, heightF - 420.0f * screenScale);
            path.lineTo(startX + 20, heightF - 460.0f * screenScale);
            path.lineTo(startX + 40, heightF - 460.0f * screenScale);
            path.lineTo(startX + 40, heightF - 510.0f * screenScale);
            path.lineTo(startX + 60, heightF - 510.0f * screenScale);
            path.lineTo(startX + 60, heightF - 530.0f * screenScale);
            path.lineTo(startX + 80, heightF - 560.0f * screenScale);
            path.lineTo(startX + 120, heightF - 560.0f * screenScale);
            path.lineTo(startX + 140, heightF - 560.0f * screenScale);
            path.lineTo(startX + 80 + 80, heightF - 530.0f * screenScale);
            path.lineTo(startX + 80 + 80, heightF - 510.0f * screenScale);
            path.lineTo(startX + 80 + 100, heightF - 510.0f * screenScale);
            path.lineTo(startX + 80 + 100, heightF - 460.0f * screenScale);
            path.lineTo(startX + 80 + 120, heightF - 460.0f * screenScale);
            path.lineTo(startX + 80 + 120, heightF - 420.0f * screenScale);
            path.lineTo(startX + 80 + 140, heightF - 420.0f * screenScale);
            path.lineTo(startX + 80 + 140, heightF + 100.0f * screenScale);
            path.close();
            canvas.drawPath(path, mPaint);
            Path build2 = new Path();
            build2.moveTo(startX + 160, heightF + 100.0f * screenScale);
            build2.lineTo(startX + 160, heightF - 210.0f * screenScale);
            build2.lineTo(startX + 400, heightF - 210.0f * screenScale);
            build2.lineTo(startX + 400, heightF + 100.0f * screenScale);
            build2.close();
            canvas.drawPath(build2, mPaint);
            Path build3 = new Path();
            build3.moveTo(startX + 260, heightF + 100.0f * screenScale);
            build3.lineTo(startX + 260, heightF - 240.0f * screenScale);
            build3.lineTo(startX + 275, heightF - 240.0f * screenScale);
            build3.lineTo(startX + 275, heightF - 260.0f * screenScale);
            build3.lineTo(startX + 295, heightF - 260.0f * screenScale);
            build3.lineTo(startX + 295, heightF - 280.0f * screenScale);
            build3.lineTo(startX + 325, heightF - 280.0f * screenScale);
            build3.lineTo(startX + 325, heightF - 260.0f * screenScale);
            build3.lineTo(startX + 345, heightF - 260.0f * screenScale);
            build3.lineTo(startX + 345, heightF + 100.0f * screenScale);
            build3.close();
            canvas.drawPath(build3, mPaint);
            Path build4 = new Path();
            build4.moveTo(startX + 385, heightF + 100.0f * screenScale);
            build4.lineTo(startX + 385, heightF - 240.0f * screenScale);
            build4.lineTo(startX + 405, heightF - 240.0f * screenScale);
            build4.lineTo(startX + 405, heightF - 360.0f * screenScale);
            build4.lineTo(startX + 445, heightF - 360.0f * screenScale);
            build4.lineTo(startX + 445, heightF - 440.0f * screenScale);
            build4.lineTo(startX + 465, heightF - 440.0f * screenScale);
            build4.lineTo(startX + 495, heightF - 470.0f * screenScale);
            build4.lineTo(startX + 525, heightF - 470.0f * screenScale);
            build4.lineTo(startX + 525, heightF - 510.0f * screenScale);
            build4.lineTo(startX + 645, heightF - 510.0f * screenScale);
            build4.lineTo(startX + 645, heightF - 470.0f * screenScale);
            build4.lineTo(startX + 725, heightF - 390.0f * screenScale);
            build4.lineTo(startX + 725, heightF - 270.0f * screenScale);
            build4.lineTo(startX + 745, heightF - 270.0f * screenScale);
            build4.lineTo(startX + 745, heightF - 250.0f * screenScale);
            build4.lineTo(startX + 755, heightF - 250.0f * screenScale);
            build4.lineTo(startX + 755, heightF - 150.0f * screenScale);
            build4.lineTo(startX + 815, heightF - 150.0f * screenScale);
            build4.lineTo(startX + 815, heightF - 120.0f * screenScale);
            build4.lineTo(startX + 815, heightF + 100.0f * screenScale);
            //815
            build4.close();
            canvas.drawPath(build4, mPaint);
            Path build5 = new Path();
            build5.moveTo(startX + 755, heightF + 100.0f * screenScale);
            build5.lineTo(startX + 755, heightF - 100.0f * screenScale);
            build5.lineTo(startX + 915, heightF - 100.0f * screenScale);
            build5.lineTo(startX + 915, heightF + 100.0f * screenScale);
            build2.close();
            canvas.drawPath(build5, mPaint);
            Path build6 = new Path();
            build6.moveTo(startX + 855, heightF + 100.0f * screenScale);
            build6.lineTo(startX + 855, heightF - 135.0f * screenScale);
            build6.lineTo(startX + 905, heightF - 135.0f * screenScale);
            build6.lineTo(startX + 905, heightF - 585.0f * screenScale);
            build6.lineTo(startX + 925, heightF - 585.0f * screenScale);
            build6.lineTo(startX + 925, heightF - 675.0f * screenScale);
            build6.lineTo(startX + 1005, heightF - 675.0f * screenScale);
            build6.lineTo(startX + 1005, heightF - 695.0f * screenScale);
            build6.lineTo(startX + 995, heightF - 695.0f * screenScale);
            build6.lineTo(startX + 995, heightF - 715.0f * screenScale);
            build6.lineTo(startX + 1035, heightF - 715.0f * screenScale);
            build6.lineTo(startX + 1035, heightF - 730.0f * screenScale);
            build6.lineTo(startX + 1055, heightF - 745.0f * screenScale);
            build6.lineTo(startX + 1175, heightF - 745.0f * screenScale);
            build6.lineTo(startX + 1175, heightF - 730.0f * screenScale);
            build6.lineTo(startX + 1195, heightF - 730.0f * screenScale);
            build6.lineTo(startX + 1195, heightF - 715.0f * screenScale);
            build6.lineTo(startX + 1205, heightF - 715.0f * screenScale);
            build6.lineTo(startX + 1205, heightF - 715.0f * screenScale);
            build6.lineTo(startX + 1205, heightF - 675.0f * screenScale);
            build6.lineTo(startX + 1235, heightF - 675.0f * screenScale);
            build6.lineTo(startX + 1235, heightF - 795.0f * screenScale);
            build6.lineTo(startX + 1255, heightF - 795.0f * screenScale);
            build6.lineTo(startX + 1255, heightF - 995.0f * screenScale);
            build6.lineTo(startX + 1315, heightF - 995.0f * screenScale);
            build6.lineTo(startX + 1315, heightF - 1065.0f * screenScale);
            build6.lineTo(startX + 1355, heightF - 1095.0f * screenScale);
            build6.lineTo(startX + 1395, heightF - 1095.0f * screenScale);
            build6.lineTo(startX + 1395, heightF - 1135.0f * screenScale);
            build6.lineTo(startX + 1515, heightF - 1135.0f * screenScale);
            build6.lineTo(startX + 1515, heightF - 1095.0f * screenScale);
            build6.lineTo(startX + 1555, heightF - 1095.0f * screenScale);
            build6.lineTo(startX + 1585, heightF - 1065.0f * screenScale);
            build6.lineTo(startX + 1585, heightF - 995.0f * screenScale);
            build6.lineTo(startX + 1675, heightF - 995.0f * screenScale);
            build6.lineTo(startX + 1675, heightF - 585.0f * screenScale);
            build6.lineTo(startX + 1925, heightF - 585.0f * screenScale);
            build6.lineTo(startX + 1925, heightF + 100.0f * screenScale);
            build6.close();
            canvas.drawPath(build6, mPaint);
        }
    }

    private static class SkyDraw {
        private Paint mPaint;
        private int hourColor;
        private boolean colorDark = false;

        public void setColorDark(boolean colorDark) {
            this.colorDark = colorDark;
        }

        private SkyDraw(int hourColor) {
            this.hourColor = hourColor;
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void draw(Canvas canvas, int width, int height) {
            int skyTopColor, skyBottomColor;
            if (colorDark) {
                skyTopColor = 0xFF44555F;
                skyBottomColor = 0xFFb8b8b8;
            } else {
                skyTopColor = 0xFF3CA0D0;
                skyBottomColor = 0xFFb2cedb;
            }
            Rect rect = new Rect(-width / 10, -height / 10, width + width / 10, height + height / 10);
            LinearGradient linearGradient = new LinearGradient(-width / 10, -height / 10, 0, height + height / 10, skyTopColor, skyBottomColor, Shader.TileMode.CLAMP);
            mPaint.setShader(linearGradient);
            canvas.drawRect(rect, mPaint);
        }
    }

    private static class HazeDraw {
        private Paint mPaint;
        /*private List<Integer> positionXList;
        private List<Integer> positionYList;
        private List<Integer> lengthList;
        private List<Integer> speedList;*/
        private int width;
        private int height;

        private HazeDraw(int width, int height) {
            this.width = width;
            this.height = height;
            /*positionXList = new ArrayList<>();
            positionYList = new ArrayList<>();
            lengthList = new ArrayList<>();
            speedList = new ArrayList<>();*/
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            /*Random random = new Random();
            for (int i = 0; i < 28; i++) {
                positionXList.add(random.nextInt(width + width / 6) % (width + width / 6 + width / 6 + 1) - width / 6);
                positionYList.add(random.nextInt(height / 4 * 3));
                lengthList.add(random.nextInt(width / 8) % (width / 8 - width / 20 + 1) + width / 20);
                speedList.add(random.nextInt(2) + 1);
            }*/
        }

        private void draw(Canvas canvas, int timeTag) {
            int heightL = height / 60;
            Rect rect = new Rect(-width / 10, -height / 10, width + width / 10, height + height / 10);
            mPaint.setColor(0xbb333333);
            canvas.drawRect(rect, mPaint);
            /*canvas.save();
            mPaint.setColor(0xbb999999);
            for (int i = 0; i < 28; i++) {
                RectF rectF = new RectF((positionXList.get(i) + timeTag * speedList.get(i)) % (width + width / 3) - width / 5, positionYList.get(i), (positionXList.get(i) + lengthList.get(i) + timeTag * speedList.get(i)) % (width + width / 3) - width / 5, positionYList.get(i) + heightL);
                canvas.drawRoundRect(rectF, heightL / 2, heightL / 2, mPaint);
            }*/
        }
    }
}
