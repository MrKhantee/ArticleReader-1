package com.article.core.book.presenter;

import com.article.base.RxPresenter;
import com.article.common.api.BookApi;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.bean.SubRankList;
import com.article.core.book.contract.SubRankFragmentContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/10.
 * Desc：
 */

public class SubRankFragmentPresenter extends RxPresenter<SubRankFragmentContract.View>
        implements SubRankFragmentContract.Presenter<SubRankFragmentContract.View> {

    private BookApi mBookApi;

    @Inject
    public SubRankFragmentPresenter(BookApi bookApi) {
        mBookApi = bookApi;
    }

    @Override
    public void getRankList(String id) {
        Disposable disposable = mBookApi.getSubRanList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subRankList -> {
                    List<SubRankList.RankingBean.BooksBean> books = subRankList.ranking.books;
                    BooksByCats cats = new BooksByCats();
                    cats.books = new ArrayList<>();
                    for (SubRankList.RankingBean.BooksBean bean : books) {
                        cats.books.add(new BooksByCats.BooksBean(bean._id, bean.cover, bean.title,
                                bean.author, bean.cat, bean.shortIntro, bean.latelyFollower, bean.retentionRatio));
                    }
                    mView.showRankList(cats);
                }, throwable -> {
                    mView.showError();
                }, () -> mView.complete());

        addSubscribe(disposable);
    }


}
