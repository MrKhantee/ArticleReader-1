package com.article.core.welfare.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.WelfareApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.welfare.bean.GankMeiziBean;
import com.article.core.welfare.bean.GankMeiziResult;
import com.article.core.welfare.contract.GankMeiZiContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/7/26.
 * Desc：
 */

public class GankMeiZiPresenter extends RxPresenter<GankMeiZiContract.View>
        implements GankMeiZiContract.Presenter<GankMeiZiContract.View> {

    WelfareApi mWelfareApi;

    @Inject
    public GankMeiZiPresenter(WelfareApi welfareApi) {
        mWelfareApi = welfareApi;
    }

    @Override
    public void getGankMeiZi(int number, int page) {
        String key = StringUtils.creatAcacheKey("gangk_mei_zi");
        Flowable<GankMeiziBean> fromNetwork = mWelfareApi.getGankMeiZi(number, page).compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, GankMeiziResult.class), fromNetwork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (mView != null && data != null) {
                        mView.showGankMeizi((GankMeiziBean) data);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
