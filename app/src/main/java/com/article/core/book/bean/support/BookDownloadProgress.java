package com.article.core.book.bean.support;

/**
 * Created by Amos on 2017/7/26.
 * Desc：下载进度
 */

public class BookDownloadProgress {
    public String bookId;

    public int progress;

    public boolean isAlreadyDownload = false;

    public BookDownloadProgress(String bookId, int progress, boolean isAlreadyDownload) {
        this.bookId = bookId;
        this.progress = progress;
        this.isAlreadyDownload = isAlreadyDownload;
    }
}
