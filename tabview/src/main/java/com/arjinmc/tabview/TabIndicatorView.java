package com.arjinmc.tabview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

/**
 * TabIndicatorView
 * Created by Eminem Lo on 2019-12-18.
 * email: arjinmc@hotmail.com
 */
public class TabIndicatorView extends View {

    private final int DEFAULT_COLOR = Color.RED;
    private final int ANIMATION_DURATION = 300;

    private final int STATUS_IDE = 0;
    private final int STATUS_SCROLLING = 1;

    /**
     * color
     */
    private int mColor = DEFAULT_COLOR;
    /**
     * total width can be draw
     */
    private int mWidth;
    /**
     * draw line width
     */
    private int mIndicatorWidth;
    /**
     * draw line thickness
     */
    private int mThickness = 1;
    /**
     * item count
     */
    private int mItemCount = 1;
    /**
     * padding horizontal/vertical
     */
    private int mPaddingHorizontal;
    /**
     * drawing status
     */
    private int mStatus = STATUS_IDE;
    /**
     * draw line position under item
     */
    private int mCurrentPosition = 0;
    /**
     * scroll to left / right animtaion
     */
    private ValueAnimator mScrollAnimation;
    /**
     * moving offset for x axis
     */
    private int mMovingOffset;
    /**
     * mark for scrolling fromPosition
     */
    private int mFromPosition;

    private Paint mPaint;

    public TabIndicatorView(Context context) {
        super(context);
        init(null);
    }

    public TabIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TabIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabIndicatorView);
        mItemCount = typedArray.getInteger(R.styleable.TabIndicatorView_TabIndicatorView_itemCount, 1);
        mThickness = typedArray.getDimensionPixelSize(R.styleable.TabIndicatorView_TabIndicatorView_thickness, 1);
        mColor = typedArray.getColor(R.styleable.TabIndicatorView_TabIndicatorView_color, DEFAULT_COLOR);
        mPaddingHorizontal = typedArray.getDimensionPixelSize(R.styleable.TabIndicatorView_TabIndicatorView_paddingHorizontal, 0);
        typedArray.recycle();

        mPaint.setStrokeWidth(mThickness);
        mPaint.setColor(mColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mWidth == 0) {
            mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            mIndicatorWidth = mWidth / mItemCount - mPaddingHorizontal * 2;
        }

        if (mStatus == STATUS_IDE) {
            int startX = getPaddingLeft() + (mPaddingHorizontal * 2 + mIndicatorWidth) * mCurrentPosition
                    + mPaddingHorizontal;
            canvas.drawLine(startX, mThickness / 2, startX + mIndicatorWidth, mThickness / 2, mPaint);
        } else if (mStatus == STATUS_SCROLLING) {
            int startX = getPaddingLeft() + (mPaddingHorizontal * 2 + mIndicatorWidth) * mFromPosition
                    + mPaddingHorizontal + mMovingOffset;
            canvas.drawLine(startX, mThickness / 2, startX + mIndicatorWidth, mThickness / 2, mPaint);
        }
    }

    /**
     * set selected position
     *
     * @param position
     */
    public void setSelectPosition(int position) {
        mCurrentPosition = position;
        postInvalidate();
    }

    /**
     * set color
     *
     * @param colorRes
     */
    public void setColorRes(@ColorRes int colorRes) {
        mColor = ContextCompat.getColor(getContext(), colorRes);
        mPaint.setColor(mColor);
    }

    /**
     * set color
     *
     * @param color
     */
    public void setColor(@ColorInt int color) {
        mColor = color;
        mPaint.setColor(mColor);
    }

    /**
     * scroll to destination position
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        startScrollAnimation(mCurrentPosition, position);
    }

    /**
     * start scrollAnimation
     *
     * @param fromPosition
     * @param toPosition
     */
    private void startScrollAnimation(int fromPosition, int toPosition) {

        if (fromPosition == toPosition) {
            return;
        }

        mFromPosition = fromPosition;
        startScrollToPosition(fromPosition, toPosition);
        mCurrentPosition = toPosition;
    }

    /**
     * start scroll to position
     *
     * @param fromPosition
     * @param toPosition
     */
    private void startScrollToPosition(int fromPosition, int toPosition) {

        int scrollLength = (mIndicatorWidth + mPaddingHorizontal * 2) * (toPosition - fromPosition);
        if (mScrollAnimation != null) {
            mScrollAnimation.removeAllUpdateListeners();
            mScrollAnimation.removeAllListeners();
        }

        mScrollAnimation = ValueAnimator.ofInt(0, scrollLength);
        mScrollAnimation.setDuration(ANIMATION_DURATION);
        mScrollAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMovingOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mScrollAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = STATUS_SCROLLING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS_IDE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mStatus = STATUS_IDE;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mScrollAnimation.start();
    }
}
