package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookListTags;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public interface BookListActivityContract {
    interface View extends BaseContract.BaseView {
        void showBookListTags(BookListTags bookListTags);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListTags();
    }
}
