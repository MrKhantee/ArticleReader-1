package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.adapter.BookResourceAdapter;
import com.article.core.book.bean.BookResource;
import com.article.core.book.contract.BookResourceContract;
import com.article.core.book.presenter.BookResourcePresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/29.
 * Desc：
 */

public class BookResourceActivity extends BaseActivity implements BookResourceContract.View {

    public static final String INTENT_BOOK_ID = "book_id";
    public static final String INTENT_SELECT_HOST = "host";

    @BindView(R.id.book_source_tb)
    Toolbar mResourceTb;
    @BindView(R.id.book_resource_rv)
    RecyclerView mResourceRv;
    @BindView(R.id.book_resource_srl)
    SwipeRefreshLayout mResourceSrl;

    @Inject
    BookResourcePresenter mPresenter;

    private String bookId;
    private String host;

    private List<BookResource> mResourceBeans;
    private BookResourceAdapter mResourceAdapter;

    public static void startActivity(Context context, String bookId, String host) {
        context.startActivity(new Intent(context, BookResourceActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId)
                .putExtra(INTENT_SELECT_HOST, host));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_resource;
    }

    @Override
    protected void initData() {
        mPresenter.attachView(this);
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
        host = getIntent().getStringExtra(INTENT_SELECT_HOST);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mResourceTb);
        mResourceTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        mResourceBeans = new ArrayList<>();
        mResourceAdapter = new BookResourceAdapter(this, mResourceBeans, host);

        mResourceSrl.measure(0, 0);
        mResourceSrl.setRefreshing(true);
        mResourceSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);

        mResourceRv.setHasFixedSize(true);
        mResourceRv.setLayoutManager(new LinearLayoutManager(this));
        mResourceRv.setAdapter(mResourceAdapter);

        mPresenter.getBookResource("summary", bookId);
    }

    @Override
    public void showError() {
        mResourceSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mResourceSrl.setRefreshing(false);
        mResourceSrl.setEnabled(false);

        mResourceAdapter.addAll(mResourceBeans);
    }


    @Override
    public void showBookResource(BookResource bookResource) {
        mResourceBeans.add(bookResource);
        System.out.println("========>" + bookResource);
    }
}
