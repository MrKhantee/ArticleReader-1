package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookDetail;
import com.article.core.book.bean.RecommendBookList;

import java.util.List;

/**
 * Created by Amos on 2017/6/12.
 * Desc：
 */

public interface BookDetailActivityContract {
    interface View extends BaseContract.BaseView {
        void showBookDetail(BookDetail bookDetail);

        void showRecommendBookList(List<RecommendBookList.RecommendBook> recommendBookList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetail(String bookId);

        void getRecommendBookList(String bookId, String limit);
    }
}
