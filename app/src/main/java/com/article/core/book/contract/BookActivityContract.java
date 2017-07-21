package com.article.core.book.contract;

import com.article.base.BaseContract;

/**
 * Created by Amos on 2017/7/21.
 * Desc：
 */

public interface BookActivityContract {
    interface View extends BaseContract.BaseView {
//        void loginSuccess();

        void syncBookShelfCompleted();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

//        void login(String uid, String token, String platform);

        void syncBookShelf();
    }
}
