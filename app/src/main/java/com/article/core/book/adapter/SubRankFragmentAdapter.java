package com.article.core.book.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.bean.BooksByCats;

import java.util.List;

/**
 * Created by Amos on 2017/6/10.
 * Desc：
 */

public class SubRankFragmentAdapter extends BaseRVAdapter<BooksByCats.BooksBean> {
    List<BooksByCats.BooksBean> mBeanList;
    Context mContext;
    private OnRvItemClickListener mClickListener;

    public SubRankFragmentAdapter(Context context, List<BooksByCats.BooksBean> list) {
        super(context, list, R.layout.item_sub_rank);
        mBeanList = list;
        this.mContext = context;
    }


    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, BooksByCats.BooksBean item) {
        viewHolder.setRoundImageUrl(R.id.sub_rank_cover_ic, Constant.BOOK_IMAGE_BASE_URL + item.cover,
                R.drawable.cover_default);
        viewHolder.setText(R.id.sub_rank_cate_title_tv, item.title)
                .setText(R.id.sub_rank_cate_author_tv, (item.author == null ? "未知" : item.author) +
                        " | " + (item.majorCate == null ? "未知" : item.majorCate))
                .setText(R.id.sub_rank_cate_short_tv, item.shortIntro)
                .setText(R.id.sub_rank_cate_msg_tv, String.format(mContext.getResources().getString(R.string.sub_ran_cate_msg),
                        item.latelyFollower,
                        TextUtils.isEmpty(item.retentionRatio) ? "0" : item.retentionRatio))
                .setOnItemClickListener(v -> {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, position);
                    }
                });
    }

    public void setClickListener(OnRvItemClickListener clickListener) {
        mClickListener = clickListener;
    }
}
