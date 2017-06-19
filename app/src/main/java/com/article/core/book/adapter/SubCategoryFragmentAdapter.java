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
import com.article.core.book.bean.BooksByCats;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/19.
 * Desc：
 */

public class SubCategoryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BooksByCats.BooksBean> mBeanList;

    private static final int FOOTER_VIEW = 1;
    private static final int CONTENT_VIEW = 2;

    private boolean isRefresh = false;

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }


    public SubCategoryFragmentAdapter(Context context, List<BooksByCats.BooksBean> beanList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBeanList = beanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == CONTENT_VIEW) {
            view = mInflater.inflate(R.layout.item_sub_category_book, parent, false);
            return new ContentViewHolder(view);
        } else if (viewType == FOOTER_VIEW) {
            view = mInflater.inflate(R.layout.item_load_more, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.setVisible(isRefresh);
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            BooksByCats.BooksBean booksBean = mBeanList.get(position);

            viewHolder.mAuthorTv.setText((booksBean.author == null ? "未知" : booksBean.author) + "|"
                    + (booksBean.majorCate == null ? "未知" : booksBean.majorCate));
            viewHolder.mTitleTv.setText(booksBean.title);
            viewHolder.mShortTv.setText(booksBean.shortIntro);
            viewHolder.mMsgTv.setText(String.format(mContext.getResources().getString(R.string.sub_ran_cate_msg),
                    booksBean.latelyFollower, booksBean.retentionRatio.equals("") ? booksBean.retentionRatio : "0"));
            Glide.with(mContext).load(Constant.BOOK_IMAGE_BASE_URL + booksBean.cover)
                    .placeholder(R.drawable.cover_default).into(viewHolder.mCoverIv);

        }

    }

    @Override
    public int getItemCount() {
        return mBeanList == null ? 1 : mBeanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mBeanList.size()) {
            return FOOTER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
//        return mBeanList.get(position) != null ? CONTENT_VIEW : FOOTER_VIEW;
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
    public boolean addAll(List<BooksByCats.BooksBean> list) {
        boolean result = mBeanList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sub_cate_cover_iv)
        ImageView mCoverIv;
        @BindView(R.id.sub_cate_title_tv)
        TextView mTitleTv;
        @BindView(R.id.sub_cate_author_tv)
        TextView mAuthorTv;
        @BindView(R.id.sub_cate_short_tv)
        TextView mShortTv;
        @BindView(R.id.sub_cate_msg_tv)
        TextView mMsgTv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public void setVisible(boolean isVisible) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (!isVisible) {
                params.height =  LinearLayout.LayoutParams.WRAP_CONTENT;
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
}
