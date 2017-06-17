package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.CategoryList;

/**
 * Created by Amos on 2017/6/17.
 * Desc：
 */

public interface TopCategoryActivityContract {
    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryList categoryList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList();
    }
}
