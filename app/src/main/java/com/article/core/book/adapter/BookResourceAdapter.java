package com.article.core.book.adapter;

import android.content.Context;
import android.view.View;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.core.book.bean.BookResource;

import java.util.List;

/**
 * Created by Amos on 2017/6/29.
 * Desc：
 */

public class BookResourceAdapter extends BaseRVAdapter<BookResource> {


    private String host;

    public BookResourceAdapter(Context context, List<BookResource> list, String selectHost) {
        super(context, list, R.layout.item_book_resource);
        host = selectHost;
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, BookResource item) {
        if (item.host.equals(host)) {
            viewHolder.setTextColor(R.id.book_resource_item_host, mContext.getResources().getColor(R.color.colorAccent));
            viewHolder.setTextColor(R.id.book_resource_item_last_chapter, mContext.getResources().getColor(R.color.colorAccent));
            viewHolder.setVisible(R.id.book_resource_item_select, View.VISIBLE);
        } else {
            viewHolder.setTextColor(R.id.book_resource_item_host, mContext.getResources().getColor(R.color.common_h1));
            viewHolder.setTextColor(R.id.book_resource_item_last_chapter, mContext.getResources().getColor(R.color.common_h1));
            viewHolder.setVisible(R.id.book_resource_item_select, View.INVISIBLE);
        }
        viewHolder.setText(R.id.book_resource_item_host, item.host);
        viewHolder.setText(R.id.book_resource_item_last_chapter, item.lastChapter);
    }
}
