package com.article.core.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.R;
import com.article.common.Constant;
import com.article.common.listener.OnRvItemClickListener;
import com.article.core.book.bean.BooksByTag;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/20.
 * Desc：
 */

public class BooksByTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_VIEW = 1;
    private static final int CONTENT_VIEW = 2;


    private boolean isRefresh = false;

    private List<BooksByTag.TagBook> mTagBooks;

    private Context mContext;
    private LayoutInflater mInflater;


    private OnRvItemClickListener mItemClickListener;


    public BooksByTagAdapter(Context context, List<BooksByTag.TagBook> books) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mTagBooks = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == FOOTER_VIEW) {
            view = mInflater.inflate(R.layout.item_load_more, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == CONTENT_VIEW) {
            view = mInflater.inflate(R.layout.item_books_by_tag, parent, false);
            return new ContentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).setVisible(isRefresh);
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            BooksByTag.TagBook tagBook = mTagBooks.get(position);
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
    }

    @Override
    public int getItemCount() {
        return mTagBooks == null ? 1 : mTagBooks.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mTagBooks.size() == position) {
            return FOOTER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    /**
     * 清除数据
     */
    public void clear() {
        mTagBooks.clear();
        notifyDataSetChanged();
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public void setItemClickListener(OnRvItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public boolean addAll(List<BooksByTag.TagBook> books) {
        boolean result = mTagBooks.addAll(books);
        notifyDataSetChanged();
        return result;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public void setVisible(boolean isVisible) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (!isVisible) {
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                params.height = 0;
                params.width = 0;
            }
            itemView.setLayoutParams(params);
        }

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
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
