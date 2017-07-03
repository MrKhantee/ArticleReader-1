package com.article.core.fun;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.fun.mvp.FunMainFragment;
import com.article.di.component.AppComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class FunMainActivity extends BaseActivity {

    @BindView(R.id.fun_tb)
    Toolbar mFunTb;
    @BindView(R.id.fun_tl)
    TabLayout mFunTl;
    @BindView(R.id.fun_vp)
    ViewPager mFunVp;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mPagerAdapter;

    private List<String> names;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fun;
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FunMainActivity.class));
    }

    @Override
    protected void initData() {
        names = Arrays.asList(mContext.getResources().getStringArray(R.array.fun_main));
        mFragments = new ArrayList<>();
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_HOT));
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_24H));
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_IMGRANK));
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_TEXT));
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_PIC));
        mFragments.add(FunMainFragment.newInstance(FunMainFragment.FUN_TAG_NEW));

        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return names.get(position);
            }
        };
        mFunVp.setAdapter(mPagerAdapter);
        mFunTl.setupWithViewPager(mFunVp);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mFunTb);
        mFunTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {

    }


}
