package com.article.core.book.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.ui.fragment.BookTocFragment;

import java.util.List;

/**
 * Created by Amos on 2017/7/1.
 * 小说目录适配器
 */

public class BookTocAdapter extends BaseRVAdapter<BookMixAToc.mixToc.Chapters> {

    private String bookId;
    private int currentChapter;

    public void setOnTocItemClickListener(BookTocFragment.OnTocItemClickListener onTocItemClickListener) {
        mOnTocItemClickListener = onTocItemClickListener;
    }

    private BookTocFragment.OnTocItemClickListener mOnTocItemClickListener;

    public BookTocAdapter(Context context, List<BookMixAToc.mixToc.Chapters> list, String bookId, int currentChapter) {
        super(context, list, R.layout.item_book_toc_list);
        this.bookId = bookId;
        this.currentChapter = currentChapter;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, BookMixAToc.mixToc.Chapters item) {
        viewHolder.setText(R.id.tvTocItem, item.title);
        Drawable drawable;
        viewHolder.setOnItemClickListener(v -> {
            if (mOnTocItemClickListener != null) {
                mOnTocItemClickListener.onItemClick(position);
            }
        });
    }

    public void setCurrentChapter(int position) {
        currentChapter = position;
        notifyDataSetChanged();
    }
}
