package com.article.core.welfare.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.core.welfare.adapter.GankMeiziAdapter;
import com.article.core.welfare.bean.GankMeiziBean;
import com.article.core.welfare.contract.GankMeiZiContract;
import com.article.core.welfare.presenter.GankMeiZiPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerWelfareComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/7/14.
 * Desc：
 */

public class GankMeiZiFragment extends BaseFragment implements GankMeiZiContract.View {
    //每一页的总数
    private int pageNum = 20;
    //第几页
    private int page = 1;
    @BindView(R.id.gank_meizi_srl)
    SwipeRefreshLayout mGankSrl;
    @BindView(R.id.gank_meizi_rv)
    RecyclerView mGankRv;

    @Inject
    GankMeiZiPresenter mPresenter;

    private List<GankMeiziBean.ResultsBean> mResultsBeanList;
    private GankMeiziAdapter mAdapter;

    public static GankMeiZiFragment newInstance() {
        GankMeiZiFragment fragment = new GankMeiZiFragment();
        return fragment;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void initData() {
        mResultsBeanList = new ArrayList<>();
        mAdapter = new GankMeiziAdapter(mContext, mResultsBeanList);
    }

    @Override
    public void configViews() {

        mGankSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mGankSrl.measure(0, 0);
        mGankSrl.setRefreshing(true);

        mGankSrl.setOnRefreshListener(this::refreshIng);

        mGankRv.setHasFixedSize(true);
        mGankRv.setLayoutManager(new LinearLayoutManager(mContext));
        mGankRv.setAdapter(mAdapter);

        mPresenter.getGankMeiZi(20, 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_meizi;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerWelfareComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showError() {
        mGankSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mGankSrl.setRefreshing(false);
    }

    /**
     * 刷新数据
     */
    private void refreshIng() {
        page = 1;
        mPresenter.getGankMeiZi(pageNum, page);
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        page++;
        mPresenter.getGankMeiZi(pageNum, page);
    }

    @Override
    public void showGankMeizi(GankMeiziBean data) {
        List<GankMeiziBean.ResultsBean> results = data.getResults();
//        mAdapter.clear();
        mAdapter.addAll(results);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
