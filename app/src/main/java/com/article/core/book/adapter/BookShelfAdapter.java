package com.article.core.book.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.common.utils.FormatUtils;
import com.article.core.book.bean.data.CollectionBook;

import java.util.List;

/**
 * Created by Amos on 2017/6/22.
 * Desc：
 */

public class BookShelfAdapter extends BaseRVAdapter<CollectionBook> {

    public BookShelfAdapter(Context context, List<CollectionBook> list) {
        super(context, list, R.layout.item_book_shelf);
    }

    private OnRvItemClickListener mItemClickListener;

    public void setItemClickListener(OnRvItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, CollectionBook item) {
        if (item.getCover() != null) {
            viewHolder.setImageUrl(R.id.book_shelf_cover_iv, Constant.BOOK_IMAGE_BASE_URL + item.getCover());
        } else {
            viewHolder.setImageUrl(R.id.book_shelf_cover_iv,
                    Constant.BOOK_IMAGE_BASE_URL + item.getCover(), R.drawable.cover_default);
        }
        String latelyUpdate = "";
        if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.getUpdated()))) {
            latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.getUpdated()) + ":";
        }
        viewHolder.setText(R.id.book_shelf_lately_update_tv, latelyUpdate)
                .setText(R.id.book_shelf_title_tv, item.getTitle())
                .setText(R.id.book_shelf_short_tv, item.getLastChapter());
        viewHolder.setOnItemClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position);
            }
        });
    }
}
