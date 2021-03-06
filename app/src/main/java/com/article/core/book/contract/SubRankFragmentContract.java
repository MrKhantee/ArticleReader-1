package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BooksByCats;

/**
 * Created by Amos on 2017/6/11.
 * Desc：
 */

public interface SubRankFragmentContract {
    interface View extends BaseContract.BaseView {
        void showRankList(BooksByCats data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList(String id);
    }
}
