package com.article.core.welfare.adapter;

import android.content.Context;

import com.article.R;
import com.article.base.BaseRVAdapter;
import com.article.base.BaseRVHolder;
import com.article.core.welfare.bean.GankMeiziBean;

import java.util.List;

/**
 * Created by Amos on 2017/7/26.
 * Desc：
 */

public class GankMeiziAdapter extends BaseRVAdapter<GankMeiziBean.ResultsBean> {
    public GankMeiziAdapter(Context context, List<GankMeiziBean.ResultsBean> list) {
        super(context, list, R.layout.item_gank_mei_zi);
    }


    @Override
    protected void onBindData(BaseRVHolder viewHolder, int position, GankMeiziBean.ResultsBean item) {
        viewHolder.setText(R.id.tvGankMeiZi, item.getDesc());
        viewHolder.setImageUrl(R.id.ivGankMeiZi, item.getUrl());
        System.out.println(item.getDesc());
    }
}
