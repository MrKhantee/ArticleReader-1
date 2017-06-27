package com.article.core.code;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.ui.BookActivity;
import com.article.core.fun.FunMainActivity;
import com.article.di.component.AppComponent;

import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/27.
 * Desc：
 */

public class CodeMainActivity extends BaseActivity {
    @BindView(R.id.code_dl)
    DrawerLayout mCodeDl;
    @BindView(R.id.code_tb)
    Toolbar mCodeTb;
    @BindView(R.id.code_rv)
    RecyclerView mCodeRv;
    @BindView(android.R.id.empty)
    TextView mEmpty;
    @BindView(R.id.fab_main)
    FloatingActionButton mFabMain;
    @BindView(R.id.nv_code)
    NavigationView mNvCode;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CodeMainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_code;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mCodeTb);

        //实现顶部状态栏透明
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mCodeDl, mCodeTb,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mCodeDl.addDrawerListener(toggle);
        toggle.syncState();

        mNvCode.setItemIconTintList(null);
        mNvCode.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.nav_code_home:
                    finish();
                    break;
                case R.id.nav_code_book:
                    BookActivity.startActivity(CodeMainActivity.this);
                    finish();
                    break;
                case R.id.nav_code_rss:
                    break;
                case R.id.nav_code_music:
                    break;
                case R.id.nav_code_weather:
                    break;
                case R.id.nav_code_welfare:
                    break;
                case R.id.nav_code_funny:
                    FunMainActivity.startActivity(CodeMainActivity.this);
                    finish();
                    break;
            }
            mCodeDl.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeTb.setTitle("代码阅读");
    }

    @Override
    public void configViews() {

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

}
