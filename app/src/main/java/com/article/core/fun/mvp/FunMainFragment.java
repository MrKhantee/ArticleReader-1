package com.article.core.fun.mvp;

import android.os.Bundle;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.core.fun.bean.FunBean;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerFunComponent;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class FunMainFragment extends BaseFragment implements FunFragmentContract.View {
    public static final String FUN_TAG = "tag";
    //热门
    public static final String FUN_TAG_HOT = "hot";
    //24小时
    public static final String FUN_TAG_24H = "24h";
    //热图
    public static final String FUN_TAG_IMGRANK = "imgrank";
    //文字
    public static final String FUN_TAG_TEXT = "text";
    //糗图
    public static final String FUN_TAG_PIC = "pic";
    //新鲜
    public static final String FUN_TAG_NEW = "new";
    //加载的页面
    private int page = 1;
    private String mTag;

    @Inject
    FunFragmentPresenter mPresenter;

    public static FunMainFragment newInstance(String tag) {
        FunMainFragment mainFragment = new FunMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FUN_TAG, tag);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        mTag = arguments.getString(FUN_TAG);

    }

    @Override
    public void configViews() {
        mPresenter.getQiushi("8hr", "2");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fun_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFunComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showQiuShi(List<FunBean> mFunBeans) {

    }
}
