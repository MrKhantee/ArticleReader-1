package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.TopRankList;
import com.article.core.book.contract.TopRankActivityContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public class TopRankActivityPresenter extends RxPresenter<TopRankActivityContract.View>
        implements TopRankActivityContract.Presenter<TopRankActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public TopRankActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getRankList() {
        String key = StringUtils.creatAcacheKey("ranking-list");
        Flowable<TopRankList> fromNetWork = mBookApi.getTopRankList()
                .compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, TopRankList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rankingList -> {
                    mView.showRankList((TopRankList) rankingList);
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
