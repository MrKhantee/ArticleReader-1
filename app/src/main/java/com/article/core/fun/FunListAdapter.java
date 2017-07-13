package com.article.core.fun;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        if (itemsBean.getUser()!=null){
            Glide.with(mContext).load(itemsBean.getUser().getThumb())
                    .placeholder(R.drawable.anony)
                    .transform(new GlideCircleTransform(mContext))
                    .into(holder.mUserAvatarIv);
        }
    }

    @Override
    public int getItemCount() {
        return mItemsBeanList.size();
    }

    class FunViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fun_item_user_avatar_iv)
        ImageView mUserAvatarIv;
        @BindView(R.id.fun_item_user_name_tv)
        TextView mUSerNameTv;
        @BindView(R.id.fun_item_content_tv)
        TextView mContentTv;
        @BindView(R.id.fun_item_image_iv)
        ImageView mImageIv;
        @BindView(R.id.fun_item_count_tv)
        TextView mCountTv;
        @BindView(R.id.fun_item_comment_ll)
        LinearLayout mCommentLl;
        @BindView(R.id.fun_item_comment_user_name_tv)
        TextView mCommentUserNameTv;
        @BindView(R.id.fun_item_comment_content_tv)
        TextView mCommentContentTv;

        public FunViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
