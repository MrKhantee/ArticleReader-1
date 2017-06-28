package com.article.core.book.contract;

import com.article.base.BaseContract;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.ChapterRead;

import java.util.List;

/**
 * Created by Amos on 2017/6/19.
 * Desc：
 */

public interface BookReadContract {
    interface View extends BaseContract.BaseView {
        void showBookToc(List<BookMixAToc.mixToc.Chapters> list);

        void showChapterRead(ChapterRead.Chapter data, int chapter);

        void netError(int chapter);//添加网络处理异常接口
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        //获取小说的源
        void getBookMixAToc(String view, String book);

        //阅读小说
        void getChapterRead(String url, int chapter);

        //换源
        void changerResource(String sourceId);
    }
}
