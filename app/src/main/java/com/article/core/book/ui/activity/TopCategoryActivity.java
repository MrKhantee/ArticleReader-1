package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.article.R;
import com.article.base.BaseActivity;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.adapter.TopCategoryActivityAdapter;
import com.article.core.book.bean.CategoryList;
import com.article.core.book.contract.TopCategoryActivityContract;
import com.article.core.book.presenter.TopCategoryActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;
import com.article.widget.SupportGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.article.R.id.top_cate_male_rv;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class TopCategoryActivity extends BaseActivity implements TopCategoryActivityContract.View {

    @BindView(R.id.top_cate_tb)
    Toolbar mTopCateTb;
    @BindView(R.id.top_cate_srl)
    SwipeRefreshLayout mTopCateSrl;
    @BindView(top_cate_male_rv)
    RecyclerView mTopCateMaleRv;
    @BindView(R.id.top_cate_female_rv)
    RecyclerView mTopCateFemaleRv;

    @Inject
    TopCategoryActivityPresenter mPresenter;

    private TopCategoryActivityAdapter mMaleAdapter;
    private TopCategoryActivityAdapter mFemaleAdapter;
    private List<CategoryList.MaleBean> mMaleList;
    private List<CategoryList.MaleBean> mFemaleList;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TopCategoryActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_top_category;
    }

    @Override
    public void initData() {
        mMaleList = new ArrayList<>();
        mFemaleList = new ArrayList<>();

        mMaleAdapter = new TopCategoryActivityAdapter(this, mMaleList);
        mFemaleAdapter = new TopCategoryActivityAdapter(this, mFemaleList);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mTopCateTb);
        mTopCateTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        mTopCateSrl.measure(0, 0);
        mTopCateSrl.setRefreshing(true);

        mPresenter.attachView(this);
        mPresenter.getCategoryList();
    }

    @Override
    public void showError() {
        mTopCateSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mTopCateSrl.setRefreshing(false);
        mTopCateSrl.setEnabled(false);

        mTopCateMaleRv.setHasFixedSize(true);
        mTopCateMaleRv.setLayoutManager(new GridLayoutManager(this, 3));
        mTopCateMaleRv.addItemDecoration(new SupportGridItemDecoration(this));
        mTopCateMaleRv.setAdapter(mMaleAdapter);

        mTopCateFemaleRv.setHasFixedSize(true);
        mTopCateFemaleRv.setLayoutManager(new GridLayoutManager(this, 3));
        mTopCateFemaleRv.addItemDecoration(new SupportGridItemDecoration(this));
        mTopCateFemaleRv.setAdapter(mFemaleAdapter);

        mMaleAdapter.setClickListener(new OnItemClickListener(Constant.Gender.MALE));
        mFemaleAdapter.setClickListener(new OnItemClickListener(Constant.Gender.FEMALE));
    }

    @Override
    public void showCategoryList(CategoryList categoryList) {
        mMaleList.clear();
        mMaleList.addAll(categoryList.male);
        mMaleAdapter.notifyDataSetChanged();

        mFemaleList.clear();
        mFemaleList.addAll(categoryList.female);
        mFemaleAdapter.notifyDataSetChanged();
    }

    class OnItemClickListener implements OnRvItemClickListener {
        private String gender = "";

        public OnItemClickListener(@Constant.Gender String gender) {
            this.gender = gender;
        }

        @Override
        public void onItemClick(View view, int position) {
            String name;
            if (Constant.Gender.MALE.equals(gender)) {
                name = mMaleList.get(position).name;
                SubCategoryActivity.startActivity(TopCategoryActivity.this, name, Constant.Gender.MALE);
            } else {
                name = mFemaleList.get(position).name;
                SubCategoryActivity.startActivity(TopCategoryActivity.this, name, Constant.Gender.FEMALE);
            }
        }
    }
}
