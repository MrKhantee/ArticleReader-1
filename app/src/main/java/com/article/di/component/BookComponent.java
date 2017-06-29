package com.article.di.component;

import com.article.core.book.ui.activity.BookDetailActivity;
import com.article.core.book.ui.activity.BookListActivity;
import com.article.core.book.ui.activity.BookListDetailActivity;
import com.article.core.book.ui.activity.BookReadActivity;
import com.article.core.book.ui.activity.BookResourceActivity;
import com.article.core.book.ui.activity.BooksByAuthorActivity;
import com.article.core.book.ui.activity.BooksByTagActivity;
import com.article.core.book.ui.activity.RecommendBookListActivity;
import com.article.core.book.ui.activity.SubCategoryActivity;
import com.article.core.book.ui.activity.SubOtherRankActivity;
import com.article.core.book.ui.activity.TopCategoryActivity;
import com.article.core.book.ui.activity.TopRankActivity;
import com.article.core.book.ui.fragment.BookListFragment;
import com.article.core.book.ui.fragment.BookShelfFragment;
import com.article.core.book.ui.fragment.SubCategoryFragment;
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

    TopCategoryActivity inject(TopCategoryActivity topCategoryActivity);

    SubCategoryActivity inject(SubCategoryActivity subCategoryActivity);

    BooksByTagActivity inject(BooksByTagActivity booksByTagActivity);

    BooksByAuthorActivity inject(BooksByAuthorActivity booksByAuthorActivity);

    BookReadActivity inject(BookReadActivity bookReadActivity);

    BookResourceActivity inject(BookResourceActivity bookResourceActivity);

    SubRankFragment inject(SubRankFragment subRankFragment);

    BookListFragment inject(BookListFragment bookListFragment);

    SubCategoryFragment inject(SubCategoryFragment subCategoryFragment);

    BookShelfFragment inject(BookShelfFragment shelfFragment);
}
