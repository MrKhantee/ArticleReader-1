package com.article.core.book.adapter;

import android.content.Context;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.manager.SettingManager;

import java.util.List;

/**
 * Created by Amos on 2017/6/13.
 * Desc：
 */

public class RecommendBookListAdapter extends BaseRVAdapter<RecommendBookList.RecommendBook> {

    private OnRvItemClickListener mOnRvItemClickListener;

    public RecommendBookListAdapter(Context context, List<RecommendBookList.RecommendBook> list,
                                    OnRvItemClickListener listener) {
        super(context, list, R.layout.item_book_detail_recommend_book_list);
        this.mOnRvItemClickListener = listener;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, RecommendBookList.RecommendBook item) {
        if (!SettingManager.getInstance().isNoneCover()) {
            viewHolder.setRoundImageUrl(R.id.book_list_cover_iv, Constant.BOOK_IMAGE_BASE_URL + item.cover, R.drawable.cover_default);
        }
        viewHolder.setText(R.id.book_list_title_tv, item.title)
                .setText(R.id.book_list_author_tv, item.author)
                .setText(R.id.book_list_desc_tv, item.desc)
                .setText(R.id.book_list_book_count_tv, String.format(mContext.getString(
                        R.string.book_detail_recommend_book_list_book_count), item.bookCount))
                .setText(R.id.book_list_book_collector_count_tv, String.format(mContext.getString(
                        R.string.book_detail_recommend_book_list_collector_count), item.collectorCount))
                .setOnItemClickListener(v -> {
                    if (mOnRvItemClickListener != null) {
                        mOnRvItemClickListener.onItemClick(v, position);
                    }
                });
    }
}
