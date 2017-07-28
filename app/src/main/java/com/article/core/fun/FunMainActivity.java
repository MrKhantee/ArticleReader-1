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
    private int page = 1;
    private int pageNum = 30;

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
        mPresenter.attachView(this);

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
        mFunListSrl.setOnRefreshListener(this::refresh);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mFunListRv.setHasFixedSize(true);
        mFunListRv.setLayoutManager(manager);
        mFunListRv.setAdapter(mAdapter);

        refresh();

        mFunListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void refresh() {
        page = 1;
        mPresenter.getQiuShiBaiKe(page, pageNum);
    }

    private void onLoadMore() {
        page++;
        mFunPresenter.getQiuShiBaiKe(page, pageNum);
    }

    @Override
    public void showError() {
        mFunListSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mFunListSrl.setRefreshing(false);
    }

    @Override
    public void showQiuShi(FunBean funBean) {
        List<FunBean.ItemsBean> items = funBean.getItems();
        mAdapter.addAll(items);
    }
}
