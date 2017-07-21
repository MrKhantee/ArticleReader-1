package com.article.core.fun;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerFunComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class FunMainActivity extends BaseMVPActivity<FunPresenter> implements FunContract.View {

    @BindView(R.id.fun_tb)
    Toolbar mFunTb;
    @BindView(R.id.fun_list_srl)
    SwipeRefreshLayout mFunListSrl;
    @BindView(R.id.fun_list_rv)
    RecyclerView mFunListRv;

    @Inject
    FunPresenter mFunPresenter;

    FunListAdapter mAdapter;
    private List<FunBean.ItemsBean> mItemsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fun;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FunMainActivity.class));
    }

    @Override
    public void initData() {
        mItemsBeanList = new ArrayList<>();
        mAdapter = new FunListAdapter(this, mItemsBeanList);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFunComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mFunTb);
        mFunTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {

        mFunListSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mFunListSrl.measure(0, 0);
        mFunListSrl.setRefreshing(true);
        mFunListSrl.setOnRefreshListener(() -> refresh());

        mFunListRv.setHasFixedSize(true);
        mFunListRv.setLayoutManager(new LinearLayoutManager(this));
        mFunListRv.setAdapter(mAdapter);

//        mFunPresenter.getQiuShiBaiKe("1", "30");
        refresh();
    }

    private void refresh() {
        mPresenter.getQiuShiBaiKe("1", "30");
    }

    private void onLoadMore() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        mFunListSrl.setRefreshing(false);
    }

    @Override
    public void showQiuShi(FunBean funBean) {
        List<FunBean.ItemsBean> items = funBean.getItems();
//        mAdapter.clear();
        mAdapter.addAll(items);
    }
}
