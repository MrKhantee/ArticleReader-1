package com.article.core.book.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.article.R;
import com.article.base.BaseMVPActivity;
import com.article.core.book.adapter.BookListDetailAdapter;
import com.article.core.book.bean.BookListDetail;
import com.article.core.book.bean.BookListDetailMsg;
import com.article.core.book.contract.BookListDetailActivityContract;
import com.article.core.book.presenter.BookListDetailActivityPresenter;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerBookComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class BookListDetailActivity extends BaseMVPActivity<BookListDetailActivityPresenter>
        implements BookListDetailActivityContract.View {

    @BindView(R.id.book_list_detail_tb)
    Toolbar mBookListDetailTb;
    @BindView(R.id.book_list_detail_rv)
    RecyclerView mBookListDetailRv;

    @Inject
    BookListDetailActivityPresenter mPresenter;
    public static String INTENT_BOOK_LIST_ID = "book_list_id";

    //开始位置
    private int start = 0;
    //每一次加载条目数
    private int limit = 20;
    private String bookListId;

    private BookListDetailAdapter mDetailAdapter;
    private List<BookListDetail.BookListBean.BooksBean> mBookBeen = new ArrayList<>();
    private BookListDetailMsg mDetailMsg = new BookListDetailMsg();

    public static void startActivity(Context context, String bookListId) {
        context.startActivity(new Intent(context, BookListDetailActivity.class)
                .putExtra(INTENT_BOOK_LIST_ID, bookListId));
    }

    @Override
    public void showError() {
        dismissDialog();
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showBookListDetail(BookListDetail bookListDetail) {
        List<BookListDetail.BookListBean.BooksBean> books = bookListDetail.getBookList().getBooks();
        mBookBeen.clear();
        mDetailAdapter.clear();
        mBookBeen.addAll(books);

        loadNextPage();

        mDetailMsg.avatar = bookListDetail.getBookList().getAuthor().getAvatar();
        mDetailMsg.author = bookListDetail.getBookList().getAuthor().getNickname();
        mDetailMsg.desc = bookListDetail.getBookList().getDesc();
        mDetailMsg.title = bookListDetail.getBookList().getTitle();
        mDetailAdapter.setDetailMsg(mDetailMsg);
    }

    private void loadNextPage() {
        if (start < mBookBeen.size()) {
            if (mBookBeen.size() - start > limit) {
                mDetailAdapter.addAll(mBookBeen.subList(start, start + limit));
            } else {
                mDetailAdapter.addAll(mBookBeen.subList(start, mBookBeen.size()));
            }
            start += limit;
        } else {
            mDetailAdapter.addAll(new ArrayList<>());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_list_detail;
    }

    @Override
    public void initData() {
        bookListId = getIntent().getStringExtra(INTENT_BOOK_LIST_ID);
        mDetailAdapter = new BookListDetailAdapter(this, mBookBeen, mDetailMsg);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mBookListDetailTb);
        mBookListDetailTb.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void configViews() {
        showDialog();
        mPresenter.attachView(this);
        mPresenter.getBookListDetail(bookListId);

        mBookListDetailRv.setHasFixedSize(true);
        mBookListDetailRv.setLayoutManager(new LinearLayoutManager(this));
        mBookListDetailRv.setAdapter(mDetailAdapter);

        mDetailAdapter.setListener((view, position) -> {
            String bookId = mBookBeen.get(position).getBook().get_id();
            BookDetailActivity.startActivity(BookListDetailActivity.this, bookId);
        });
    }
}
