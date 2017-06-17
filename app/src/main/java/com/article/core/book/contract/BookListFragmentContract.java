package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookLists;

import java.util.List;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public interface BookListFragmentContract {
    interface View extends BaseContract.BaseView {
        void showBookList(List<BookLists.BookListsBean> bookLists, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookLists(String duration, String sort, int start, int limit, String tag, String gender);
    }
}
