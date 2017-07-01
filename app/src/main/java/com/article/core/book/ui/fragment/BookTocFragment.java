package com.article.core.book.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.R;
import com.article.core.book.adapter.BookTocAdapter;
import com.article.core.book.bean.BookMixAToc;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Amos on 2017/6/30.
 * Desc：
 */

public class BookTocFragment extends DialogFragment {

    /**
     * 选中位置
     */
    private int selectPosition = 0;
    private String bookId;
    private int currentChapter = 1;

    public static final String ARGS_BOOK_ID = "bookId";
    public static final String ARGS_CURRENT_CHAPTER = "currentChapter";

    private Unbinder mUnbinder;

    @BindView(R.id.toc_list_title_tv)
    TextView mTitleTv;
    @BindView(R.id.toc_list_close_iv)
    ImageView mCloseIv;
    @BindView(R.id.toc_list_rv)
    RecyclerView mListRv;

    private Context mContext;
    private List<BookMixAToc.mixToc.Chapters> mChapters;
    private BookTocAdapter mAdapter;

    public void setOnItemClickListener(OnTocItemClickListener itemClick) {
        mItemClick = itemClick;
    }

    public OnTocItemClickListener getItemClick() {
        return mItemClick;
    }

    private OnTocItemClickListener mItemClick;

    public static BookTocFragment newInstance(Context context, String bookId, int currentChapter) {
        BookTocFragment tocFragment = new BookTocFragment(context);
        Bundle args = new Bundle();
        args.putString(ARGS_BOOK_ID, bookId);
        args.putInt(ARGS_CURRENT_CHAPTER, currentChapter);
        tocFragment.setArguments(args);
        return tocFragment;
    }

    public BookTocFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        bookId = arguments.getString(ARGS_BOOK_ID);
        currentChapter = arguments.getInt(ARGS_CURRENT_CHAPTER);

        mChapters = new ArrayList<>();
        mAdapter = new BookTocAdapter(getContext(), mChapters, bookId, currentChapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_toc, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mListRv.setHasFixedSize(true);
        mListRv.setLayoutManager(new LinearLayoutManager(mContext));
        mListRv.setAdapter(mAdapter);
        mAdapter.setOnTocItemClickListener(getItemClick());
        return view;
    }

    @OnClick(R.id.toc_list_close_iv)
    public void onCloseClick() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

    public void addAllChapters(List<BookMixAToc.mixToc.Chapters> chapters) {
        if (mAdapter != null) {
            mAdapter.addAll(chapters);
        }
    }

    public interface OnTocItemClickListener {
        void onItemClick(int position);
    }
}
