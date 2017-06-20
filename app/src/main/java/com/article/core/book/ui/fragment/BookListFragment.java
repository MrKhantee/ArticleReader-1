package com.article.core.book.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.article.R;
import com.article.base.BaseMVPFragment;
import com.article.core.book.adapter.BookListFragmentAdapter;
import com.article.core.book.bean.BookLists;
import com.article.core.book.bean.support.TagEvent;
import com.article.core.book.contract.BookListFragmentContract;
import com.article.core.book.manager.SettingManager;
import com.article.core.book.presenter.BookListFragmentPresenter;
import com.article.core.book.ui.activity.BookListDetailActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/15.
 * Desc：
 */

public class BookListFragment extends BaseMVPFragment<BookListFragmentPresenter>
        implements BookListFragmentContract.View {
    public final static String BUNDLE_TAG = "tag";
    public final static String BUNDLE_TAB = "tab";

    //用于分别各种收藏榜
    public String duration = "";
    public String sort = "";

    protected int start = 0;
    protected int limit = 20;

    //当前显示的Tab
    public int currentTab;
    public String currentTag;

    @BindView(R.id.book_list_fragment_rv)
    RecyclerView mBookListFragmentRv;
    @BindView(R.id.book_list_fragment_srl)
    SwipeRefreshLayout mBookListSrl;

    @Inject
    BookListFragmentPresenter mPresenter;

    private BookListFragmentAdapter mAdapter;
    private List<BookLists.BookListsBean> mListsBeen;

    public static BookListFragment newInstance(String tag, int tab) {
        BookListFragment fragment = new BookListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TAG, tag);
        bundle.putInt(BUNDLE_TAB, tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        currentTab = getArguments().getInt(BUNDLE_TAB);
        mListsBeen = new ArrayList<>();
        mAdapter = new BookListFragmentAdapter(getContext(), mListsBeen);
        switch (currentTab) {
            case 0:
                //本周最热
                duration = "last-seven-days";
                sort = "collectorCount";
                break;
            case 1:
                //最新发布
                duration = "all";
                sort = "created";
                break;
            case 2:
                //最多收藏
                duration = "all";
                sort = "collectorCount";
                break;
        }
    }

    boolean isLoading;

    @Override
    public void configViews() {

        mPresenter.attachView(this);

        mBookListSrl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mBookListSrl.measure(0, 0);
        mBookListSrl.setRefreshing(true);

        mBookListFragmentRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mBookListFragmentRv.setLayoutManager(layoutManager);
        mBookListFragmentRv.setAdapter(mAdapter);

        mBookListSrl.setOnRefreshListener(() -> onRefresh());

        mBookListFragmentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int itemCount = mAdapter.getItemCount() - 1;
                if (lastVisibleItemPosition == itemCount) {
                    boolean isRefreshing = mBookListSrl.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        mBookListFragmentRv.post(() -> {
                            onLoadMore();
                            isLoading = false;
                        });
                    }
                }
            }
        });

        onRefresh();

        mAdapter.setOnRvItemClickListener((view, position) -> {
            String id = mListsBeen.get(position)._id;
            BookListDetailActivity.startActivity(mContext, id);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_list;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(TagEvent event) {
        currentTag = event.tag;
        if (getUserVisibleHint()) {
            mPresenter.getBookLists(duration, sort, 0, limit, currentTag,
                    SettingManager.getInstance().getUserChooseSex());
        }
    }

    @Override
    public void showError() {
        dismissDialog();
    }

    @Override
    public void complete() {
        mBookListSrl.setRefreshing(false);
    }


    private void onRefresh() {
        mPresenter.getBookLists(duration, sort, 0, limit, currentTag,
                SettingManager.getInstance().getUserChooseSex());
    }

    private void onLoadMore() {
        mPresenter.getBookLists(duration, sort, start, limit, currentTag,
                SettingManager.getInstance().getUserChooseSex());
    }

    @Override
    public void showBookList(List<BookLists.BookListsBean> bookLists, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(bookLists);
        start = start + bookLists.size();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
