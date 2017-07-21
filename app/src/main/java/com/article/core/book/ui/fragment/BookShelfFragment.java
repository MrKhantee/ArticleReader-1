package com.article.core.book.ui.fragment;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.base.RealmHelper;
import com.article.core.book.adapter.BookShelfAdapter;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.CollectionBook;
import com.article.core.book.bean.Recommend;
import com.article.core.book.contract.BookShelfContract;
import com.article.core.book.manager.CollectionsManager;
import com.article.core.book.presenter.BookShelfPresenter;
import com.article.core.book.ui.BookActivity;
import com.article.core.book.ui.activity.BookDetailActivity;
import com.article.core.book.ui.activity.BookReadActivity;
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
public class BookShelfFragment extends BaseFragment implements
        BookShelfContract.View, BookShelfAdapter.OnItemLongClickListener {

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

        if (allCollectionBook.size() <= 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mBookShelfRv.setVisibility(View.INVISIBLE);
        } else {
            mCollectionBooks.clear();
            mCollectionBooks.addAll(allCollectionBook);
            mAdapter.notifyDataSetChanged();
            mBookShelfRv.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void configViews() {


        mBookShelfRv.setHasFixedSize(true);
        mBookShelfRv.setLayoutManager(new LinearLayoutManager(mContext));
        mBookShelfRv.setAdapter(mAdapter);

        mBookShelfSrl.setEnabled(false);

        mAdapter.setItemClickListener((view, position) -> {
//            String title = mCollectionBooks.get(position).getTitle();
//            SnackBarUtils.showSnackbar(view, title);
            CollectionBook collectionBook = mCollectionBooks.get(position);
            Recommend.RecommendBooks books = new Recommend.RecommendBooks();
            books.chaptersCount = collectionBook.getChaptersCount();
            books.lastChapter = collectionBook.getLastChapter();
            books.latelyFollower = collectionBook.getLatelyFollower();
            books.author = collectionBook.getAuthor();
            books.cover = collectionBook.getCover();
            books._id = collectionBook.get_id();
            books.title = collectionBook.getTitle();
            books.isSeleted = collectionBook.isFromSD;
            books.path = collectionBook.path;
            books.recentReadingTime = collectionBook.recentReadingTime;
            books.retentionRatio = collectionBook.getRetentionRatio();
            books.shortIntro = collectionBook.getShortIntro();
            books.updated = collectionBook.getUpdated();
            books.isTop = collectionBook.isTop;
            books.hasCp = collectionBook.hasCp;
            books.showCheckBox = collectionBook.showCheckBox;
            BookReadActivity.startActivity(mContext, books, true);
        });
        mFab.setOnClickListener(v -> ((BookActivity) mActivity).setCurrentItem(1));
        mAdapter.setItemLongClickListener(this);
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
    public boolean onItemLongClick(int position) {
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按监听窗口
     */
    private void showLongClickDialog(int position) {
        boolean isTop = CollectionsManager.getInstance().isTop(mCollectionBooks.get(position)._id);
        String[] items;

        DialogInterface.OnClickListener listener;
        if (mCollectionBooks.get(position).isFromSD) {
            //当小说的本地的时候
            items = getResources().getStringArray(R.array.book_shelf_item_long_click_choice_local);
            listener = (dialog, which) -> {
                switch (which) {
                    case 0://置顶
                        break;
                    case 1://删除
                        break;
                    case 2://批量管理
                        break;
                }
                dialog.dismiss();
            };
        } else {
            //小说不是本地书籍
            items = getResources().getStringArray(R.array.book_shelf_item_long_click_choice);
            listener = (dialog, which) -> {
                switch (which) {
                    case 0://置顶
                        break;
                    case 1://书籍详情
                        BookDetailActivity.startActivity(mContext, mCollectionBooks.get(position)._id);
                        break;
                    case 2://移入养肥区
                        break;
                    case 3://缓存全本
                        break;
                    case 4://删除
                        break;
                    case 5://批量管理
                        break;
                }
                dialog.dismiss();
            };
        }
        if (isTop) {
            items[0] = getResources().getString(R.string.cancel_top);
        }
        new AlertDialog.Builder(mContext)
                .setTitle(mCollectionBooks.get(position).title)
                .setItems(items, listener)
                .setNegativeButton(null, null)
                .create().show();
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
