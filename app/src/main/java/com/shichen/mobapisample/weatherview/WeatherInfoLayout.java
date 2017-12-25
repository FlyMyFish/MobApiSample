package com.shichen.mobapisample.weatherview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by shichen on 2017/12/25.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherInfoLayout extends LinearLayout implements NestedScrollingParent {
    private Scroller mScroller;
    private View headView;

    public WeatherInfoLayout(Context context) {
        this(context, null);
    }

    public WeatherInfoLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    private int mTopViewHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headView = getChildAt(0);
        mTopViewHeight = headView.getMeasuredHeight() / 2;
        for (int i = 0, size = getChildCount(); i < size; i++) {
            if (getChildAt(i) instanceof RecyclerView) {
                RecyclerView mRecyclerView = (RecyclerView) getChildAt(i);
                mRecyclerView.measure(widthMeasureSpec, heightMeasureSpec - mTopViewHeight);
            }
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
        Log.d("WeatherInfoLayout", "hiddenTop=" + hiddenTop + "-----mTopViewHeight=" + mTopViewHeight);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
        if (getScrollY() >= mTopViewHeight ) {
            headView.setZ(10.0f);
        } else {
            headView.setZ(0.0f);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (getScrollY() >= mTopViewHeight) {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }
}
