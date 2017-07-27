package com.article.core.welfare.contract;

import com.article.base.BaseContract;
import com.article.core.welfare.bean.GankMeiziBean;

/**
 * Created by Amos on 2017/7/26.
 * Desc：
 */

public interface GankMeiZiContract {
    interface View extends BaseContract.BaseView {
        void showGankMeizi(GankMeiziBean GankMeiziBean);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getGankMeiZi(int number, int page);
    }
}
