package com.article.core.fun;

import com.article.base.RxPresenter;
import com.article.common.api.FunApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/7/12.
 * Desc：
 */

public class FunPresenter extends RxPresenter<FunContract.View>
        implements FunContract.Presenter<FunContract.View> {

    FunApi mFunApi;

    @Inject
    public FunPresenter(FunApi funApi) {
        mFunApi = funApi;
    }


    @Override
    public void getQiuShiBaiKe(String page, String count) {
        String key = StringUtils.creatAcacheKey("qiushibaike-data");
        Flowable<FunBean> compose = mFunApi.getQiuShiBaiKe(page, count).compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, FunBean.class), compose)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qiuShiBean -> {
                    if (mView != null && qiuShiBean != null) {
                        mView.showQiuShi((FunBean) qiuShiBean);
                        System.out.println(qiuShiBean);
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.showError();
                    }
                }, () -> {
                    if (mView != null) {
                        mView.complete();
                    }
                });
        addSubscribe(disposable);
    }
}
