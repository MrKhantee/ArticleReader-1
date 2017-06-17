package com.article.base;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public interface BaseContract {
    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError();

        void complete();

    }
}
