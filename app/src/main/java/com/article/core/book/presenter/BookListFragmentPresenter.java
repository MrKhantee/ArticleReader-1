package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BookLists;
import com.article.core.book.contract.BookListFragmentContract;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookListFragmentPresenter extends RxPresenter<BookListFragmentContract.View>
        implements BookListFragmentContract.Presenter<BookListFragmentContract.View> {

    BookApi mBookApi;

    @Inject
    public BookListFragmentPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }


    @Override
    public void getBookLists(String duration, String sort, int start, int limit, String tag, String gender) {
        String key = StringUtils.creatAcacheKey("book-lists", duration, sort, start + "", limit + "", tag, gender);
        Flowable<BookLists> fromNetWork = mBookApi.getBookLists(duration, sort, start + "", limit + "", tag, gender)
                .compose(RxUtils.rxCacheListHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BookLists.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BookLists>() {
                    @Override
                    public void accept(@NonNull BookLists bookLists) throws Exception {
                        mView.showBookList(bookLists.bookLists, start == 0 ? true : false);
                        if (bookLists.bookLists == null || bookLists.bookLists.size() <= 0) {
                        }
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
