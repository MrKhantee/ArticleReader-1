package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.common.utils.LogUtils;
import com.article.common.utils.ToastUtils;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.Recommend;
import com.article.core.book.contract.BookActivityContract;
import com.article.core.book.manager.CollectionsManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/7/21.
 * Desc：
 */

public class BookActivityPresenter extends RxPresenter<BookActivityContract.View> implements
        BookActivityContract.Presenter<BookActivityContract.View> {

    BookApi mBookApi;

    @Inject
    public BookActivityPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void syncBookShelf() {
        List<Recommend.RecommendBooks> list = CollectionsManager.getInstance().getCollectionList();
        List<Flowable<BookMixAToc.mixToc>> flowables = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Recommend.RecommendBooks bean : list) {
                if (!bean.isFromSD) {
                    Flowable<BookMixAToc.mixToc> chapters = mBookApi.getBookMixToc(bean._id, "chapters")
                            .map(bookMixAToc -> bookMixAToc.mixToc);
                    flowables.add(chapters);
                }
            }
        } else {
            ToastUtils.showSingleToast("书架中还没有书，先去收藏吧！");
            mView.syncBookShelfCompleted();
            return;
        }
        Disposable disposable = Flowable.mergeDelayError(flowables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mixToc -> {
                    String lastChapter = mixToc.chapters.get(mixToc.chapters.size() - 1).title;
                    CollectionsManager.getInstance().setLastChapterAndLatelyUpdate(mixToc.book, lastChapter, mixToc.chaptersUpdated);
                }, throwable -> {
                    LogUtils.e("onError: " + throwable);
                    mView.showError();
                }, () ->{
                    if (mView!=null){
                        mView.syncBookShelfCompleted();
                    }
                });
        addSubscribe(disposable);
    }
}
