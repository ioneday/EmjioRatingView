package com.yongchun.emjioratingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by dee on 15/12/21.
 */
public class RatingView extends View {
    private int redColor = Color.parseColor("#e95356");
    private int blueColor = Color.parseColor("#1a8be2");
    private int greenColor = Color.parseColor("#18bd9c");
    private Paint mPaint;
    private int paintWidth = 20;
    private float moveY = 0;

    private float mInitialMotionY;

    private OnRateChangeListener onRateChangeListener;

    public RatingView(Context context) {
        super(context);
        initView();
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(blueColor);
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        mPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        mPaint.setPathEffect(new CornerPathEffect(10));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        int widthPadding = getPaddingLeft() + getPaddingRight();
        int heightPadding = getPaddingTop() + getPaddingBottom();

        int ringSize = width >= height ? height - heightPadding : width - widthPadding;
        int ringPadding = ringSize / 5;
        int lineWidth = ringSize - ringPadding * 2;


        int lineY = centerY + ringSize / 6;
        int lineBottomY = centerY + (ringSize / 2) - (ringPadding / 2);
        int lineTopY = lineY - (lineBottomY - lineY);


        float curY = lineY + moveY;
        curY = Math.max(Math.min(lineBottomY, curY), lineTopY);

        float percent = (curY - lineTopY) / (lineBottomY - lineTopY);
        getColor(percent);

        Path path = new Path();
        path.reset();
        path.moveTo(centerX - (lineWidth / 2), lineY);
        path.cubicTo(centerX - lineWidth / 3, curY, centerX + lineWidth / 3, curY, centerX + (lineWidth / 2), lineY);

        canvas.drawPath(path, mPaint);

        canvas.drawCircle(centerX, centerY, ringSize / 2 - paintWidth, mPaint);

        if(onRateChangeListener != null){
            DecimalFormat df = new DecimalFormat("0.00");
            onRateChangeListener.onChanged(Float.parseFloat(df.format(percent)));
        }
        super.onDraw(canvas);
    }


    public void getColor(float percent) {
        if (percent < 0.5f) {
            mPaint.setColor(computeColor(redColor, blueColor, percent * 2));
        } else {
            mPaint.setColor(computeColor(blueColor, greenColor, percent * 2 - 1));
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                moveY += y - mInitialMotionY;
                invalidate();
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private int computeColor(int c1, int c2, float percent) {
        int a = computeChannel((c1 >> 24) & 0xFF, (c2 >> 24) & 0xFF, percent);
        int r = computeChannel((c1 >> 16) & 0xFF, (c2 >> 16) & 0xFF, percent);
        int g = computeChannel((c1 >> 8) & 0xFF, (c2 >> 8) & 0xFF, percent);
        int b = computeChannel((c1) & 0xFF, (c2) & 0xFF, percent);
        return a << 24 | r << 16 | g << 8 | b;
    }


    private int computeChannel(int c1, int c2, float percent) {
        return c1 + (int) ((percent * (c2 - c1)) + .5);
    }

    public void setOnRateChangeListener(OnRateChangeListener onRateChangeListener) {
        this.onRateChangeListener = onRateChangeListener;
    }

    public interface OnRateChangeListener {
        void onChanged(float ratio);
    }
}
