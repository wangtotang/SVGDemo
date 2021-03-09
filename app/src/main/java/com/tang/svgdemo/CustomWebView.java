package com.tang.svgdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomWebView extends WebView {

    float lastX = 0;
    float lastY = 0;
    long lastTime = 0L;

    public CustomWebView(@NonNull Context context) {
        super(context);
    }

    public CustomWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getContentWidth() {
        return super.computeHorizontalScrollRange();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - lastX;
                int scrollX = getScrollX();
                if (deltaX < 0) {
                    int contentWidth = getContentWidth();
                    if (contentWidth - scrollX <= getWidth()) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else {
                    if (scrollX == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
        }
        lastX = x;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastTime < 300) {
                    lastTime = currentTime;
                    return true;
                } else {
                    lastTime = currentTime;
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
