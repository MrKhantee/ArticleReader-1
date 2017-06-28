package com.article.core.book.presenter;

import android.content.Context;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.contract.BookReadContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/26.
 */

public class BookReadPresenter extends RxPresenter<BookReadContract.View>
        implements BookReadContract.Presenter<BookReadContract.View> {
    Context mContext;
    BookApi mBookApi;

    @Inject
    public BookReadPresenter(Context context, BookApi bookApi) {
        mBookApi = bookApi;
        mContext = context;
    }

    @Override
    public void getBookMixAToc(String view, String book) {

    }

    @Override
    public void getChapterRead(String url, int chapter) {
        Disposable disposable = mBookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chapterRead -> {
                    if (chapterRead.chapter != null && mView != null) {
                        mView.showChapterRead(chapterRead.chapter, chapter);
                    } else {
                        mView.netError(chapter);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }

    @Override
    public void changerResource(String sourceId) {
        Disposable disposable = mBookApi.changeResource(sourceId, "chapters").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(changeChapter -> {
                    if (mView != null && changeChapter != null) {

                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
