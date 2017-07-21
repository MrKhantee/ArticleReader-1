package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.Constant;
import com.article.core.book.adapter.MinorAdapter;
import com.article.core.book.bean.CategoryListLv2;
import com.article.core.book.contract.SubCategoryActivityContract;
import com.article.core.book.manager.EventManager;
import com.article.core.book.presenter.SubCategoryActivityPresenter;
import com.article.core.book.ui.fragment.SubCategoryFragment;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/17.
 * Desc：
 */

public class SubCategoryActivity extends BaseActivity implements SubCategoryActivityContract.View {
    public static final String INTENT_CATE_NAME = "name";
    public static final String INTENT_GENDER = "gender";
    private String cate = "";
    private String gender = "";

    private String currentMinor = "";

    private ListPopupWindow mListPopupWindow;

    @BindView(R.id.sub_cate_tb)
    Toolbar mSubCateTb;
    @BindView(R.id.sub_cate_tl)
    TabLayout mSubCateTl;
    @BindView(R.id.sub_cate_vp)
    ViewPager mSubCateVp;

    private MenuItem mMenuItem;

    @Inject
    SubCategoryActivityPresenter mPresenter;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mPagerAdapter;
    private List<String> mTabNames;

    //小说的类型 新书、热门、口碑、完结
    private String[] type = {Constant.CateType.NEW, Constant.CateType.HOT,
            Constant.CateType.REPUTATION, Constant.CateType.OVER};

    private List<String> minors = new ArrayList<>();
    private MinorAdapter mMinorAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub_category;
    }


    public static void startActivity(Context context, String name, @Constant.Gender String gender) {
        context.startActivity(new Intent(context, SubCategoryActivity.class)
                .putExtra(INTENT_CATE_NAME, name)
                .putExtra(INTENT_GENDER, gender));
    }

    @Override
    public void initData() {
        cate = getIntent().getStringExtra(INTENT_CATE_NAME);
        gender = getIntent().getStringExtra(INTENT_GENDER);

        minors = new ArrayList<>();

        mTabContents = new ArrayList<>();
        mTabNames = Arrays.asList(getResources().getStringArray(R.array.sub_cate_list));

        mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.NEW));
        mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.HOT));
        mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.REPUTATION));
        mTabContents.add(SubCategoryFragment.newInstance(cate, "", gender, Constant.CateType.OVER));
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
                return mTabNames.get(position);
            }
        };

        mSubCateTl.setTabMode(TabLayout.MODE_FIXED);
        mSubCateVp.setAdapter(mPagerAdapter);
        mSubCateTl.setupWithViewPager(mSubCateVp);


    }

    @Override
    public void configViews() {

        mPresenter.attachView(this);
        mPresenter.getSubCategoryListLv2();

        mSubCateVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                EventManager.refreshSubCategory(currentMinor, type[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_category, menu);
        mMenuItem = menu.findItem(R.id.menu_sub_category_major);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sub_category_major) {
            showMenuPop();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showMenuPop() {
        if (minors.size() > 0 && mMinorAdapter != null) {
            if (mListPopupWindow == null) {
                mListPopupWindow = new ListPopupWindow(this);
                mListPopupWindow.setAdapter(mMinorAdapter);
                mListPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                //必须要设置这个属性，否则会出现异常
                mListPopupWindow.setAnchorView(mSubCateTb);
                mListPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
                    mMinorAdapter.setItemCheck(position);
                    if (position > 0) {
                        currentMinor = minors.get(position);
                    } else {
                        currentMinor = "";
                    }
                    mSubCateTb.setTitle(currentMinor);
                    int currentItem = mSubCateVp.getCurrentItem();
                    EventManager.refreshSubCategory(currentMinor, type[currentItem]);
                    mListPopupWindow.dismiss();
                    mSubCateTb.setTitle(minors.get(position));
                });
            }
            mListPopupWindow.show();
        }
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
        setSupportActionBar(mSubCateTb);
        mSubCateTb.setNavigationOnClickListener(v -> finish());
        getSupportActionBar().setTitle(cate);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mSubCateTb.setTitle(cate);
//    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showSubCategory(CategoryListLv2 categoryListLv2) {
        minors.clear();
        minors.add(cate);
        if (Constant.Gender.MALE.equals(gender)) {
            for (CategoryListLv2.MaleBean male : categoryListLv2.male) {
                if (male.major.equals(cate)) {
                    minors.addAll(male.mins);
                    break;
                }
            }
        } else if (Constant.Gender.FEMALE.equals(gender)) {
            for (CategoryListLv2.MaleBean female : categoryListLv2.female) {
                if (female.major.equals(cate)) {
                    minors.addAll(female.mins);
                    break;
                }
            }
        }
        mMinorAdapter = new MinorAdapter(this, minors);
        mMinorAdapter.setItemCheck(0);
        currentMinor = "";
        EventManager.refreshSubCategory(currentMinor, Constant.CateType.NEW);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
