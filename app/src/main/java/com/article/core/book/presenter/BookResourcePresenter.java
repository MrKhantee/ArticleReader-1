package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.contract.BookResourceContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/29.
 * Desc：
 */

public class BookResourcePresenter extends RxPresenter<BookResourceContract.View>
        implements BookResourceContract.Presenter<BookResourceContract.View> {

    BookApi mBookApi;

    @Inject
    public BookResourcePresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBookResource(String view, String book) {
        Disposable disposable = mBookApi.getBookResource(view, book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookResource -> {
                    if (mView != null && bookResource != null) {
                        mView.showBookResource(bookResource);
                        System.out.println("------>"+bookResource);
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
