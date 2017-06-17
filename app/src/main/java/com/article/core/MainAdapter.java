package com.article.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.article.R;
import com.article.common.listener.OnRvItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private OnRvItemClickListener mClickListener;
    private Context mContext;
    private List<MainBean> mBeanList;
    private LayoutInflater mInflater;

    public MainAdapter(Context context, List<MainBean> beanList) {
        this.mContext = context;
        mBeanList = beanList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setItemClickListener(OnRvItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_main, null);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(holder.itemView, position);
            }
        });
        MainBean mainBean = mBeanList.get(position);
        holder.mTvTitle.setText(mainBean.getTitle());
        holder.mIvIcon.setImageResource(mainBean.getIconResId());
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivIcon)
        ImageView mIvIcon;

        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.rl_main)
        RelativeLayout mRlMain;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
