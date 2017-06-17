package com.article.base;

import javax.inject.Inject;

/**
 * Created by Amos on 2017/6/8.
 * Desc：带mvp的全局基类
 */

public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity  {

    @Inject
    protected T mPresenter;


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

}
