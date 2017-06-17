package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.contract.BookDetailActivityContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/12.
 * Desc：
 */

public class BookDetailActivityPresenter extends RxPresenter<BookDetailActivityContract.View>
        implements BookDetailActivityContract.Presenter<BookDetailActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public BookDetailActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBookDetail(String bookId) {
        Disposable disposable = mBookApi.getBookDetail(bookId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookDetail -> {
                    if (bookDetail != null && mView != null) {
                        mView.showBookDetail(bookDetail);
                    }
                },throwable -> mView.showError(),()->mView.complete());
        addSubscribe(disposable);
    }

    @Override
    public void getRecommendBookList(String bookId, String limit) {
        Disposable disposable = mBookApi.getRecommendBookList(bookId, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendBookList -> {
                            List<RecommendBookList.RecommendBook> booklists = recommendBookList.booklists;
                            if (booklists != null && !booklists.isEmpty() && mView != null) {
                                mView.showRecommendBookList(booklists);
                            }
                        },
                        throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
