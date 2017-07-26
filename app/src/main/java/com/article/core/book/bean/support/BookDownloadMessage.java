package com.article.core.book.bean.support;

/**
 * Created by Amos on 2017/7/26.
 * Desc：下载信息
 */

public class BookDownloadMessage {
    public String bookId;

    public String message;

    public boolean isComplete = false;

    public BookDownloadMessage(String bookId, String message, boolean isComplete) {
        this.bookId = bookId;
        this.message = message;
        this.isComplete = isComplete;
    }
}
