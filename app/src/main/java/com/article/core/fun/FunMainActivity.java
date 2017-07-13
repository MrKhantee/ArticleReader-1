package com.article.core.fun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerFunComponent;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class FunMainActivity extends BaseMVPActivity<FunPresenter> implements FunContract.View {

    @BindView(R.id.fun_tb)
    Toolbar mFunTb;

    @BindView(R.id.fun_list_rv)
    RecyclerView mFunListRv;

    @Inject
    FunPresenter mFunPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fun;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FunMainActivity.class));
    }

    @Override
    protected void initData() {

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
        mFunPresenter.getQiuShiBaiKe("1", "30");
    }


    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showQiuShi(FunBean funBean) {

    }
}
