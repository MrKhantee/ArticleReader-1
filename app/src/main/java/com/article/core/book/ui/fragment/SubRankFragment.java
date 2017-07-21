package com.article.core.book.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseMVPFragment;
import com.article.core.book.adapter.SubRankFragmentAdapter;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.contract.SubRankFragmentContract;
import com.article.core.book.presenter.SubRankFragmentPresenter;
import com.article.core.book.ui.activity.BookDetailActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/10.
 * Desc：
 */

public class SubRankFragment extends BaseMVPFragment<SubRankFragmentPresenter>
        implements SubRankFragmentContract.View {

    public final static String BUNDLE_ID = "_id";
    private String id = "";

    @Inject
    SubRankFragmentPresenter mPresenter;

    @BindView(R.id.fragment_sub_rank_rl)
    RecyclerView mFragmentSubRankRl;
    @BindView(R.id.sub_rank_srl)
    SwipeRefreshLayout mSubRankSrl;

    private SubRankFragmentAdapter mAdapter;
    private List<BooksByCats.BooksBean> mBeanList = new ArrayList<>();

    public static SubRankFragment newInstance(String id) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initData() {
        id = this.getArguments().getString(BUNDLE_ID);
        mAdapter = new SubRankFragmentAdapter(mContext, mBeanList);

        mSubRankSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSubRankSrl.measure(0, 0);
        mSubRankSrl.setRefreshing(true);
    }

    @Override
    public void configViews() {
//        showDialog();
        mPresenter.attachView(this);
        mPresenter.getRankList(id);

        mFragmentSubRankRl.setLayoutManager(new LinearLayoutManager(mContext));
        mFragmentSubRankRl.setHasFixedSize(true);
        mFragmentSubRankRl.setAdapter(mAdapter);

        mAdapter.setClickListener((view, position) -> BookDetailActivity.startActivity(mContext, mAdapter.getData(position)._id));
        mSubRankSrl.setOnRefreshListener(() -> mPresenter.getRankList(id));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }


    @Override
    public void showError() {
//        dismissDialog();
        mSubRankSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
//        dismissDialog();
        mAdapter.notifyDataSetChanged();
        mSubRankSrl.setRefreshing(false);
    }

    @Override
    public void showRankList(BooksByCats data) {
        mAdapter.clear();
        mAdapter.addAll(data.books);
    }
}
