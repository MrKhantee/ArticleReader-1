package com.article.core.book.bean.support;

import com.article.core.book.bean.BookMixAToc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Amos on 2017/7/21.
 * Desc：
 */

public class BookDownloadQueue implements Serializable {
    public String bookId;

    public List<BookMixAToc.mixToc.Chapters> list;
    //开始位置
    public int start;
    //结束位置
    public int end;

    /**
     * 是否已经开始下载
     */
    public boolean isStartDownload = false;

    /**
     * 是否中断下载
     */
    public boolean isCancel = false;

    /**
     * 是否下载完成
     */
    public boolean isFinish = false;

    public BookDownloadQueue(String bookId, List<BookMixAToc.mixToc.Chapters> list, int start, int end) {
        this.bookId = bookId;
        this.list = list;
        this.start = start;
        this.end = end;
    }

    /**
     * 空事件。表示通知继续执行下一条任务
     */
    public BookDownloadQueue() {
    }
}
