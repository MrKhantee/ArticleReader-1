package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BooksByCats;

/**
 * Created by Amos on 2017/6/12.
 * Desc：
 */

public interface SubOtherRankActivityContract {
    interface View extends BaseContract.BaseView {
        void showRankList(BooksByCats data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList(String id);
    }
}
