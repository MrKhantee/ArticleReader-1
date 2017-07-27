package com.article.core.fun;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.R;
import com.article.widget.GlideCircleTransform;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/7/13.
 * Desc：
 */

public class FunListAdapter extends RecyclerView.Adapter<FunListAdapter.FunViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FunBean.ItemsBean> mItemsBeanList;

    public FunListAdapter(Context context, List<FunBean.ItemsBean> itemsBeanList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mItemsBeanList = itemsBeanList;
    }

    @Override
    public FunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_fun_list, parent, false);
        return new FunViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FunViewHolder holder, int position) {
        FunBean.ItemsBean itemsBean = mItemsBeanList.get(position);
        if (itemsBean.getUser() != null) {
            Glide.with(mContext).load("https:"+itemsBean.getUser().getMedium())
                    .transform(new GlideCircleTransform(mContext))
                    .placeholder(R.drawable.anony)
                    .into(holder.mUserAvatarIv);
            holder.mUserNameTv.setText(itemsBean.getUser().getLogin());
        } else {
            holder.mUserNameTv.setText("匿名用户");
        }
        if (itemsBean.getFormat().equals("word")) {
            holder.mImageIv.setVisibility(View.GONE);
        } else if (itemsBean.getFormat().equals("image")) {
            holder.mImageIv.setVisibility(View.VISIBLE);
        }
        holder.mContentTv.setText(itemsBean.getContent());
        FunBean.ItemsBean.HotCommentBean hot_comment = itemsBean.getHot_comment();
        if (hot_comment == null) {
            holder.mCommentLl.setVisibility(View.GONE);
        } else {
            holder.mCommentLl.setVisibility(View.VISIBLE);
            holder.mCommentUserNameTv.setText(String.format(mContext.getString(R.string.fun_list_item_comment_user),
                    hot_comment.getUser().getLogin()));
            holder.mCommentContentTv.setText(hot_comment.getContent());
        }
        holder.mCountTv.setText(String.format(mContext.getString(R.string.fun_list_item_count),
                itemsBean.getComments_count(), itemsBean.getShare_count()));
    }

    public boolean addAll(List<FunBean.ItemsBean> itemsBeen) {
        boolean result = mItemsBeanList.addAll(itemsBeen);
        notifyDataSetChanged();
        return result;
    }

    public void clear() {
        mItemsBeanList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItemsBeanList.size();
    }

    class FunViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fun_item_user_avatar_iv)
        ImageView mUserAvatarIv;
        @BindView(R.id.fun_item_user_name_tv)
        TextView mUserNameTv;
        @BindView(R.id.fun_item_content_tv)
        TextView mContentTv;
        @BindView(R.id.fun_item_image_iv)
        ImageView mImageIv;
        @BindView(R.id.fun_item_count_tv)
        TextView mCountTv;
        @BindView(R.id.fun_item_comment_ll)
        RelativeLayout mCommentLl;
        @BindView(R.id.fun_item_comment_user_name_tv)
        TextView mCommentUserNameTv;
        @BindView(R.id.fun_item_comment_content_tv)
        TextView mCommentContentTv;

        public FunViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
