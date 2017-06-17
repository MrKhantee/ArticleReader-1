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
import com.article.core.book.bean.BookListDetail;
import com.article.core.book.bean.BookListDetailMsg;
import com.article.core.book.manager.SettingManager;
import com.article.widget.GlideCircleTransform;
import com.article.widget.GlideRoundTransform;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/15.
 * Desc：
 */

public class BookListDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_HEADER = 0;
    private final static int ITEM_CONTENT = 1;
    private final static int ITEM_FOOTER = 2;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookListDetail.BookListBean.BooksBean> mBeanList;
    private OnRvItemClickListener mListener;

    public void setListener(OnRvItemClickListener listener) {
        mListener = listener;
    }

    public void setDetailMsg(BookListDetailMsg detailMsg) {
        mDetailMsg = detailMsg;
        notifyDataSetChanged();
    }

    private BookListDetailMsg mDetailMsg;


    public BookListDetailAdapter(Context context, List<BookListDetail.BookListBean.BooksBean> list,
                                 BookListDetailMsg detailMsg) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBeanList = list;
        mDetailMsg = detailMsg;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_HEADER) {
            view = mInflater.inflate(R.layout.item_book_list_detail_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_FOOTER) {
            view = mInflater.inflate(R.layout.item_load_more, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = mInflater.inflate(R.layout.item_book_list_detail_book, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int newPosition = position - 1;
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            Glide.with(mContext)
                    .load(Constant.BOOK_IMAGE_BASE_URL + mDetailMsg.avatar)
                    .placeholder(R.drawable.avatar_default)
                    .transform(new GlideCircleTransform(mContext))
                    .into(viewHolder.mAvatarIv);
            viewHolder.mAuthorTv.setText(mDetailMsg.author);
            viewHolder.mTitleTv.setText(mDetailMsg.title);
            viewHolder.mDescTv.setText(mDetailMsg.desc);
        } else if (holder instanceof ContentViewHolder) {
            BookListDetail.BookListBean.BooksBean.BookBean book = mBeanList.get(newPosition).getBook();
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            if (!SettingManager.getInstance().isNoneCover()) {
                Glide.with(mContext)
                        .load(Constant.BOOK_IMAGE_BASE_URL + book.getCover())
                        .placeholder(R.drawable.cover_default)
                        .transform(new GlideRoundTransform(mContext))
                        .into(viewHolder.mBookCoverIv);
            } else {
                viewHolder.mBookCoverIv.setImageResource(R.drawable.cover_default);
            }
            viewHolder.mBookTitleTv.setText(book.getTitle());
            viewHolder.mBookAuthorTv.setText(book.getAuthor());
            viewHolder.mBookDetailTv.setText(book.getLongIntro());
            viewHolder.mBookWordCountTv.setText(String
                    .format(mContext.getString(R.string.subject_book_list_detail_book_word_count),
                            book.getWordCount() / 10000));
            viewHolder.mLatelyFollowerTv.setText(String
                    .format(mContext.getString(R.string.subject_book_list_detail_book_lately_follower)
                            , book.getLatelyFollower()));
            viewHolder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClick(v, newPosition);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBeanList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        } else if (position - mBeanList.size() - 1 >= 0) {
            return ITEM_FOOTER;
        } else {
            return ITEM_CONTENT;
        }
    }

    public void clear() {
        mBeanList.clear();
        notifyDataSetChanged();
    }

    public boolean addAll(List<BookListDetail.BookListBean.BooksBean> beanList) {
        boolean result = mBeanList.addAll(beanList);
        notifyDataSetChanged();
        return result;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_list_detail_header_title_tv)
        TextView mTitleTv;
        @BindView(R.id.book_list_detail_header_desc_tv)
        TextView mDescTv;
        @BindView(R.id.book_list_detail_header_author_avatar_iv)
        ImageView mAvatarIv;
        @BindView(R.id.book_list_detail_header_author_tv)
        TextView mAuthorTv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_list_detail_book_cover_iv)
        ImageView mBookCoverIv;
        @BindView(R.id.book_list_detail_book_title_tv)
        TextView mBookTitleTv;
        @BindView(R.id.book_list_detail_book_author_tv)
        TextView mBookAuthorTv;
        @BindView(R.id.book_list_detail_book_lately_follower_tv)
        TextView mLatelyFollowerTv;
        @BindView(R.id.book_list_detail_book_word_count_tv)
        TextView mBookWordCountTv;
        @BindView(R.id.book_list_detail_book_detail_tv)
        TextView mBookDetailTv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
