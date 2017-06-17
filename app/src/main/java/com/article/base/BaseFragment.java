package com.article.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.CoreApplication;
import com.article.di.component.AppComponent;
import com.article.widget.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Amos on 2017/6/8.
 * Desc：全局的基类fragment，没有mvp
 */

public abstract class BaseFragment extends SupportFragment {
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;

    protected CustomDialog mDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mActivity = getSupportActivity();
        mContext = mActivity;
        return mView;
    }

    protected Activity getSupportActivity() {
        return super.getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        setupActivityComponent(CoreApplication.getInstance().getAppComponent());
        attachView();
        initData();
        configViews();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        isInited = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }

    /**
     * 当view挂载的时候
     */
    public abstract void attachView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 设置component
     *
     * @param appComponent
     */
    protected abstract void setupActivityComponent(AppComponent appComponent);

    private CustomDialog getDialog() {
        if (mDialog == null) {
            mDialog = new CustomDialog(getActivity());
            mDialog.setCancelable(true);
        }
        return mDialog;
    }

    protected void showDialog() {
        getDialog().show();
    }

    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


}
