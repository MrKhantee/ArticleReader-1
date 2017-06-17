package com.article.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.article.CoreApplication;
import com.article.di.component.AppComponent;
import com.article.widget.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Amos on 2017/6/8.
 * Desc：全局基类的activity，不设置mvp
 */

public abstract class BaseActivity extends SupportActivity {
    protected Activity mContext;
    private Unbinder mUnBinder;
    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //初始化butterknife
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        //添加activity
        CoreApplication.getInstance().addActivity(this);
        setupActivityComponent(CoreApplication.getInstance().getAppComponent());
        initData();
        initToolBar();
        configViews();
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化事件和数据
     */
    protected abstract void initData();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    /**
     * 初始化ToolBar
     */
    public abstract void initToolBar();

    /**
     * 对各种控件进行设置、适配、填充数据
     */
    public abstract void configViews();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        dismissDialog();
        CoreApplication.getInstance().removeActivity(this);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressedSupport());
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
