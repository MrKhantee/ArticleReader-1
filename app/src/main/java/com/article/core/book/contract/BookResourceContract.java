package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookResource;

/**
 * Created by Amos on 2017/6/29.
 * Desc：
 */

public interface BookResourceContract {
    interface View extends BaseContract.BaseView {
        void showBookResource(BookResource bookResource);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookResource(String view, String book);
    }
}
