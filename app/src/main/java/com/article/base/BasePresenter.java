package com.article.base;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public interface BasePresenter<T extends BaseContract.BaseView> {
    void attachView(T view);

    void detachView();
}
