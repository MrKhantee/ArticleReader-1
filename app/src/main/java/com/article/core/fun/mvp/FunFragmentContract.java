package com.article.core.fun.mvp;

import com.article.base.BaseContract;
import com.article.core.fun.bean.FunBean;

import java.util.List;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */

public interface FunFragmentContract {
    interface View extends BaseContract.BaseView {
        void showQiuShi(List<FunBean> mFunBeans);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getQiushi(String tag, String page);
    }
}
