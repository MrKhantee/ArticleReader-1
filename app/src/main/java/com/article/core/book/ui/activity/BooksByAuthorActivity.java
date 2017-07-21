package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.adapter.BooksByAuthorAdapter;
import com.article.core.book.bean.BooksByAuthor;
import com.article.core.book.contract.BooksByAuthorContract;
import com.article.core.book.presenter.BooksByAuthorPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByAuthorActivity extends BaseActivity implements BooksByAuthorContract.View {

    @BindView(R.id.book_by_author_tb)
    Toolbar mAuthorTb;
    @BindView(R.id.book_by_author_rv)
    RecyclerView mAuthorRv;
    @BindView(R.id.book_by_author_srl)
    SwipeRefreshLayout mAuthorSrl;


    public static final String INTENT_AUTHOR = "author";
    private String author = "";

    private List<BooksByAuthor.BooksBean> mBeanList;
    private BooksByAuthorAdapter mAuthorAdapter;

    @Inject
    BooksByAuthorPresenter mPresenter;

    public static void startActivity(Context context, String author) {
        context.startActivity(new Intent(context, BooksByAuthorActivity.class)
                .putExtra(INTENT_AUTHOR, author));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_books_by_author;
    }

    @Override
    public void initData() {
        author = getIntent().getStringExtra(INTENT_AUTHOR);

        mPresenter.attachView(this);
        mPresenter.getBooksByAuthor(author);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mAuthorTb);
        mAuthorTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuthorTb.setTitle(author);
    }

    @Override
    public void configViews() {
        mBeanList = new ArrayList<>();
        mAuthorAdapter = new BooksByAuthorAdapter(this, mBeanList);
        mAuthorSrl.measure(0, 0);
        mAuthorSrl.setRefreshing(true);
        mAuthorSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);
        mAuthorSrl.setOnRefreshListener(() -> {
            mPresenter.getBooksByAuthor(author);
        });

        mAuthorRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAuthorRv.setLayoutManager(manager);
        mAuthorRv.setAdapter(mAuthorAdapter);
        mAuthorAdapter.setItemClickListener((view, position) -> {
            BookDetailActivity.startActivity(BooksByAuthorActivity.this, mBeanList.get(position)._id);
        });
    }

    @Override
    public void showError() {
        mAuthorSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mAuthorSrl.setRefreshing(false);
    }

    @Override
    public void showBooks(List<BooksByAuthor.BooksBean> beanList) {
        mAuthorAdapter.clear();
        mAuthorAdapter.addAll(beanList);
    }


}
