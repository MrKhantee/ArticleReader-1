package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.adapter.BooksByTagAdapter;
import com.article.core.book.bean.BooksByTag;
import com.article.core.book.contract.BooksByTagContract;
import com.article.core.book.presenter.BooksByTagPresenter;
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

public class BooksByTagActivity extends BaseActivity implements BooksByTagContract.View {

    public static final String INTENT_TAG = "tag";
    private String tag = "";
    private int current = 0;
    @BindView(R.id.book_by_tag_tb)
    Toolbar mTagTb;
    @BindView(R.id.book_by_tag_srl)
    SwipeRefreshLayout mTagSrl;
    @BindView(R.id.book_by_tag_rv)
    RecyclerView mTagRv;

    @Inject
    BooksByTagPresenter mPresenter;

    private List<BooksByTag.TagBook> mTagBooks;
    private BooksByTagAdapter mTagAdapter;
    private int limit = 20;

    public static void startActivity(Context context, String tag) {
        context.startActivity(new Intent(context, BooksByTagActivity.class)
                .putExtra(INTENT_TAG, tag));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_books_by_tag;
    }

    @Override
    protected void initData() {
        tag = getIntent().getStringExtra(INTENT_TAG);

        mTagBooks = new ArrayList<>();
        mTagAdapter = new BooksByTagAdapter(this, mTagBooks);

        mPresenter.attachView(this);

        mTagSrl.measure(0, 0);
        mTagSrl.setRefreshing(true);
        mTagSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);
        mTagSrl.setOnRefreshListener(this::onRefresh);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mTagTb);
        mTagTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!tag.equals("")) {
            mTagTb.setTitle(tag);
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    boolean isLoading;

    @Override
    public void configViews() {
        mTagRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mTagRv.setLayoutManager(manager);
        mTagRv.setAdapter(mTagAdapter);
        mTagRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                int itemCount = mTagAdapter.getItemCount();
                if ((itemCount - 1) == lastVisibleItemPosition) {
                    boolean refreshing = mTagSrl.isRefreshing();
                    if (refreshing) {
                        mTagAdapter.notifyItemRemoved(mTagAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mTagRv.post(() -> {
                            onLoadMore();
                            isLoading = false;
                        });
                    }
                }
            }
        });

        onRefresh();

        mTagAdapter.setItemClickListener((view, position) -> BookDetailActivity
                .startActivity(BooksByTagActivity.this, mTagBooks.get(position)._id));
    }

    @Override
    public void showError() {
        mTagSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mTagSrl.setRefreshing(false);
    }

    private void onRefresh() {
        mPresenter.getBooksByTag(tag, 0 + "", limit + "");
        mTagAdapter.setRefresh(true);
    }

    private void onLoadMore() {
        mPresenter.getBooksByTag(tag, current + "", limit + "");
        mTagAdapter.setRefresh(false);
    }

    @Override
    public void showBooksByTag(List<BooksByTag.TagBook> list, boolean isRefresh) {
        if (isRefresh) {
            current = 0;
            mTagAdapter.clear();
        }
        mTagAdapter.addAll(list);
        current += list.size();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onLoadComplete(boolean isSuccess, String msg) {

    }
}
