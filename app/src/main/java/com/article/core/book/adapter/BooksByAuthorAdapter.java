package com.article.core.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.R;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.bean.BooksByAuthor;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByAuthorAdapter extends RecyclerView.Adapter<BooksByAuthorAdapter.ContentViewHolder> {

    private List<BooksByAuthor.BooksBean> mTagBooks;
    private Context mContext;
    private LayoutInflater mInflater;
    private OnRvItemClickListener mItemClickListener;


    public BooksByAuthorAdapter(Context context, List<BooksByAuthor.BooksBean> books) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mTagBooks = books;
    }


    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_books_by_tag, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder viewHolder, int position) {

        BooksByAuthor.BooksBean tagBook = mTagBooks.get(position);
        Glide.with(mContext).load(Constant.BOOK_IMAGE_BASE_URL + tagBook.cover)
                .placeholder(R.drawable.cover_default)
                .into(viewHolder.mTagCoverIv);
        viewHolder.mTagAuthorTv.setText((tagBook.author == null ? "未知" : tagBook.author) + "|"
                + (tagBook.majorCate == null ? "未知" : tagBook.majorCate));
        viewHolder.mTagTitleTv.setText(tagBook.title);
        viewHolder.mTagShortTv.setText(tagBook.shortIntro);
        viewHolder.mTagMsgTv.setText(String.format(mContext.getResources().getString(R.string.sub_ran_cate_msg),
                tagBook.latelyFollower, !tagBook.retentionRatio.equals("") ? tagBook.retentionRatio : "0"));
        viewHolder.itemView.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTagBooks == null ? 0 : mTagBooks.size();
    }


    /**
     * 清除数据
     */
    public void clear() {
        mTagBooks.clear();
        notifyDataSetChanged();
    }


    public void setItemClickListener(OnRvItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public boolean addAll(List<BooksByAuthor.BooksBean> books) {
        boolean result = mTagBooks.addAll(books);
        notifyDataSetChanged();
        return result;
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.books_tag_cover_iv)
        ImageView mTagCoverIv;
        @BindView(R.id.books_tag_title_tv)
        TextView mTagTitleTv;
        @BindView(R.id.books_tag_author_tv)
        TextView mTagAuthorTv;
        @BindView(R.id.books_tag_short_tv)
        TextView mTagShortTv;
        @BindView(R.id.books_tag_msg_tv)
        TextView mTagMsgTv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
