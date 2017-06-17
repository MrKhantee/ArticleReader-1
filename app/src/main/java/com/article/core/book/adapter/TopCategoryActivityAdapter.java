package com.article.core.book.adapter;

import android.content.Context;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.core.book.bean.CategoryList;

import java.util.List;

/**
 * Created by Amos on 2017/6/17.
 * Desc：
 */

public class TopCategoryActivityAdapter extends BaseRVAdapter<CategoryList.MaleBean> {

    public TopCategoryActivityAdapter(Context context, List<CategoryList.MaleBean> list
            , int... layoutIds) {
        super(context, list, R.layout.item_top_category);
    }

    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, CategoryList.MaleBean item) {
        viewHolder.setText(R.id.top_cate_item_name_tv, item.name)
                .setText(R.id.top_cate_item_count_tv, String.format(mContext.getString(R.string
                        .category_book_count), item.bookCount));
    }
}
