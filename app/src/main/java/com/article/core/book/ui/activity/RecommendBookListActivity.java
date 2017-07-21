package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.adapter.RecommendBookListActivityAdapter;
import com.article.core.book.bean.RecommendBookList;
import com.article.core.book.contract.RecommendBookListActivityConstract;
import com.article.core.book.presenter.RecommendBookListActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RecommendBookListActivity extends BaseMVPActivity<RecommendBookListActivityPresenter>
        implements RecommendBookListActivityConstract.View, OnRvItemClickListener {
    public static String INTENT_BOOK_ID = "bookId";
    @BindView(R.id.recommend_book_list_tb)
    Toolbar mRecommendBookListTb;
    @BindView(R.id.recommend_book_list_rv)
    RecyclerView mRecommendBookListRv;

    @Inject
    RecommendBookListActivityPresenter mPresenter;

    private RecommendBookListActivityAdapter mAdapter;
    private List<RecommendBookList.RecommendBook> mBookList = new ArrayList<>();

    private String bookId;

    public static void startActivity(Context context, String bookId) {
        context.startActivity(new Intent(context, RecommendBookListActivity.class)
                .putExtra(INTENT_BOOK_ID, bookId));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend_book_list;
    }

    @Override
    public void initData() {
        bookId = getIntent().getStringExtra(INTENT_BOOK_ID);
        mAdapter = new RecommendBookListActivityAdapter(this, mBookList, this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mRecommendBookListTb);
        mRecommendBookListTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        showDialog();
        mPresenter.attachView(this);
        mPresenter.getRecommendBookList(bookId, "20");

        mRecommendBookListRv.setHasFixedSize(true);
        mRecommendBookListRv.setLayoutManager(new LinearLayoutManager(this));
        mRecommendBookListRv.addItemDecoration(new SupportDividerItemDecoration(this, SupportDividerItemDecoration.VERTICAL_LIST));
        mRecommendBookListRv.setAdapter(mAdapter);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showRecommendBookList(List<RecommendBookList.RecommendBook> recommendBooks) {
        mBookList.clear();
        mBookList.addAll(recommendBooks);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        BookListDetailActivity.startActivity(this, mBookList.get(position).id);
    }
}
