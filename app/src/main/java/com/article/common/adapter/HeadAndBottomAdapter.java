package com.article.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Amos on 2017/7/17.
 * Desc：
 */

public class HeadAndBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADERS_START = Integer.MIN_VALUE;

    private static final int FOOTERS_START = Integer.MIN_VALUE + 10;

    private static final int ITEMS_START = Integer.MIN_VALUE + 20;

    private static final int ADAPTER_MAX_TYPES = 100;
    //内容的适配器
    private RecyclerView.Adapter mContentAdapter;
    //头部和底部的View
    private List<View> mHeaderViews, mFooterViews;

    private Map<Class, Integer> mItemTypesOffset;

    public HeadAndBottomAdapter(RecyclerView.Adapter adapter) {
        mHeaderViews = new ArrayList<>();
        mFooterViews = new ArrayList<>();
        mItemTypesOffset = new HashMap<>();
        setContentAdapter(adapter);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (mContentAdapter != null && mContentAdapter.getItemCount() > 0) {
            notifyItemRangeRemoved(getHeaderCount(), mContentAdapter.getItemCount());
        }
        setContentAdapter(adapter);
        notifyItemRangeInserted(getHeaderCount(), mContentAdapter.getItemCount());
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getContentItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = getHeaderCount();
        if (position < headerCount) {
            return HEADERS_START + position;
        } else {
            int itemCount = mContentAdapter.getItemCount();
            if (position < itemCount + headerCount) {
                return getAdapterTypeOffset() + mContentAdapter.getItemViewType(position - headerCount);
            } else {
                return FOOTERS_START + position - headerCount - itemCount;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < HEADERS_START + getHeaderCount()) {
            return new StaticViewHolder(mHeaderViews.get(viewType - HEADERS_START));
        } else if (viewType < FOOTERS_START + getFooterCount()) {
            return new StaticViewHolder(mFooterViews.get(viewType - FOOTERS_START));
        } else {
            return mContentAdapter.onCreateViewHolder(parent, viewType - getAdapterTypeOffset());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int hCount = getHeaderCount();
        if (position >= hCount && position < hCount + mContentAdapter.getItemCount())
            mContentAdapter.onBindViewHolder(viewHolder, position - hCount);
    }

    private int getAdapterTypeOffset() {
        return mItemTypesOffset.get(mContentAdapter.getClass());
    }

    //内容Item数量
    public int getContentItemCount() {
        return mContentAdapter.getItemCount();
    }

    /**
     * 头部View的数量
     *
     * @return
     */
    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    /**
     * 底部View的数量
     *
     * @return
     */
    public int getFooterCount() {
        return mFooterViews.size();
    }


    /**
     * 添加一个头布局
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
    }

    /**
     * 添加一个底部布局
     *
     * @param view
     */
    public void addFooterView(View view) {
        mFooterViews.add(view);
    }

    /**
     * 设置内容的item
     *
     * @param adapter
     */
    private void setContentAdapter(RecyclerView.Adapter adapter) {
        if (mContentAdapter != null) {
            mContentAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        mContentAdapter = adapter;
        Class<? extends RecyclerView.Adapter> adapterClass = adapter.getClass();
        if (!mItemTypesOffset.containsKey(adapterClass)) {
            putAdapterTypeOffset(adapterClass);
        }
        mContentAdapter.registerAdapterDataObserver(mDataObserver);
    }

    private void putAdapterTypeOffset(Class adapterClass) {
        mItemTypesOffset.put(adapterClass, ITEMS_START + mItemTypesOffset.size() * ADAPTER_MAX_TYPES);
    }

    private static class StaticViewHolder extends RecyclerView.ViewHolder {
        public StaticViewHolder(View itemView) {
            super(itemView);
        }
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderCount(), itemCount);
        }


        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {

            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderCount(), itemCount);
        }


        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {

            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderCount(), itemCount);
        }


        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {

            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int hCount = getHeaderCount();
            // TODO: No notifyItemRangeMoved method?
            notifyItemRangeChanged(fromPosition + hCount, toPosition + hCount + itemCount);
        }
    };
}
