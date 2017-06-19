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
import com.article.core.book.bean.BookLists;
import com.article.core.book.manager.SettingManager;
import com.article.widget.GlideRoundTransform;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/16.
 * Desc：
 */

public class BookListFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_NORMAL = 0;
    private static final int ITEM_MORE = 1;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookLists.BookListsBean> mBeanList;

    public BookListFragmentAdapter(Context context, List<BookLists.BookListsBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBeanList = list;
    }

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        mOnRvItemClickListener = onRvItemClickListener;
    }

    private OnRvItemClickListener mOnRvItemClickListener;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_MORE) {
            view = mInflater.inflate(R.layout.item_load_more, parent, false);
            return new MoreViewHolder(view);
        } else if (viewType == ITEM_NORMAL) {
            view = mInflater.inflate(R.layout.item_book_list, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            BookLists.BookListsBean bookListsBean = mBeanList.get(position);
            if (!SettingManager.getInstance().isNoneCover()) {
                Glide.with(mContext).load(Constant.BOOK_IMAGE_BASE_URL + bookListsBean.cover)
                        .placeholder(R.drawable.cover_default)
                        .transform(new GlideRoundTransform(mContext))
                        .into(viewHolder.mBookListCoverIv);
            } else {
                viewHolder.mBookListCoverIv.setImageResource(R.drawable.cover_default);
            }

            viewHolder.mBookListAuthorTv.setText(bookListsBean.author);
            viewHolder.mBookListTitleTv.setText(bookListsBean.title);
            viewHolder.mBookListDescTv.setText(bookListsBean.desc);
            viewHolder.mBookCountTv.setText(String.format(mContext.getResources()
                    .getString(R.string.book_detail_recommend_book_list_book_count), bookListsBean.bookCount));
            viewHolder.mCollectorCountTv.setText(String.format(mContext.getResources()
                    .getString(R.string.book_detail_recommend_book_list_collector_count), bookListsBean.collectorCount));
            viewHolder.itemView.setOnClickListener(v -> {
                if (mOnRvItemClickListener != null) {
                    mOnRvItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    /**
     * 清除数据
     */
    public void clear() {
        mBeanList.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param list
     * @return
     */
    public boolean addAll(List<BookLists.BookListsBean> list) {
        boolean result = mBeanList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public int getItemCount() {
        return mBeanList != null ? mBeanList.size() : 0;
    }


    @Override
    public int getItemViewType(int position) {
        return mBeanList.get(position) != null ? ITEM_NORMAL : ITEM_MORE;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.book_list_cover_iv)
        ImageView mBookListCoverIv;
        @BindView(R.id.book_list_title_tv)
        TextView mBookListTitleTv;
        @BindView(R.id.book_list_author_tv)
        TextView mBookListAuthorTv;
        @BindView(R.id.book_list_desc_tv)
        TextView mBookListDescTv;
        @BindView(R.id.book_list_book_count_tv)
        TextView mBookCountTv;
        @BindView(R.id.book_list_book_collector_count_tv)
        TextView mCollectorCountTv;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class MoreViewHolder extends RecyclerView.ViewHolder {
        public MoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
