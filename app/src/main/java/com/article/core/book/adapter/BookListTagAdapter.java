package com.article.core.book.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.core.book.bean.BookListTags;

import java.util.List;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookListTagAdapter extends BaseRVAdapter<BookListTags.DataBean> {


    private TopRankActivityAdapter.OnRvItemClickListener listener;

    public BookListTagAdapter(Context context, List<BookListTags.DataBean> list) {
        super(context, list, R.layout.item_book_list_tags);
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, BookListTags.DataBean item) {
        RecyclerView tagItem = viewHolder.getView(R.id.book_list_tag_item_rv);
        tagItem.setHasFixedSize(true);
        tagItem.setLayoutManager(new GridLayoutManager(mContext, 4));
        TagAdapter tagAdapter = new TagAdapter(mContext, item.tags);
        tagItem.setAdapter(tagAdapter);
        viewHolder.setText(R.id.book_list_tag_name_tv, item.name);
    }

    class TagAdapter extends BaseRVAdapter<String> {
        public TagAdapter(Context context, List<String> list) {
            super(context, list, R.layout.item_book_list_tag);
        }

        @Override
        protected void onBindData(BaseRVHolder viewHolder, int position, String item) {
            viewHolder.setText(R.id.tag_name_tv, item);
            viewHolder.getItemView().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(v, position, item);
                }
            });
        }
    }

    public void setListener(TopRankActivityAdapter.OnRvItemClickListener<String> listener) {
        this.listener = listener;
    }
}
