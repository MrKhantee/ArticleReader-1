package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.core.book.adapter.TopRankActivityAdapter;
import com.article.core.book.bean.TopRankList;
import com.article.core.book.contract.TopRankActivityContract;
import com.article.core.book.presenter.TopRankActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TopRankActivity extends BaseMVPActivity<TopRankActivityPresenter>
        implements TopRankActivityContract.View {

    @BindView(R.id.tb_top_rank)
    Toolbar mTbTopRank;
    @BindView(R.id.elv_male)
    ExpandableListView mElvMale;
    @BindView(R.id.elv_female)
    ExpandableListView mElvFemale;
    @BindView(R.id.ranking_srl)
    SwipeRefreshLayout mRankSrl;
    @Inject
    TopRankActivityPresenter mPresenter;


    private List<TopRankList.MaleBean> mMaleGroupList;
    private List<List<TopRankList.MaleBean>> mMaleChildList;
    private TopRankActivityAdapter mMaleAdapter;

    private List<TopRankList.MaleBean> mFemaleGroupList;
    private List<List<TopRankList.MaleBean>> mFemaleChildList;
    private TopRankActivityAdapter mFemaleAdapter;

    private OnItemClickListener mItemClickListener;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TopRankActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTbTopRank.setTitle("排行榜");
    }

    @Override
    public void initData() {

        mItemClickListener = new OnItemClickListener();
        mMaleGroupList = new ArrayList<>();
        mMaleChildList = new ArrayList<>();

        mFemaleGroupList = new ArrayList<>();
        mFemaleChildList = new ArrayList<>();


        mMaleAdapter = new TopRankActivityAdapter(getApplicationContext(), mMaleGroupList, mMaleChildList);
        mFemaleAdapter = new TopRankActivityAdapter(getApplicationContext(), mFemaleGroupList, mFemaleChildList);

        mMaleAdapter.setItemClickListener(mItemClickListener);
        mFemaleAdapter.setItemClickListener(mItemClickListener);

        mElvMale.setAdapter(mMaleAdapter);
        mElvFemale.setAdapter(mFemaleAdapter);


        mTbTopRank.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void showError() {
        mRankSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
//        dismissDialog();
        mRankSrl.setRefreshing(false);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mTbTopRank);
        mTbTopRank.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        mPresenter.getRankList();

        mRankSrl.measure(0, 0);
        mRankSrl.setRefreshing(true);
        mRankSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);
        mRankSrl.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        mPresenter.getRankList();
    }

    @Override
    public void showRankList(TopRankList topRankList) {
        mMaleGroupList.clear();
        mFemaleGroupList.clear();

        updateMale(topRankList);
        updateFemale(topRankList);
    }

    private void updateMale(TopRankList topRankList) {
        List<TopRankList.MaleBean> male = topRankList.getMale();
        List<TopRankList.MaleBean> collapse = new ArrayList<>();
        for (TopRankList.MaleBean bean : male) {
            if (bean.collapse) {
                collapse.add(bean);
            } else {
                mMaleGroupList.add(bean);
                mMaleChildList.add(new ArrayList<>());
            }
        }
        if (collapse.size() > 0) {
            TopRankList.MaleBean bean = new TopRankList.MaleBean();
            bean.setTitle("别人家的排行榜");
            mMaleGroupList.add(bean);
            mMaleChildList.add(collapse);
        }
        mMaleAdapter.notifyDataSetChanged();
    }

    private void updateFemale(TopRankList topRankList) {
        List<TopRankList.MaleBean> female = topRankList.getFemale();
        List<TopRankList.MaleBean> collapse = new ArrayList<>();
        for (TopRankList.MaleBean bean : female) {
            if (bean.collapse) {
                collapse.add(bean);
            } else {
                mFemaleGroupList.add(bean);
                mFemaleChildList.add(new ArrayList<>());
            }
        }
        if (collapse.size() > 0) {
            TopRankList.MaleBean bean = new TopRankList.MaleBean();
            bean.setTitle("别人家的排行榜");
            mFemaleGroupList.add(bean);
            mFemaleChildList.add(collapse);
        }
        mFemaleAdapter.notifyDataSetChanged();
    }

    class OnItemClickListener implements TopRankActivityAdapter.OnRvItemClickListener<TopRankList.MaleBean> {
        @Override
        public void onItemClick(View view, int position, TopRankList.MaleBean data) {
            if (data.monthRank == null) {
                SubOtherRankActivity.startActivity(TopRankActivity.this, data.get_id(), data.getTitle());
            } else {
                SubRankActivity.startActivity(TopRankActivity.this, data.get_id(),
                        data.monthRank, data.getTotalRank(), data.title);
            }

        }
    }
}
