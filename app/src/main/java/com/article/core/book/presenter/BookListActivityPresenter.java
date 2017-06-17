package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BookListTags;
import com.article.core.book.contract.BookListActivityContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookListActivityPresenter extends RxPresenter<BookListActivityContract.View>
        implements BookListActivityContract.Presenter<BookListActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public BookListActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getBookListTags() {
        String key = StringUtils.creatAcacheKey("book-list-tags");
        Flowable<BookListTags> fromNetWork = mBookApi.getBookListTags().compose(RxUtils.rxCacheListHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BookListTags.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookListTags -> mView.showBookListTags((BookListTags) bookListTags),
                        throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
