package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shichen on 2017/11/6.
 *
 * @author shichen 754314442@qq.com
 */

public class HazeView extends View {
    private Paint cloudPaint;
    private float density;

    public HazeView(Context context) {
        this(context, null);
    }

    public HazeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        initCloudPaint();
    }

    private void initCloudPaint() {
        cloudPaint = new Paint();
        cloudPaint.setAntiAlias(true);
        cloudPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0x20000000);
        //drawHaze(canvas);
    }

    private void drawHaze(Canvas canvas) {
        if (positionList.size() > 0) {
            for (int i = 0; i < positionList.size(); i++) {
                RadialGradient shader = new RadialGradient(positionList.get(i).getPointX(), positionList.get(i).getPointY(), density * 3, 0x50999999, 0x00999999, Shader.TileMode.REPEAT);
                cloudPaint.setShader(shader);
                canvas.drawCircle(positionList.get(i).getPointX(), positionList.get(i).getPointY(), density * 3, cloudPaint);
            }
        } else {
            getPosition(getWidth()*4);
        }
    }

    private Disposable mDisposable;
    private List<PointPosition> positionList = new ArrayList<>();

    /**
     * int pointX = (int) (Math.random() * getWidth());
     * int pointY = (int) (Math.random() * getHeight());
     * return new PointPosition(pointX, pointY);
     *
     * @param pointCount
     */
    private void getPosition(final long pointCount) {
        Observable.create(new ObservableOnSubscribe<PointPosition>() {
            @Override
            public void subscribe(ObservableEmitter<PointPosition> e) throws Exception {
                for (int i = 0; i < pointCount; i++) {
                    int pointX = (int) (Math.random() * getWidth());
                    int pointY = (int) (Math.random() * getHeight());
                    PointPosition pointPosition = new PointPosition(pointX, pointY);
                    positionList.add(pointPosition);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PointPosition>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(PointPosition aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        invalidate();
                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    private class PointPosition {
        private int pointX;
        private int pointY;

        public PointPosition(int pointX, int pointY) {
            this.pointX = pointX;
            this.pointY = pointY;
        }

        public int getPointX() {
            return pointX;
        }

        public void setPointX(int pointX) {
            this.pointX = pointX;
        }

        public int getPointY() {
            return pointY;
        }

        public void setPointY(int pointY) {
            this.pointY = pointY;
        }
    }
}
