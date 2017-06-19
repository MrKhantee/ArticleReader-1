package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BooksByCats;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public interface SubCategoryFragmentContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(BooksByCats booksByCats, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList(String gender, String major, String minor,
                             String type, int start, int limit);
    }
}
