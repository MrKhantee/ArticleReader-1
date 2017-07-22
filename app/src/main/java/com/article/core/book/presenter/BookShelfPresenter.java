package com.article.core.book.presenter;

import android.content.Context;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.ACache;
import com.article.common.utils.LogUtils;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.Recommend;
import com.article.core.book.contract.BookShelfContract;
import com.article.core.book.manager.SettingManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/22.
 * Desc：
 */

public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter<BookShelfContract.View> {


    BookApi mBookApi;
    Context mContext;

    @Inject
    public BookShelfPresenter(BookApi bookApi, Context context) {
        mBookApi = bookApi;
        mContext = context;
    }

    @Override
    public void getCollectionBook() {
        String key = StringUtils.creatAcacheKey("recommend-list", SettingManager.getInstance().getUserChooseSex());
        Flowable<Recommend> fromNetWork = mBookApi.getRecommend(SettingManager.getInstance().getUserChooseSex())
                .compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, Recommend.class), fromNetWork)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommend -> {
                    if (recommend != null) {
                        List<Recommend.RecommendBooks> list = ((Recommend) recommend).books;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showCollectionBook(list);
                        }
                    }
                }, throwable -> {
                    LogUtils.e("getRecommendList", throwable.toString());
                    mView.showError();
                }, () -> mView.complete());
        addSubscribe(disposable);
    }

    @Override
    public void getTopMac(String bookId) {
        mBookApi.getBookMixToc(bookId, "chapters").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookMixAToc -> {
                    ACache.get(mContext).put(bookId + "bookToc", bookMixAToc);
                    List<BookMixAToc.mixToc.Chapters> list = bookMixAToc.mixToc.chapters;
                    if (list != null && !list.isEmpty() && mView != null) {
                        mView.showChapter(bookId, list);
                    }
                }, throwable -> {
                    LogUtils.e("onError: " + throwable);
                    mView.showError();
                }, () -> mView.complete());

    }
}
