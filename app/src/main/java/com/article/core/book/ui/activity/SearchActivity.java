package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.core.book.bean.SearchDetail;
import com.article.core.book.contract.SearchContract;
import com.article.core.book.presenter.SearchPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.TagColor;
import com.article.widget.TagGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/7/28.
 * Desc：
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {
    public static final String INTENT_SEARCH = "search";
    @BindView(R.id.searchLayout)
    LinearLayout mSearchLayout;
    @BindView(R.id.tbSearch)
    Toolbar mTbSearch;
    @BindView(R.id.rlHotWords)
    RelativeLayout mRlHotWords;
    @BindView(R.id.tvChangeWords)
    TextView mTvChangeWords;
    @BindView(R.id.tgSearch)
    TagGroup mTgSearch;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    private String key;

    private List<String> tagList = new ArrayList<>();
    private int times = 0;
    int hotIndex = 0;

    @Inject
    SearchPresenter mPresenter;

    public static void startActivity(Context context, String search) {
        context.startActivity(new Intent(context, SearchActivity.class).putExtra(INTENT_SEARCH, search));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        key = getIntent().getStringExtra(INTENT_SEARCH);
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
        mTbSearch.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        mPresenter.attachView(this);
        mPresenter.getHotWordList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        searchMenuItem = menu.findItem(R.id.action_book_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        search(key);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
//                        initTagGroup();
                        return true;
                    }
                });
        return true;
    }

    /**
     * 进去页面就打开搜索
     *
     * @param key
     */
    private void search(String key) {
        MenuItemCompat.expandActionView(searchMenuItem);
        if (!TextUtils.isEmpty(key)) {
            searchView.setQuery(key, true);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showHotWordList(List<String> list) {
        visible(mTvChangeWords);
        tagList.clear();
        tagList.addAll(list);
        times = 0;
        showHotWords();
    }

    /**
     * 每次显示8个热搜词
     */
    private synchronized void showHotWords() {
        int tagSize = 10;
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < tagList.size(); hotIndex++, j++) {
            tags[j] = tagList.get(hotIndex % tagList.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        mTgSearch.setTags(colors, tags);
    }

    @Override
    public void showAutoCompleteList(List<String> list) {

    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {

    }

    /**
     * 初始化tgGroup
     */
    private void initTgGoup() {
        visible(mTgSearch, mRlHotWords);
    }
}
