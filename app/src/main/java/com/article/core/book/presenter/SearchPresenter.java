package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.LogUtils;
import com.article.common.utils.RxUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.HotWords;
import com.article.core.book.bean.SearchDetail;
import com.article.core.book.contract.SearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/7/28.
 * Desc：
 */

public class SearchPresenter extends RxPresenter<SearchContract.View>
        implements SearchContract.Presenter<SearchContract.View> {

    BookApi mBookApi;

    @Inject
    public SearchPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getHotWordList() {
        String key = StringUtils.creatAcacheKey("hot-word-list");
        Flowable<HotWords> compose = mBookApi.getHotWords().compose(RxUtils.rxCacheBeanHelper(key));
        Disposable disposable = Flowable.concat(RxUtils.rxCreateDiskFlowable(key, HotWords.class), compose)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotWords -> {
                    List<String> list = ((HotWords) hotWords).hotWords;
                    if (list != null && list.size() > 0 && mView != null) {
                        mView.showHotWordList(list);
                    }
                }, throwable -> LogUtils.e("onError: " + throwable), () -> {

                });
        addSubscribe(disposable);
    }

    @Override
    public void getAutoCompleteList(String query) {
        Disposable disposable = mBookApi.autoComplete(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(autoComplete -> {
                    List<String> keyWords = autoComplete.keyWords;
                    if (keyWords != null && keyWords.size() > 0 && mView != null) {
                        mView.showAutoCompleteList(keyWords);
                    }
                }, throwable -> LogUtils.e(throwable.toString()), () -> {

                });
        addSubscribe(disposable);
    }

    @Override
    public void getSearchResultList(String query) {
        Disposable disposable = mBookApi.searchBooks(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchDetail -> {
                    List<SearchDetail.SearchBooks> books = searchDetail.books;
                    if (books != null && !books.isEmpty() && mView != null) {
                        mView.showSearchResultList(books);
                    }
                }, throwable -> LogUtils.e(throwable.toString()), () -> {

                });
        addSubscribe(disposable);
    }
}
