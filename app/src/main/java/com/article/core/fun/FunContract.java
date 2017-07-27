package com.article.core.fun;

import com.article.base.BaseContract;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public interface FunContract {
    interface View extends BaseContract.BaseView {
        void showQiuShi(FunBean funBean);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getQiuShiBaiKe(int page, int count);
    }
}
