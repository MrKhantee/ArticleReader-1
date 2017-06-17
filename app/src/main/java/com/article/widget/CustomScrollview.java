package com.article.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class CustomScrollview extends ScrollView implements NestedScrollingParent {

    private RecyclerView mRecyclerView;
    private int maxHeight = 464;

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public CustomScrollview(Context context) {
        super(context);
    }

    public CustomScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);
    }

    /**
     * 返回true时，代表父View消费滑动事件，子View不滑动
     *
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) target;
        }
        if (mRecyclerView.computeVerticalScrollOffset() != 0) {
            return false;
        }
        this.fling((int) velocityY);
        return true;
    }

    /**
     * 对应子view 的dispatchNestedPreScroll方法， 最后一个数组代表消耗的滚动量，下标0代表x轴，下标1代表y轴
     *
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //判断是否滚动到最大值
        if (dy >= 0 && this.getScrollY() < maxHeight) {
            if (mRecyclerView == null) {
                mRecyclerView = (RecyclerView) target;
            }
            //计算RecyclerView的偏移量， 等于0的时候说明recyclerView没有滑动，否则应该交给recyclerView自己处理
            if (mRecyclerView.computeVerticalScrollOffset() != 0) {
                return;
            }
            this.smoothScrollBy(dx, dy);
            consumed[1] = dy; //consumed[1]赋值为 dy ，代表父类已经消耗了改滚动。
        }
    }

}
