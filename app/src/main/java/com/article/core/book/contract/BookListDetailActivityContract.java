package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookListDetail;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public interface BookListDetailActivityContract {
    interface View extends BaseContract.BaseView {
        void showBookListDetail(BookListDetail bookListDetail);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListDetail(String bookListId);
    }
}
