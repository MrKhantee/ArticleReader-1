package com.article.core.book.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.base.RealmHelper;
import com.article.common.utils.SnackBarUtils;
import com.article.core.book.adapter.BookShelfAdapter;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.CollectionBook;
import com.article.core.book.contract.BookShelfContract;
import com.article.core.book.presenter.BookShelfPresenter;
import com.article.core.book.ui.BookActivity;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */
public class BookShelfFragment extends BaseFragment implements BookShelfContract.View {

    @BindView(R.id.book_shelf_rv)
    RecyclerView mBookShelfRv;
    @BindView(R.id.emptyView)
    LinearLayout mEmptyView;
    @BindView(R.id.book_shelf_srl)
    SwipeRefreshLayout mBookShelfSrl;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private List<CollectionBook> mCollectionBooks;
    private BookShelfAdapter mAdapter;

    private RealmHelper mRealmHelper;

    @Inject
    BookShelfPresenter mPresenter;

    public static BookShelfFragment newInstance() {
        BookShelfFragment fragment = new BookShelfFragment();
        return fragment;
    }


    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }


    @Override
    public void initData() {
        mRealmHelper = new RealmHelper(mContext);
        mCollectionBooks = new ArrayList<>();
        mAdapter = new BookShelfAdapter(mContext, mCollectionBooks);


    }

    @Override
    public void onResume() {
        super.onResume();
        List<CollectionBook> allCollectionBook = mRealmHelper.getAllCollectionBook();
        mCollectionBooks.clear();
        mCollectionBooks.addAll(allCollectionBook);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void configViews() {
//        mBookShelfSrl.measure(0, 0);
//        mBookShelfSrl.setRefreshing(true);
//        mBookShelfSrl.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
//                R.color.colorAccent, R.color.colorPrimary);

        mBookShelfRv.setHasFixedSize(true);
        mBookShelfRv.setLayoutManager(new LinearLayoutManager(mContext));
        mBookShelfRv.setAdapter(mAdapter);

        mBookShelfSrl.setEnabled(false);

        mAdapter.setItemClickListener((view, position) -> {
            String title = mCollectionBooks.get(position).getTitle();
//            SnackBarUtils.showLongSnackbar(view, title, Color.WHITE, R.color.colorPrimary);
            SnackBarUtils.showSnackbar(view,title);
        });
        mFab.setOnClickListener(v -> ((BookActivity) mActivity).setCurrentItem(1));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void showError() {
        mBookShelfSrl.setRefreshing(false);
    }

    @Override
    public void complete() {
        mBookShelfSrl.setRefreshing(false);
    }

    @Override
    public void showCollectionBook(List<CollectionBook> collectionBooks) {
        mCollectionBooks.clear();
        mCollectionBooks.addAll(collectionBooks);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChapter(String bookId, List<BookMixAToc.mixToc.Chapters> mChapters) {

    }
}
