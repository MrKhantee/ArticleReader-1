package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.CategoryListLv2;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public interface SubCategoryActivityContract {

    interface View extends BaseContract.BaseView {
        void showSubCategory(CategoryListLv2 categoryListLv2);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getSubCategoryListLv2();
    }
}
