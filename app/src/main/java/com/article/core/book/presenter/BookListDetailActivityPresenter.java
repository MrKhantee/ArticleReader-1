package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.contract.BookListDetailActivityContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class BookListDetailActivityPresenter extends RxPresenter<BookListDetailActivityContract.View>
        implements BookListDetailActivityContract.Presenter<BookListDetailActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public BookListDetailActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBookListDetail(String bookListId) {
        Disposable disposable = mBookApi.getBookListDetail(bookListId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookListDetail -> mView.showBookListDetail(bookListDetail),
                        throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
