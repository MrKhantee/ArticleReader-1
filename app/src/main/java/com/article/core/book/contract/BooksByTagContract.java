package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BooksByTag;

import java.util.List;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public interface BooksByTagContract {
    interface View extends BaseContract.BaseView {
        void showBooksByTag(List<BooksByTag.TagBook> list, boolean isRefresh);

        void onLoadComplete(boolean isSuccess, String msg);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBooksByTag(String tag, String start, String limit);
    }
}
