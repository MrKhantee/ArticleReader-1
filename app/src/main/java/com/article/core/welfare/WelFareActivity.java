package com.article.core.welfare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.ui.BookActivity;
import com.article.core.code.CodeMainActivity;
import com.article.core.fun.FunMainActivity;
import com.article.di.component.AppComponent;

import java.lang.reflect.Method;

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

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, WelFareActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mWelfareTb);
        //实现顶部状态栏透明
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mWelfareDl, mWelfareTb,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mWelfareDl.addDrawerListener(toggle);
        toggle.syncState();

        mWelfareNav.setItemIconTintList(null);
        mWelfareNav.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    /**
     * 显示item中的图片；
     *
     * @param view
     * @param menu
     * @return

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
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initToolBar() {
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
                break;
            case R.id.nav_welfare_meizi:
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
}
