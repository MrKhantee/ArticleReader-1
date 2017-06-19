package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.contract.SubCategoryFragmentContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public class SubCategoryFragmentPresenter extends RxPresenter<SubCategoryFragmentContract.View>
        implements SubCategoryFragmentContract.Presenter<SubCategoryFragmentContract.View> {

    BookApi mBookApi;

    @Inject
    public SubCategoryFragmentPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getCategoryList(String gender, String type, String major, String minor, int start, int limit) {
        String key = StringUtils.creatAcacheKey("category-list", gender, type, major, minor, start, limit);
        Flowable<BooksByCats> fromNetWork = mBookApi.getBooksByCats(gender, type, major, minor, start, limit);
        fromNetWork.compose(RxUtils.rxCacheBeanHelper(key));

        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BooksByCats.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booksByCats -> mView.showCategoryList((BooksByCats) booksByCats,
                        start == 0 ? true : false), throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
