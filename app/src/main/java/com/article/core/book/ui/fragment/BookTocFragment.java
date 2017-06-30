package com.article.core.book.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Amos on 2017/6/30.
 * Desc：
 */

public class BookTocFragment extends DialogFragment {

    /**
     * 选中位置
     */
    private int selectPosition = 0;


    public static BookTocFragment newInstance() {
        BookTocFragment tocFragment = new BookTocFragment();

        return tocFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
