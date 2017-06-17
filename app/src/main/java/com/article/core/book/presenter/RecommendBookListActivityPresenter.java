package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.contract.RecommendBookListActivityConstract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class RecommendBookListActivityPresenter extends RxPresenter<RecommendBookListActivityConstract.View>
        implements RecommendBookListActivityConstract.Presenter<RecommendBookListActivityConstract.View> {

    BookApi mBookApi;

    @Inject
    public RecommendBookListActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
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
