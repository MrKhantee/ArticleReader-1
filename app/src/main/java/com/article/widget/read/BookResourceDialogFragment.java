package com.article.widget.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.R;

/**
 * Created by Amos on 2017/6/29.
 * Desc：
 */

public class BookResourceDialogFragment extends DialogFragment {

    public static BookResourceDialogFragment newInstance() {
        BookResourceDialogFragment dialogFragment = new BookResourceDialogFragment();
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_book_resource, container, false);
        return view;
    }
}
