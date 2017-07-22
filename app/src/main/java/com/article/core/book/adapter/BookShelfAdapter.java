package com.article.core.book.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.common.utils.FormatUtils;
import com.article.core.book.bean.Recommend;

import java.util.List;

/**
 * Created by Amos on 2017/6/22.
 * Desc：
 */

public class BookShelfAdapter extends BaseRVAdapter<Recommend.RecommendBooks> {

    public BookShelfAdapter(Context context, List<Recommend.RecommendBooks> list) {
        super(context, list, R.layout.item_book_shelf);
    }

    private OnRvItemClickListener mItemClickListener;

    public void setItemClickListener(OnRvItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private OnItemLongClickListener mItemLongClickListener;

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    private OnMoreIVClickListener mOnMoreIVClickListener;

    public void setOnMoreIVClickListener(OnMoreIVClickListener onMoreIVClickListener) {
        mOnMoreIVClickListener = onMoreIVClickListener;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, Recommend.RecommendBooks item) {
        if (item.cover != null) {
            viewHolder.setImageUrl(R.id.book_shelf_cover_iv, Constant.BOOK_IMAGE_BASE_URL + item.cover);
        } else {
            viewHolder.setImageUrl(R.id.book_shelf_cover_iv,
                    Constant.BOOK_IMAGE_BASE_URL + item.cover, R.drawable.cover_default);
        }
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
            latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
        }
        viewHolder.setText(R.id.book_shelf_lately_update_tv, latelyUpdate)
                .setText(R.id.book_shelf_title_tv, item.title)
                .setText(R.id.book_shelf_short_tv, item.lastChapter);
        viewHolder.setOnItemClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position);
            }
        });
        viewHolder.setVisible(R.id.book_shelf_top_label_iv, item.isTop)
                .setVisible(R.id.book_shelf_select_cb, item.showCheckBox)
                .setVisible(R.id.book_shelf_unread_iv, FormatUtils.formatZhuiShuDateString(item.updated)
                        .compareTo(item.recentReadingTime) > 0);
        ImageView moreIV = viewHolder.getView(R.id.book_shelf_more_iv);
        moreIV.setOnClickListener(v -> {
            if (mOnMoreIVClickListener != null) {
                mOnMoreIVClickListener.onClick(position);
            }
        });
        //长按监听
        if (mItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(v -> mItemLongClickListener.onItemLongClick(position));
        }

    }

    public Recommend.RecommendBooks getItem(int position) {
        Recommend.RecommendBooks books = mList.get(position);
        return books;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(int position);
    }

    public interface OnMoreIVClickListener {
        void onClick(int position);
    }
}
