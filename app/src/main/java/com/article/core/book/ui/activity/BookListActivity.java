package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.utils.SnackBarUtils;
import com.article.core.book.adapter.BookListTagAdapter;
import com.article.core.book.bean.BookListTags;
import com.article.core.book.bean.support.TagEvent;
import com.article.core.book.contract.BookListActivityContract;
import com.article.core.book.presenter.BookListActivityPresenter;
import com.article.core.book.ui.fragment.BookListFragment;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.ReboundScrollView;
import com.article.widget.SupportDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/15.
 * Desc：
 */

public class BookListActivity extends BaseActivity implements BookListActivityContract.View {

    @BindView(R.id.book_list_tb)
    Toolbar mBookListTb;
    @BindView(R.id.book_list_tl)
    TabLayout mBookListTl;
    @BindView(R.id.book_list_vp)
    ViewPager mBookListVp;
    @BindView(R.id.book_list_tags_rsv)
    ReboundScrollView mBookListTagsRsv;
    @BindView(R.id.book_list_tags_rv)
    RecyclerView mBookListTagsRv;


    @Inject
    BookListActivityPresenter mPresenter;

    private List<BookListTags.DataBean> mDataBeanList = new ArrayList<>();
    private List<String> names;
    private List<Fragment> mTabContents;

    private FragmentPagerAdapter mPagerAdapter;

    private BookListTagAdapter mTagAdapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, BookListActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_list;
    }

    @Override
    public void initData() {
        names = Arrays.asList(getResources().getStringArray(R.array.subject_tabs));
        mTabContents = new ArrayList<>();
        mTabContents.add(BookListFragment.newInstance("", 0));
        mTabContents.add(BookListFragment.newInstance("", 1));
        mTabContents.add(BookListFragment.newInstance("", 2));
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return names.get(position);
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_tags) {
            if (isVisible(mBookListTagsRsv)) {
                hideTagGroup();
            } else {
                showTagGroup();
            }
            return true;
        } else if (item.getItemId() == R.id.menu_my_book_list) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTagGroup() {
        if (mDataBeanList.isEmpty()) {
            SnackBarUtils.showLongSnackbar(mBookListTagsRsv, "加载失败，请检查网络或稍后再试",
                    Color.WHITE, R.color.colorPrimary);
            return;
        }
        Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(400);
        mBookListTagsRsv.startAnimation(mShowAction);
        mBookListTagsRsv.setVisibility(View.VISIBLE);
    }

    private void hideTagGroup() {
        Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(400);
        mBookListTagsRsv.startAnimation(mHiddenAction);
        mBookListTagsRsv.setVisibility(View.GONE);
    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mBookListTb);
        mBookListTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {

        mBookListVp.setAdapter(mPagerAdapter);
        mBookListTl.setTabMode(TabLayout.MODE_FIXED);
        mBookListTl.setupWithViewPager(mBookListVp);

        mTagAdapter = new BookListTagAdapter(this, mDataBeanList);
        mBookListTagsRv.setHasFixedSize(true);
        mBookListTagsRv.setLayoutManager(new LinearLayoutManager(this));
        mBookListTagsRv.addItemDecoration(new SupportDividerItemDecoration(this,
                SupportDividerItemDecoration.VERTICAL_LIST));
        mBookListTagsRv.setAdapter(mTagAdapter);

        mTagAdapter.setListener((view, position, data) -> {
            hideTagGroup();
            mBookListTb.setTitle(data);
            EventBus.getDefault().post(new TagEvent(data));
        });

        mPresenter.attachView(this);
        mPresenter.getBookListTags();
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {
    }

    @Override
    public void showBookListTags(BookListTags bookListTags) {
        mDataBeanList.clear();
        mDataBeanList.addAll(bookListTags.data);
        mTagAdapter.notifyDataSetChanged();
    }
}
