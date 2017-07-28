package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.SearchDetail;

import java.util.List;

/**
 * Created by Amos on 2017/7/28.
 * Desc：
 */

public interface SearchContract {
    interface View extends BaseContract.BaseView {
        void showHotWordList(List<String> list);

        void showAutoCompleteList(List<String> list);

        void showSearchResultList(List<SearchDetail.SearchBooks> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getHotWordList();

        void getAutoCompleteList(String query);

        void getSearchResultList(String query);
    }
}
