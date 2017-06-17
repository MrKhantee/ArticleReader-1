package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.CategoryList;
import com.article.core.book.contract.TopCategoryActivityContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/17.
 * Desc：
 */

public class TopCategoryActivityPresenter extends RxPresenter<TopCategoryActivityContract.View>
        implements TopCategoryActivityContract.Presenter<TopCategoryActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public TopCategoryActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getCategoryList() {
        String key = StringUtils.creatAcacheKey("book-category-list");
        Flowable<CategoryList> fromNetWork = mBookApi.getCategoryList().compose(RxUtils.rxCacheBeanHelper(key));

        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, CategoryList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryList -> {
                    if (mView != null && categoryList != null) {
                        mView.showCategoryList((CategoryList) categoryList);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
