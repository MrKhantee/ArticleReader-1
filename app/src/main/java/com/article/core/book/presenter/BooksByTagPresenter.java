package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BooksByTag;
import com.article.core.book.contract.BooksByTagContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByTagPresenter extends RxPresenter<BooksByTagContract.View>
        implements BooksByTagContract.Presenter<BooksByTagContract.View> {

    BookApi mBookApi;

    @Inject
    public BooksByTagPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBooksByTag(String tag, String start, String limit) {
        String key = StringUtils.creatAcacheKey("books-by-tag");
        Flowable<BooksByTag> compose = mBookApi.getBooksByTag(tag, start, limit).compose(RxUtils.rxCacheBeanHelper(key));

        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BooksByTag.class), compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booksByTag -> {
                    if (booksByTag != null) {
                        List<BooksByTag.TagBook> books = ((BooksByTag) booksByTag).books;
                        if (books != null && !books.isEmpty() && mView != null) {
                            mView.showBooksByTag(books, start.equals("0"));
                        }
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
