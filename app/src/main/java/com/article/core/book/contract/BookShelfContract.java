package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.data.CollectionBook;

import java.util.List;

/**
 * Created by Amos on 2017/6/21.
 * Desc：
 */

public interface BookShelfContract {
    interface View extends BaseContract.BaseView {
        void showCollectionBook(List<CollectionBook> collectionBooks);

        void showChapter(String bookId,List<BookMixAToc.mixToc.Chapters> mChapters);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCollectionBook();

        void getTopMac(String bookId);
    }
}
