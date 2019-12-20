package com.arjinmc.tabview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * TabView
 * Created by Eminem Lo on 2019-12-18.
 * email: arjinmc@hotmail.com
 */
public class TabView extends LinearLayout {

    /**
     * title text color
     */
    private ColorStateList mTitleTextColor;
    /**
     * title text size
     */
    private float mTitleTextSize;
    /**
     * number text color
     */
    private ColorStateList mNumberTextColor;
    /**
     * number text size
     */
    private float mNumberTextSize;
    /**
     * number text padding horizontal/vertical
     */
    private int mNumberPaddingHorizontal, mNumberPaddingVertical;
    /**
     * number background drawable
     */
    private Drawable mNumberBackgroundDrawable;
    /**
     * number margin top/left
     */
    private int mNumberMarginTop, mNumberMarginLeft;

    private List<TabItemView> mTabItemViewList;

    private OnSelectedChangeListener mOnSelectedChangeListener;

    private TabIndicatorView mTabIndicatorView;
    private int mSelectedItemId = -1;

    public TabView(Context context) {
        super(context);
        init(null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        setOrientation(LinearLayout.HORIZONTAL);

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabView);
        mTitleTextColor = typedArray.getColorStateList(R.styleable.TabView_TabView_tabTitleTextColor);
        mTitleTextSize = typedArray.getDimension(R.styleable.TabView_TabView_tabTitleTextSize
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_title_text_size));

        mNumberTextColor = typedArray.getColorStateList(R.styleable.TabView_TabView_tabNumberTextColor);
        mNumberTextSize = typedArray.getDimension(R.styleable.TabView_TabView_tabNumberTextSize
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_number_text_size));
        mNumberPaddingHorizontal = typedArray.getDimensionPixelSize(R.styleable.TabView_TabView_tabNumberPaddingHorizontal
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_number_padding_horizontal));
        mNumberPaddingVertical = typedArray.getDimensionPixelSize(R.styleable.TabView_TabView_tabNumberPaddingVertical
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_number_padding_vertical));
        mNumberBackgroundDrawable = typedArray.getDrawable(R.styleable.TabView_TabView_tabNumberBackground);
        mNumberMarginTop = typedArray.getDimensionPixelSize(R.styleable.TabView_TabView_tabNumberMarginTop
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_number_margin_top));
        mNumberMarginLeft = typedArray.getDimensionPixelSize(R.styleable.TabView_TabView_tabNumberMarginLeft
                , getResources().getDimensionPixelSize(R.dimen.tabview_item_number_margin_left));
    }

    /**
     * use new item to create child TabItemView
     *
     * @return
     */
    public TabItemView newItem() {
        TabItemView tabItemView = new TabItemView(getContext(), this);
        tabItemView.setTitleTextColor(mTitleTextColor);
        tabItemView.setTitleTextSize(mTitleTextSize);
        tabItemView.setNumberTextColor(mNumberTextColor);
        tabItemView.setNumberTextSize(mNumberTextSize);
        tabItemView.setNumberPaddingHorizontal(mNumberPaddingHorizontal);
        tabItemView.setNumberPaddingVertical(mNumberPaddingVertical);
        tabItemView.setNumberBackground(mNumberBackgroundDrawable);
        tabItemView.setNumberMargin(mNumberMarginLeft, mNumberMarginTop);
        return tabItemView;
    }

    /**
     * add item
     *
     * @param tabItemView
     */
    public void addItem(TabItemView tabItemView) {
        if (tabItemView == null) {
            return;
        }
        if (mTabItemViewList == null) {
            mTabItemViewList = new ArrayList<>(2);
            mSelectedItemId = tabItemView.getId();
            tabItemView.setSelected(true);
        }
        LinearLayout.LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(tabItemView, params);
        mTabItemViewList.add(tabItemView);
    }

    /**
     * remove item
     *
     * @param index
     */
    public void removeItem(int index) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty() || index >= mTabItemViewList.size()) {
            return;
        }
        mTabItemViewList.remove(index);
        removeViewAt(index);
    }

    /**
     * remove item
     *
     * @param itemId
     */
    public void removeItemById(int itemId) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return;
        }
        for (TabItemView tabItemView : mTabItemViewList) {
            if (tabItemView.getId() == itemId) {
                mTabItemViewList.remove(tabItemView);
                removeView(tabItemView);
                break;
            }
        }
    }

    /**
     * get selected item id
     *
     * @return
     */
    public int getSelectedItemId() {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return -1;
        }
        return mSelectedItemId;
    }

    /**
     * set selected id
     *
     * @param id
     */
    public void setSelectedItemId(int id) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return;
        }

        int itemCount = mTabItemViewList.size();
        for (int i = 0; i < itemCount; i++) {
            if (mTabItemViewList.get(i).getId() == id) {
                dispatchSelectEvent(mTabItemViewList.get(i).getId(), false);
                return;
            }
        }
    }

    /**
     * set selected item position
     *
     * @param position
     */
    public void setSelectedItemPosition(int position) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty() || position >= mTabItemViewList.size()) {
            return;
        }
        dispatchSelectEvent(mTabItemViewList.get(position).getId(), false);
    }

    /**
     * get selected item position
     *
     * @return
     */
    public int getSelectedItemPosition() {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty() || mSelectedItemId == -1) {
            return -1;
        }
        int itemCount = mTabItemViewList.size();
        for (int i = 0; i < itemCount; i++) {
            if (mTabItemViewList.get(i).getId() == mSelectedItemId) {
                return i;
            }
        }
        return -1;
    }

    /**
     * get child item by id
     *
     * @param itemId
     * @return
     */
    private TabItemView getItemById(int itemId) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return null;
        }
        for (TabItemView tabItemView : mTabItemViewList) {
            if (tabItemView.getId() == itemId) {
                return tabItemView;
            }
        }
        return null;
    }

    /**
     * find item position by item id
     *
     * @param itemId
     * @return
     */
    private int findItemPositionById(int itemId) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return -1;
        }

        int itemCount = mTabItemViewList.size();
        for (int i = 0; i < itemCount; i++) {
            if (mTabItemViewList.get(i).getId() == itemId) {
                return i;
            }
        }

        return -1;
    }

    /**
     * scroll to position
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        if (mTabItemViewList == null || mTabItemViewList.isEmpty() || position >= mTabItemViewList.size()) {
            return;
        }
        dispatchSelectEvent(mTabItemViewList.get(position).getId(), true);
    }

    /**
     * scroll to position by item id
     *
     * @param itemId
     */
    public void scrollToItem(int itemId) {
        dispatchSelectEvent(itemId, true);
    }

    /**
     * scroll to preview position
     */
    public void scrollToPreviewPosition() {

        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return;
        }
        int position = findItemPositionById(mSelectedItemId);
        if (position == -1) {
            return;
        }
        position--;
        if (position < 0) {
            position = mTabItemViewList.size() - 1;
        }
        dispatchSelectEvent(mTabItemViewList.get(position).getId(), true);
    }

    /**
     * scroll to next position
     */
    public void scrollToNextPosition() {

        if (mTabItemViewList == null || mTabItemViewList.isEmpty()) {
            return;
        }

        int position = findItemPositionById(mSelectedItemId);
        if (position == -1) {
            return;
        }
        position++;
        if (position >= mTabItemViewList.size()) {
            position = 0;
        }
        dispatchSelectEvent(mTabItemViewList.get(position).getId(), true);

    }

    /**
     * dispatch selecte event
     *
     * @param itemId
     * @param needAnimation
     */
    private void dispatchSelectEvent(int itemId, boolean needAnimation) {

        if (mSelectedItemId == itemId) {
            return;
        }
        if (mOnSelectedChangeListener != null) {
            getItemById(mSelectedItemId).setSelected(false);
            mOnSelectedChangeListener.onReleaseSelect(mSelectedItemId);
            mSelectedItemId = itemId;
            getItemById(mSelectedItemId).setSelected(true);
            mOnSelectedChangeListener.onSelected(itemId);
        }

        if (mTabIndicatorView != null) {
            int position = findItemPositionById(itemId);
            if (position != -1) {
                if (needAnimation) {
                    mTabIndicatorView.scrollToPosition(position);
                } else {
                    mTabIndicatorView.setSelectPosition(position);
                }
            }
        }
    }

    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        mOnSelectedChangeListener = onSelectedChangeListener;
    }

    public interface OnSelectedChangeListener {
        void onSelected(int itemId);

        void onReleaseSelect(int itemId);
    }

    /**
     * bind TabIndicatorView
     *
     * @param tabIndicatorView
     */
    public void bindIndicatorView(TabIndicatorView tabIndicatorView) {
        mTabIndicatorView = tabIndicatorView;
    }

}
