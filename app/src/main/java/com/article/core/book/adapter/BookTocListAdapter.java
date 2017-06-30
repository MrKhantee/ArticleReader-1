package com.article.core.book.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.article.R;
import com.article.common.utils.FileUtils;
import com.article.core.book.bean.BookMixAToc;

import java.util.List;

/**
 * Created by Amos on 2017/6/30.
 * Desc：
 */

public class BookTocListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookMixAToc.mixToc.Chapters> mChapterses;
    private String bookId;
    private int currentChapter;

    public BookTocListAdapter(Context context, List<BookMixAToc.mixToc.Chapters> chapters
            , String bookId, int currentChapter) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mChapterses = chapters;
        this.bookId = bookId;
        this.currentChapter = currentChapter;
    }


    @Override
    public int getCount() {
        return mChapterses.size();
    }

    @Override
    public Object getItem(int position) {
        return mChapterses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TocViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_book_toc_list, parent, false);
            viewHolder = new TocViewHolder();
            viewHolder.tvTocItem = (TextView) convertView.findViewById(R.id.tvTocItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TocViewHolder) convertView.getTag();
        }
        BookMixAToc.mixToc.Chapters chapters = mChapterses.get(position);
        viewHolder.tvTocItem.setText(chapters.title);
        Drawable drawable;
        if (currentChapter == position + 1) {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_activated);
            viewHolder.tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_red));
        } else if (FileUtils.getChapterFile(bookId, position + 1).length() > 10) {
            viewHolder.tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_download);
        } else {
            viewHolder.tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_normal);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        viewHolder.tvTocItem.setCompoundDrawables(drawable, null, null, null);
        return convertView;
    }

    public void setCurrentChapter(int chapter) {
        currentChapter = chapter;
        notifyDataSetChanged();
    }

    class TocViewHolder {
        TextView tvTocItem;
    }
}
