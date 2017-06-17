package com.article.di.component;

import com.article.core.book.ui.activity.BookDetailActivity;
import com.article.core.book.ui.activity.BookListActivity;
import com.article.core.book.ui.activity.BookListDetailActivity;
import com.article.core.book.ui.activity.RecommendBookListActivity;
import com.article.core.book.ui.activity.SubOtherRankActivity;
import com.article.core.book.ui.activity.TopRankActivity;
import com.article.core.book.ui.fragment.BookListFragment;
import com.article.core.book.ui.fragment.SubRankFragment;

import dagger.Component;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */
@Component(dependencies = AppComponent.class)
public interface BookComponent {

    TopRankActivity inject(TopRankActivity topRankActivity);

    SubOtherRankActivity inject(SubOtherRankActivity subOtherRankActivity);

    BookDetailActivity inject(BookDetailActivity bookDetailActivity);

    RecommendBookListActivity inject(RecommendBookListActivity recommendBookListActivity);

    BookListDetailActivity inject(BookListDetailActivity bookListDetailActivity);

    BookListActivity inject(BookListActivity bookListActivity);

    SubRankFragment inject(SubRankFragment subRankFragment);

    BookListFragment inject(BookListFragment bookListFragment);
}
