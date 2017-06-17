package com.article.core.book.adapter;

import android.content.Context;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.MainBean;

import java.util.List;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public class FragmentFindAdapter extends BaseRVAdapter<MainBean> {
    private OnRvItemClickListener mClickListener;

    public FragmentFindAdapter(Context context, List<MainBean> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_main);
        mClickListener = listener;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, MainBean item) {
        viewHolder.setImageResource(R.id.ivIcon, item.getIconResId())
                .setText(R.id.tvTitle, item.getTitle()).setOnItemClickListener(
                v -> mClickListener.onItemClick(viewHolder.itemView, position));
    }
}
