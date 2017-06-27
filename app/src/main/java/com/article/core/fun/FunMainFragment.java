package com.article.core.fun;

import android.os.Bundle;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.di.component.AppComponent;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class FunMainFragment extends BaseFragment {
    public static final String FUN_TAG = "tag";
    //热门
    public static final String FUN_TAG_HOT = "hot";
    //24小时
    public static final String FUN_TAG_24H = "24h";
    //热图
    public static final String FUN_TAG_IMGRANK = "imgrank";
    //文字
    public static final String FUN_TAG_TEXT = "text";
    //穿越
    public static final String FUN_TAG_HISTORY = "history";
    //糗图
    public static final String FUN_TAG_PIC = "pic";
    //新鲜
    public static final String FUN_TAG_NEW = "new";


    public static FunMainFragment newInstance(String tag) {
        FunMainFragment mainFragment = new FunMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FUN_TAG, tag);
        mainFragment.setArguments(bundle);
        return mainFragment;
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
        return R.layout.fragment_fun_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
