package com.article.core.fun.mvp;

import com.article.base.RxPresenter;
import com.article.common.api.FunApi;
import com.article.common.utils.LogUtils;

import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/7/12.
 * Desc：
 */

public class FunFragmentPresenter extends RxPresenter<FunFragmentContract.View>
        implements FunFragmentContract.Presenter<FunFragmentContract.View> {

    FunApi mFunApi;

    @Inject
    public FunFragmentPresenter(FunApi funApi) {
        mFunApi = funApi;
    }

    @Override
    public void getQiushi(String tag, String page) {
        mFunApi.getQiuShi(tag, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    InputStream inputStream = responseBody.byteStream();
                    String result = QiuShiParser.stream2string(inputStream);
                    LogUtils.i(result);
                });
    }
}
