package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.RecommendBookList;

import java.util.List;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public interface RecommendBookListActivityConstract {

    interface View extends BaseContract.BaseView {
        void showRecommendBookList(List<RecommendBookList.RecommendBook> recommendBooks);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRecommendBookList(String bookId, String limit);
    }
}
