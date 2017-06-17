package com.article.core.book.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.R;
import com.article.common.Constant;
import com.article.core.book.bean.TopRankList;
import com.article.widget.GlideCircleTransform;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */

public class TopRankActivityAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private List<TopRankList.MaleBean> mGroupList;
    private List<List<TopRankList.MaleBean>> mChildList;

    private OnRvItemClickListener<TopRankList.MaleBean> listener;

    public TopRankActivityAdapter(Context context, List<TopRankList.MaleBean> groupList, List<List<TopRankList.MaleBean>> childList) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);

        this.mGroupList = groupList;
        this.mChildList = childList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View group = mInflater.inflate(R.layout.item_top_rank_group, null);

        TopRankList.MaleBean maleBean = mGroupList.get(groupPosition);
        ImageView ivCover = (ImageView) group.findViewById(R.id.ivRankCover);
        if (!TextUtils.isEmpty(maleBean.cover)) {
            Glide.with(mContext).load(Constant.BOOK_IMAGE_BASE_URL + maleBean.cover)
                    .placeholder(R.drawable.avatar_default)
                    .transform(new GlideCircleTransform(mContext)).into(ivCover);
            group.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(group, groupPosition, mGroupList.get(groupPosition));
                }
            });
        } else {
            ivCover.setImageResource(R.drawable.ic_rank_collapse);
        }

        TextView tvName = (TextView) group.findViewById(R.id.tvRankGroupName);
        tvName.setText(maleBean.title);

        ImageView ivArrow = (ImageView) group.findViewById(R.id.ivRankArrow);
        if (mChildList.get(groupPosition).size() > 0) {
            if (isExpanded) {
                ivArrow.setImageResource(R.drawable.rank_arrow_up);
            } else {
                ivArrow.setImageResource(R.drawable.rank_arrow_down);
            }
        } else {
            ivArrow.setVisibility(View.GONE);
        }
        return group;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View child = mInflater.inflate(R.layout.item_top_rank_child, null);
        TextView tvName = (TextView) child.findViewById(R.id.tvRankChildName);
        TopRankList.MaleBean maleBean = mChildList.get(groupPosition).get(childPosition);
        tvName.setText(maleBean.title);
        child.setOnClickListener(v -> listener.onItemClick(child, childPosition, mChildList.get(groupPosition).get(childPosition)));
        return child;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setItemClickListener(OnRvItemClickListener<TopRankList.MaleBean> listener) {
        this.listener = listener;
    }

    public interface OnRvItemClickListener<T> {
        void onItemClick(View view, int position, T data);
    }
}
