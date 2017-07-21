package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.ui.fragment.SubRankFragment;
import com.article.di.component.AppComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class SubRankActivity extends BaseActivity {
    public static final String INTENT_WEEK = "_id";
    public static final String INTENT_MONTH = "month";
    public static final String INTENT_ALL = "all";
    public static final String INTENT_TITLE = "title";

    @BindView(R.id.sub_rank_tb)
    Toolbar mSubRankTb;
    @BindView(R.id.sub_rank_tl)
    TabLayout mSubRankTl;
    @BindView(R.id.sub_rank_vp)
    ViewPager mSubRanVp;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mNames;

    /**
     * 跳转到当前Activity
     *
     * @param context
     * @param week
     * @param month
     * @param all
     * @param title
     */
    public static void startActivity(Context context, String week, String month, String all, String title) {
        context.startActivity(new Intent(context, SubRankActivity.class)
                .putExtra(INTENT_WEEK, week)
                .putExtra(INTENT_MONTH, month)
                .putExtra(INTENT_ALL, all)
                .putExtra(INTENT_TITLE, title));
    }

    private String week = "";
    private String month = "";
    private String all = "";
    private String title = "";


    @Override
    protected void onResume() {
        super.onResume();
        mSubRankTb.setTitle(title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub_rank;
    }

    @Override
    public void initData() {
        week = getIntent().getStringExtra(INTENT_WEEK);
        month = getIntent().getStringExtra(INTENT_MONTH);
        all = getIntent().getStringExtra(INTENT_ALL);
        title = getIntent().getStringExtra(INTENT_TITLE);
        //初始化Fragment
        mTabContents = new ArrayList<>();
        mTabContents.add(SubRankFragment.newInstance(week));
        mTabContents.add(SubRankFragment.newInstance(month));
        mTabContents.add(SubRankFragment.newInstance(all));

        mNames = Arrays.asList(getResources().getStringArray(R.array.sub_rank_tabs));

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mNames.get(position);
            }
        };
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mSubRankTb);
        mSubRankTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        mSubRanVp.setAdapter(mAdapter);
        mSubRankTl.setTabMode(TabLayout.MODE_FIXED);
        mSubRankTl.setupWithViewPager(mSubRanVp);
    }
}
