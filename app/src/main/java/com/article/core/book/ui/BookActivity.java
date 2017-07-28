package com.article.core.book.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.contract.BookActivityContract;
import com.article.core.book.manager.EventManager;
import com.article.core.book.presenter.BookActivityPresenter;
import com.article.core.book.ui.activity.BookListActivity;
import com.article.core.book.ui.activity.SearchActivity;
import com.article.core.book.ui.activity.TopCategoryActivity;
import com.article.core.book.ui.activity.TopRankActivity;
import com.article.core.book.ui.fragment.BookShelfFragment;
import com.article.core.book.ui.fragment.FindFragment;
import com.article.core.code.CodeMainActivity;
import com.article.core.fun.FunMainActivity;
import com.article.core.welfare.WelFareActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BookActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BookActivityContract.View {

    @BindView(R.id.tb_book)
    Toolbar mTbBook;
    @BindView(R.id.tl_book)
    TabLayout mTlBook;
    @BindView(R.id.dl_book)
    DrawerLayout mDlBook;
    @BindView(R.id.nv_book)
    NavigationView mNvBook;
    @BindView(R.id.vp_book)
    ViewPager mVpBook;
//    @BindView(R.id.fab)
//    FloatingActionButton mFab;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mList;

    @Inject
    BookActivityPresenter mPresenter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, BookActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book;
    }

    @Override
    public void initData() {
        setSupportActionBar(mTbBook);
//        EventBus.getDefault().register(this);
        //实现顶部状态栏透明
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDlBook, mTbBook, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDlBook.addDrawerListener(toggle);
        toggle.syncState();


        mNvBook.setNavigationItemSelectedListener(this);
        //显示图标的默认颜色
        mNvBook.setItemIconTintList(null);

        //初始化数据
        mList = Arrays.asList(getResources().getStringArray(R.array.book_tabs));
        mTabContents = new ArrayList<>();
        mTabContents.add(BookShelfFragment.newInstance());
        mTabContents.add(FindFragment.newInstance());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return mList.get(position);
            }


        };
        mVpBook.setAdapter(mAdapter);
        mTlBook.setTabMode(TabLayout.MODE_FIXED);
        mTlBook.setupWithViewPager(mVpBook);
    }

    public void setCurrentItem(int position) {
        mVpBook.setCurrentItem(position);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTbBook.setTitle("小说阅读器");
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
//        mPresenter.syncBookShelf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (mDlBook.isDrawerOpen(GravityCompat.START)) {
            mDlBook.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    /**
     * 显示item中的图片；
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.book_action_login:
                break;
            case R.id.book_action_sync:
                break;
            case R.id.book_action_search:
                SearchActivity.startActivity(BookActivity.this,SearchActivity.INTENT_SEARCH);
                break;
            case R.id.book_action_scan_local:
                break;
            case R.id.book_action_wifi_book:
                break;
            case R.id.book_action_night:
                break;
            case R.id.book_action_feedback:
                break;
            case R.id.book_action_setting:
                break;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_book_home:
                finish();//返回主界面菜单
                break;
            case R.id.nav_book_code:
                CodeMainActivity.startActivity(BookActivity.this);
                finish();
                break;
            case R.id.nav_book_rss:
                break;
            case R.id.nav_book_music:
                break;
            case R.id.nav_book_weather:
                break;
            case R.id.nav_book_welfare:
                WelFareActivity.startActivity(BookActivity.this);
                finish();
                break;
            case R.id.nav_book_funny:
                FunMainActivity.startActivity(BookActivity.this);
                finish();
                break;
            case R.id.nav_book_rank:
                TopRankActivity.startActivity(this);
                break;
            case R.id.nav_book_cate:
                TopCategoryActivity.startActivity(this);
                break;
            case R.id.nav_book_list:
                BookListActivity.startActivity(this);
                break;
        }
        mDlBook.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
    }

    @Override
    public void syncBookShelfCompleted() {
//        dismissDialog();
        EventManager.refreshCollectionList();
    }
}
