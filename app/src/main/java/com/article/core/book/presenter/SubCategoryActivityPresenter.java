package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.CategoryListLv2;
import com.article.core.book.contract.SubCategoryActivityContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public class SubCategoryActivityPresenter extends RxPresenter<SubCategoryActivityContract.View>
        implements SubCategoryActivityContract.Presenter<SubCategoryActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public SubCategoryActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getSubCategoryListLv2() {
        String key = StringUtils.creatAcacheKey("book-category-list-lv2");
        Flowable<CategoryListLv2> fromNetWork = mBookApi.getCategoryListLv2()
                .compose(RxUtils.rxCacheBeanHelper(key));

        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, CategoryListLv2.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryListLv2 -> {
                    if (mView != null && categoryListLv2 != null) {
                        mView.showSubCategory((CategoryListLv2) categoryListLv2);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
