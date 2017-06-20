package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.bean.BooksByAuthor;
import com.article.core.book.contract.BooksByAuthorContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByAuthorPresenter extends RxPresenter<BooksByAuthorContract.View>
        implements BooksByAuthorContract.Presenter<BooksByAuthorContract.View> {

    BookApi mBookApi;

    @Inject
    public BooksByAuthorPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBooksByAuthor(String author) {
        Disposable disposable = mBookApi.getBooksByAuthor(author)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booksByAuthor -> {
                    List<BooksByAuthor.BooksBean> books = booksByAuthor.books;
                    if (books != null && !books.isEmpty() && mView != null) {
                        mView.showBooks(books);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
