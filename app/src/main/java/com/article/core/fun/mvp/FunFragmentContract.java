package com.article.core.fun.mvp;

import com.article.base.BaseContract;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public interface FunFragmentContract {
    interface View extends BaseContract.BaseView {

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getQiushi(String tag, String s);
    }
}
