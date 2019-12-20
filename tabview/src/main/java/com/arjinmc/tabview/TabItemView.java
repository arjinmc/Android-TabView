package com.arjinmc.tabview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

/**
 * TabItemView
 * Created by Eminem Lo on 2019-12-18.
 * email: arjinmc@hotmail.com
 */
public class TabItemView extends RelativeLayout {

    private final int DEFAULT_MAX_NUMBER = 99;

    private TextView mTvTitle, mTvNumber;

    private int mMaxNumber = DEFAULT_MAX_NUMBER;
    /**
     * when number is beyond max number to show. default values is max number+
     */
    private String mMaxNumberText;

    private TabView mParent;

    public TabItemView(@NonNull Context context, TabView tabView) {
        super(context);
        mParent = tabView;
        init();
    }

    private void init() {

        if (mParent == null) {
            try {
                throw new IllegalAccessException("use TabView.newItem() to create");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        inflate(getContext(), R.layout.tabview_item_view, this);
        mTvTitle = findViewById(R.id.tabview_tv_title);
        mTvNumber = findViewById(R.id.tabview_tv_number);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(true);
                mParent.scrollToItem(getId());
            }
        });
    }

    /**
     * set title
     *
     * @param title
     */
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * set title
     *
     * @param titleRes
     */
    public void setTitle(@StringRes int titleRes) {
        mTvTitle.setText(titleRes);
    }

    /**
     * set title text color
     *
     * @param colorStateList
     */
    public void setTitleTextColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        mTvTitle.setTextColor(colorStateList);
    }

    /**
     * set title text color
     *
     * @param colorRes
     */
    public void setTitleTextColor(@ColorRes int colorRes) {
        mTvTitle.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * set title text size
     *
     * @param textSize
     */
    public void setTitleTextSize(float textSize) {
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * set title text size
     *
     * @param textSizeRes
     */
    public void setTitleTextSize(@DimenRes int textSizeRes) {
        mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(textSizeRes));
    }

    /**
     * set number
     *
     * @param number
     */
    public void setNumber(int number) {
        if (number <= 0) {
            mTvNumber.setVisibility(View.GONE);
        } else {
            mTvNumber.setVisibility(View.VISIBLE);
            if (number <= mMaxNumber) {
                mTvNumber.setText(number + "");
            } else if (TextUtils.isEmpty(mMaxNumberText)) {
                mTvNumber.setText(mMaxNumber + "+");
            } else {
                mTvNumber.setText(mMaxNumberText);
            }
        }
    }

    /**
     * set number text color
     *
     * @param colorRes
     */
    public void setNumberTextColor(@ColorRes int colorRes) {
        mTvNumber.setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    /**
     * set number text color
     *
     * @param colorStateList
     */
    public void setNumberTextColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        mTvNumber.setTextColor(colorStateList);
    }

    /**
     * set number text color
     *
     * @param textSize
     */
    public void setNumberTextSize(float textSize) {
        mTvNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * set number text size
     *
     * @param textSizeRes
     */
    public void setNumberTextSize(@DimenRes int textSizeRes) {
        mTvNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(textSizeRes));
    }

    /**
     * set horizontal padding
     *
     * @param padding
     */
    public void setNumberPaddingHorizontal(int padding) {
        mTvNumber.setPadding(padding, mTvNumber.getPaddingTop(), padding, mTvNumber.getPaddingBottom());
    }

    /**
     * set horizontal padding
     *
     * @param paddingRes
     */
    public void setNumberPaddingHorizontalRes(@DimenRes int paddingRes) {
        int padding = getResources().getDimensionPixelSize(paddingRes);
        mTvNumber.setPadding(padding, mTvNumber.getPaddingTop(), padding, mTvNumber.getPaddingBottom());
    }

    /**
     * set vertical padding
     *
     * @param padding
     */
    public void setNumberPaddingVertical(int padding) {
        mTvNumber.setPadding(mTvNumber.getPaddingLeft(), padding, mTvNumber.getPaddingRight(), padding);
    }

    /**
     * set vertical padding
     *
     * @param paddingRes
     */
    public void setNumberPaddingVerticalRes(@DimenRes int paddingRes) {
        int padding = getResources().getDimensionPixelSize(paddingRes);
        mTvNumber.setPadding(mTvNumber.getPaddingLeft(), padding, mTvNumber.getPaddingRight(), padding);
    }

    /**
     * set number background
     *
     * @param drawable
     */
    public void setNumberBackground(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTvNumber.setBackground(drawable);
        } else {
            mTvNumber.setBackgroundDrawable(drawable);
        }
    }

    /**
     * set number background
     *
     * @param drawableRes
     */
    public void setNumberBackground(@DrawableRes int drawableRes) {
        mTvNumber.setBackgroundResource(drawableRes);
    }

    /**
     * set number text margin
     *
     * @param marginLeft
     * @param marginTop
     */
    public void setNumberMargin(int marginLeft, int marginTop) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mTvNumber.getLayoutParams();
        layoutParams.leftMargin = marginLeft;
        layoutParams.topMargin = marginTop;
        mTvNumber.setLayoutParams(layoutParams);
    }

    /**
     * set number text margin
     *
     * @param marginLeftRes
     * @param marginTopRes
     */
    public void setNumberMarginRes(@DimenRes int marginLeftRes, @DimenRes int marginTopRes) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mTvNumber.getLayoutParams();
        layoutParams.leftMargin = getResources().getDimensionPixelSize(marginLeftRes);
        layoutParams.topMargin = getResources().getDimensionPixelSize(marginTopRes);
        mTvNumber.setLayoutParams(layoutParams);
    }

    /**
     * set number
     *
     * @param maxNumber
     */
    public void setMaxNumber(int maxNumber) {
        mMaxNumber = maxNumber;
    }

    /**
     * when number is  beyond max number
     *
     * @param maxNumberText
     */
    public void setMaxNumberText(String maxNumberText) {
        mMaxNumberText = maxNumberText;
    }

    /**
     * when number is  beyond max number
     *
     * @param maxNumberTextRes
     */
    public void setMaxNumberText(@StringRes int maxNumberTextRes) {
        mMaxNumberText = getResources().getString(maxNumberTextRes);
    }

    /**
     * set show dot or not
     *
     * @param show
     */
    public void setShowDot(boolean show) {
        if (show) {
            mTvNumber.setVisibility(View.VISIBLE);
        } else {
            mTvNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mTvTitle.setSelected(selected);
        mTvNumber.setSelected(selected);
    }
}
