package com.article.core.book.presenter;

import android.content.Context;

import com.article.base.RealmHelper;
import com.article.base.RxBus;
import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.ACache;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.CollectionBook;
import com.article.core.book.contract.BookShelfContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/22.
 * Desc：
 */

public class BookShelfPresenter extends RxPresenter<BookShelfContract.View>
        implements BookShelfContract.Presenter<BookShelfContract.View> {


    BookApi mBookApi;
    RealmHelper mRealmHelper;
    Context mContext;

    @Inject
    public BookShelfPresenter(BookApi bookApi, RealmHelper realmHelper, Context context) {
        mBookApi = bookApi;
        mRealmHelper = realmHelper;
        mContext = context;
    }

    @Override
    public void getCollectionBook() {
//        Flowable.create((FlowableOnSubscribe<CollectionBook>) e -> {
//            List<CollectionBook> allCollectionBook = mRealmHelper.getAllCollectionBook();
//            for (CollectionBook book : allCollectionBook) {
//                e.onNext(book);
//            }
//            e.onComplete();
//        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(collectionBook -> {
//                    List<CollectionBook> books = new ArrayList<>();
//                    books.add(collectionBook);
//                    mView.showCollectionBook(books);
//                }, throwable -> mView.showError(), () -> mView.complete());
        List<CollectionBook> collectionBooks = mRealmHelper.getAllCollectionBook();
        for (CollectionBook book : collectionBooks) {
            RxBus.getInstance().post(book);
        }
    }

    @Override
    public void getTopMac(String bookId) {
        Disposable disposable = mBookApi.getBookToc("chapters", bookId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookMixAToc -> {
                    ACache.get(mContext).put(bookId + "bookToc", bookMixAToc);
                    if (mView != null && bookMixAToc != null) {
                        List<BookMixAToc.mixToc.Chapters> chapters = bookMixAToc.mixToc.chapters;
                        mView.showChapter(bookId, chapters);
                    }
                }, throwable -> mView.showError(), () -> mView.complete());
        addSubscribe(disposable);
    }
}
