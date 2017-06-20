package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BooksByAuthor;

import java.util.List;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public interface BooksByAuthorContract {
    interface View extends BaseContract.BaseView {
        void showBooks(List<BooksByAuthor.BooksBean> beanList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBooksByAuthor(String author);
    }
}
