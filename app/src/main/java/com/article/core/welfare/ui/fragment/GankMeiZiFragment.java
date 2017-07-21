package com.article.core.welfare.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.di.component.AppComponent;

import butterknife.BindView;

/**
 * Created by Amos on 2017/7/14.
 * Desc：
 */

public class GankMeiZiFragment extends BaseFragment {
    //每一页的总数
    private int pageNum = 20;
    //第几页
    private int page = 1;
    @BindView(R.id.gank_meizi_srl)
    SwipeRefreshLayout mGankSrl;
    @BindView(R.id.gank_meizi_rv)
    RecyclerView mGankRv;

    public static GankMeiZiFragment newInstance() {
        GankMeiZiFragment fragment = new GankMeiZiFragment();
        return fragment;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_meizi;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
