package com.article.core.book.ui.fragment;

import android.os.Bundle;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.common.Constant;
import com.article.di.component.AppComponent;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public class SubCategoryFragment extends BaseFragment {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        major = arguments.getString(BUNDLE_MAJOR);
        minor = arguments.getString(BUNDLE_MINOR);
        gender = arguments.getString(BUNDLE_GENDER);
        type = arguments.getString(BUNDLE_TYPE);
    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_category;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
