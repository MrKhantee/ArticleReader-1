package com.article.core.welfare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.ui.BookActivity;
import com.article.core.code.CodeMainActivity;
import com.article.core.fun.FunMainActivity;
import com.article.core.welfare.ui.fragment.GankMeiZiFragment;
import com.article.core.welfare.ui.fragment.MeiZiFragment;
import com.article.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Amos on 2017/7/14.
 * Desc：
 */

public class WelFareActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.welfare_nav)
    NavigationView mWelfareNav;
    @BindView(R.id.welfare_dl)
    DrawerLayout mWelfareDl;
    @BindView(R.id.welfare_tb)
    Toolbar mWelfareTb;
    @BindView(R.id.welfare_fl)
    FrameLayout mWelfareFl;

    private List<Fragment> mFragments;
    private int currentTabIndex;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WelFareActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    public void initData() {
        setSupportActionBar(mWelfareTb);

        mFragments = new ArrayList<>();

        //实现顶部状态栏透明
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mWelfareDl, mWelfareTb,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mWelfareDl.addDrawerListener(toggle);
        toggle.syncState();

        mWelfareNav.setItemIconTintList(null);
        mWelfareNav.setNavigationItemSelectedListener(this);

        initFragment();
        changIndex(0, "干货妹子");
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        GankMeiZiFragment gankMeiZiFragment = GankMeiZiFragment.newInstance();
        MeiZiFragment meiZiFragment = MeiZiFragment.newInstance();
        mFragments.add(gankMeiZiFragment);
        mFragments.add(meiZiFragment);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initToolBar() {
        mWelfareTb.setTitle("干货妹纸");
    }

    @Override
    public void configViews() {

    }

    /**
     * 侧滑菜单选项选中监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.nav_welfare_home:
                finish();
                break;
            case R.id.nav_welfare_gank_io:
                changIndex(0, "干货妹子");
                break;
            case R.id.nav_welfare_meizi:
                changIndex(1, "妹子图");
                break;
            case R.id.nav_welfare_tao:
                break;
            case R.id.nav_welfare_douban:
                break;
            case R.id.nav_welfare_huaban:
                break;
            case R.id.nav_welfare_jiandan:
                break;
            case R.id.nav_welfare_book:
                BookActivity.startActivity(WelFareActivity.this);
                finish();
                break;
            case R.id.nav_welfare_code:
                CodeMainActivity.startActivity(WelFareActivity.this);
                finish();
                break;
            case R.id.nav_welfare_rss:
                break;
            case R.id.nav_welfare_music:
                break;
            case R.id.nav_welfare_weather:
                break;
            case R.id.nav_welfare_funny:
                FunMainActivity.startActivity(WelFareActivity.this);
                finish();
                break;
        }
        mWelfareDl.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changIndex(int position, String title) {
        mWelfareTb.setTitle(title);
        switchFragment(mFragments.get(position));
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideAllFragment(transaction);
        if (!fragment.isAdded()) {
            transaction.add(R.id.welfare_fl, fragment);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        for (Fragment fragment : mFragments) {
            transaction.hide(fragment);
        }
    }
}
