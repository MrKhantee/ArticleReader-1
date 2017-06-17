package com.article.core.book.ui.fragment;

import com.article.R;
import com.article.base.BaseFragment;
import com.article.di.component.AppComponent;

/**
 * Created by Amos on 2017/6/9.
 * Desc：
 */
public class BookShelfFragment extends BaseFragment {


    public static BookShelfFragment newInstance() {
        BookShelfFragment fragment = new BookShelfFragment();
        return fragment;
    }


    @Override
    public void attachView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_shelf;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
