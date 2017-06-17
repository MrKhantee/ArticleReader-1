package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.TopRankList;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public interface TopRankActivityContract {
    interface View extends BaseContract.BaseView {
        void showRankList(TopRankList topRankList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getRankList();
    }

}
