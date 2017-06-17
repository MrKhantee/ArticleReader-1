package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.core.book.adapter.SubRankFragmentAdapter;
import com.article.core.book.bean.BooksByCats;
import com.article.core.book.contract.SubOtherRankActivityContract;
import com.article.core.book.presenter.SubOtherRankActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.article.core.book.ui.activity.SubRankActivity.INTENT_TITLE;
import static com.article.core.book.ui.fragment.SubRankFragment.BUNDLE_ID;

public class SubOtherRankActivity extends BaseMVPActivity<SubOtherRankActivityPresenter>
        implements SubOtherRankActivityContract.View {


    @BindView(R.id.sub_other_rank_rv)
    RecyclerView mSubOtherRankRv;
    @BindView(R.id.sub_other_rank_tb)
    Toolbar mSubOtherRankTb;

    private String title = "";
    private String id = "";

    @Inject
    SubOtherRankActivityPresenter mPresenter;

    private SubRankFragmentAdapter mAdapter;

    private List<BooksByCats.BooksBean> mBeanList;

    /**
     * 跳转到当前activity
     *
     * @param context
     * @param id
     * @param title
     */
    public static void startActivity(Context context, String id, String title) {
        context.startActivity(new Intent(context, SubOtherRankActivity.class)
                .putExtra(INTENT_TITLE, title).putExtra(BUNDLE_ID, id));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sub_other_rank;
    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra(INTENT_TITLE);
        id = getIntent().getStringExtra(BUNDLE_ID);

        mBeanList = new ArrayList<>();
        mAdapter = new SubRankFragmentAdapter(this, mBeanList);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mSubOtherRankTb);

        mSubOtherRankTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSubOtherRankTb.setTitle(title);
    }

    @Override
    public void configViews() {
        showDialog();
        mPresenter.attachView(this);
        mPresenter.getRankList(id);

        mSubOtherRankRv.setHasFixedSize(true);
        mSubOtherRankRv.setLayoutManager(new LinearLayoutManager(this));
        mSubOtherRankRv.setAdapter(mAdapter);

        mAdapter.setClickListener((view, position) ->
                BookDetailActivity.startActivity(SubOtherRankActivity.this, mAdapter.getData(position)._id));
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showRankList(BooksByCats data) {
        mBeanList.clear();
        mAdapter.addAll(data.books);
    }
}
