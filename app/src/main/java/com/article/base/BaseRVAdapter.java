package com.article.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseRVHolder> {
    protected Context mContext;
    protected List<T> mList;
    protected int[] layoutIds;
    protected LayoutInflater mLInflater;

    private SparseArray<View> mConvertViews = new SparseArray<>();

    public BaseRVAdapter(Context context, List<T> list, int... layoutIds) {
        this.mContext = context;
        this.mList = list;
        this.layoutIds = layoutIds;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > layoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("layoutIndex");
        }
        if (layoutIds.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = layoutIds[viewType];
        View view = mConvertViews.get(layoutId);
        if (view == null) {
            view = mLInflater.inflate(layoutId, parent, false);
        }
        BaseRVHolder viewHolder = (BaseRVHolder) view.getTag();
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = new BaseRVHolder(mContext, layoutId, view);
            return viewHolder;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRVHolder viewHolder, int position) {
        final T item = mList.get(position);
        onBindData(viewHolder, position, item);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIndex(position, mList.get(position));
    }

    /**
     * 设置数据
     *
     * @param list
     * @return
     */
    public boolean addAll(List<T> list) {
        boolean result = mList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    /**
     * 在指定位置设置数据
     *
     * @param position
     * @param list
     * @return
     */
    public boolean addAll(int position, List list) {
        boolean result = mList.addAll(position, list);
        notifyDataSetChanged();
        return result;
    }

    /**
     * 添加单个数据
     *
     * @param data
     */
    public void add(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加数据
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        mList.add(position, data);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public boolean contains(T data) {
        return mList.contains(data);
    }

    public T getData(int index) {
        return mList.get(index);
    }

    public void modify(T oldData, T newData) {
        modify(mList.indexOf(oldData), newData);
    }

    public void modify(int index, T newData) {
        mList.set(index, newData);
        notifyDataSetChanged();
    }

    /**
     * 移除数据
     *
     * @param data
     * @return
     */
    public boolean remove(T data) {
        boolean result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    /**
     * 移除指定位置的数据
     *
     * @param index
     */
    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 指定多种item布局，默认使用第一个
     *
     * @param position
     * @param item
     * @return
     */
    public int getLayoutIndex(int position, T item) {
        return 0;
    }

    /**
     * 每个adapter需要实现这个方法，通过这个方法绑定数据
     *
     * @param viewHolder
     * @param position
     * @param item
     */
    protected abstract void onBindData(BaseRVHolder viewHolder, int position, T item);
}
