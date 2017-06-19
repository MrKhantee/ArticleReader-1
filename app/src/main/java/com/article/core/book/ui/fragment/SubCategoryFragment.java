package com.article.core.book.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.common.Constant;
import com.article.core.book.adapter.SubCategoryFragmentAdapter;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.contract.SubCategoryFragmentContract;
import com.article.core.book.presenter.SubCategoryFragmentPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public class SubCategoryFragment extends BaseFragment implements SubCategoryFragmentContract.View {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";
    private int limit = 20;
    protected int start = 0;

    @BindView(R.id.sub_cate_srl)
    SwipeRefreshLayout mSubCateSrl;
    @BindView(R.id.sub_cate_rv)
    RecyclerView mSubCateRv;

    @Inject
    SubCategoryFragmentPresenter mPresenter;
    private List<BooksByCats.BooksBean> mBeanList;
    private SubCategoryFragmentAdapter mAdapter;

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_category;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        major = arguments.getString(BUNDLE_MAJOR);
        minor = arguments.getString(BUNDLE_MINOR);
        gender = arguments.getString(BUNDLE_GENDER);
        type = arguments.getString(BUNDLE_TYPE);


    }
    boolean isLoading;
    @Override
    public void configViews() {
        mSubCateSrl.measure(0, 0);
        mSubCateSrl.setRefreshing(true);
        mSubCateSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
                R.color.colorAccent, R.color.colorPrimary);
        mSubCateSrl.setOnRefreshListener(this::onRefresh);


        mBeanList = new ArrayList<>();
        mAdapter = new SubCategoryFragmentAdapter(mContext, mBeanList);

        mSubCateRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mSubCateRv.setLayoutManager(manager);
        mSubCateRv.setAdapter(mAdapter);


        onRefresh();

        mSubCateRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int itemCount = mAdapter.getItemCount();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == (itemCount - 1)){
                    boolean isRefreshing = mSubCateSrl.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mSubCateRv.post(() -> {
                            onLoadMore();
                            isLoading = false;
                        });
                    }
                }
            }
        });
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showError() {
        mSubCateSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mSubCateSrl.setRefreshing(false);
    }

    @Override
    public void showCategoryList(BooksByCats booksByCats, boolean isRefresh) {
        if (isRefresh) {
            start = 0;
            mAdapter.clear();
        }
        mAdapter.addAll(booksByCats.books);
        start += booksByCats.books.size();
    }

    /**
     * 请求数据，第一次请求或者刷新
     */
    private void onRefresh() {
        mPresenter.getCategoryList(gender, this.type, major, minor, 0, limit);
        mAdapter.setRefresh(true);
    }

    /**
     * 记载更多数据
     */
    private void onLoadMore() {
        mAdapter.setRefresh(false);
        mPresenter.getCategoryList(gender, this.type, major, minor, start, limit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
