package com.article.core;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.article.CoreApplication;
import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.utils.SnackBarUtils;
import com.article.core.book.ui.BookActivity;
import com.article.core.code.CodeMainActivity;
import com.article.core.fun.FunMainActivity;
import com.article.core.welfare.WelFareActivity;
import com.article.di.component.AppComponent;
import com.article.widget.SupportDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;
    @BindView(R.id.tb_main)
    Toolbar mTbMain;
    @BindView(R.id.rv_main)
    RecyclerView mRvMain;

    private List<MainBean> mMainBeanList;
    private MainAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mMainBeanList = new ArrayList<>();
        mMainBeanList.add(new MainBean("小说阅读器", R.drawable.home_book_reader));
        mMainBeanList.add(new MainBean("代码阅读器", R.drawable.home_code_reader));
        mMainBeanList.add(new MainBean("RSS阅读器", R.drawable.home_rss_reader));
        mMainBeanList.add(new MainBean("音乐", R.drawable.home_music));
        mMainBeanList.add(new MainBean("天气", R.drawable.home_weather));
        mMainBeanList.add(new MainBean("福利", R.drawable.home_welfare));
        mMainBeanList.add(new MainBean("搞笑段子", R.drawable.home_funny));
        mAdapter = new MainAdapter(this, mMainBeanList);

        mRvMain.setHasFixedSize(true);
        mRvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRvMain.addItemDecoration(new SupportDividerItemDecoration(this, LinearLayoutCompat.VERTICAL));
        mRvMain.addItemDecoration(new SupportDividerItemDecoration(this, LinearLayoutManager.VERTICAL, true));

        mRvMain.setAdapter(mAdapter);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mTbMain);
    }

    @Override
    public void configViews() {
        mAdapter.setItemClickListener((view, position) -> {
            switch (position) {
                case 0:
                    BookActivity.startActivity(MainActivity.this);
                    break;
                case 1:
                    CodeMainActivity.startActivity(MainActivity.this);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    WelFareActivity.startActivity(MainActivity.this);
                    break;
                case 6:
                    FunMainActivity.startActivity(MainActivity.this);
                    break;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                SnackBarUtils.showLongSnackbar(mRvMain, "再按一次退出应用",
                        Color.WHITE, getResources().getColor(R.color.colorPrimary));
                return true;
            } else {
                CoreApplication.getInstance().exitApp(); // 退出
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
