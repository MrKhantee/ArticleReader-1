package com.article.core.book.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.article.R;

import java.util.List;

/**
 * Created by Amos on 2017/6/18.
 * Desc：
 */

public class MinorAdapter extends BaseAdapter {

    private List<String> minors;
    private Context mContext;
    private LayoutInflater mInflater;

    private int currentItem = 0;

    public MinorAdapter(Context context, List<String> minors) {
        this.mContext = context;
        this.minors = minors;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return minors == null ? 0 : minors.size();
    }

    @Override
    public Object getItem(int position) {
        return minors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_minor_list, null);
            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.tvMinorItem);
            viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.ivMinorChecked);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (currentItem == position) {
            viewHolder.mIcon.setVisibility(View.VISIBLE);
            viewHolder.mName.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.mIcon.setVisibility(View.INVISIBLE);
            viewHolder.mName.setTextColor(mContext.getResources().getColor(R.color.common_h1));
        }
        viewHolder.mName.setText(minors.get(position));

        return convertView;
    }

    public void setItemCheck(int position) {
        currentItem = position;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView mName;
        ImageView mIcon;
    }
}
