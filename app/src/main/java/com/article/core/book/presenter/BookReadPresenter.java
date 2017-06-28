package com.article.core.book.presenter;

import android.content.Context;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.BookResource;
import com.article.core.book.contract.BookReadContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.article.common.utils.StringUtils.creatAcacheKey;

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
    public void getBookMixAToc(String book, String view) {
        String key = creatAcacheKey("book-toc", book, view);
        Flowable<BookMixAToc.mixToc> mixTocFlowable = mBookApi.getBookMixToc(book, view)
                .map(bookMixAToc -> bookMixAToc.mixToc)
                .compose(RxUtils.rxCacheListHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BookMixAToc.mixToc.class), mixTocFlowable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mixToc -> {
                    List<BookMixAToc.mixToc.Chapters> chapters = ((BookMixAToc.mixToc) mixToc).chapters;
                    if (chapters != null && !chapters.isEmpty() && mView != null) {
                        mView.showBookToc(chapters);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
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

    @Override
    public void getBookResource(String book) {
        String key = StringUtils.creatAcacheKey("book-resource");
        Flowable<BookResource> resourceFlowable = mBookApi.getBookResource("summary", book)
                .compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, BookResource.class), resourceFlowable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookResource -> {
                    if (bookResource != null && mView != null) {
                        mView.showBookResource((BookResource) bookResource);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
