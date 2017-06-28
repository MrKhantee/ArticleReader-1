package com.article.core.book.manager;

import android.content.Context;

import com.article.common.Constant;
import com.article.common.utils.ACache;
import com.article.common.utils.AppUtils;
import com.article.common.utils.FileUtils;
import com.article.common.utils.LogUtils;
import com.article.common.utils.SharedPreferencesUtils;
import com.article.common.utils.StringUtils;
import com.article.core.book.bean.ChapterRead;

import java.io.File;
import java.util.List;

/**
 * Created by Amos on 2017/6/13.
 * Desc：
 */

public class CacheManager {
    private static CacheManager manager;

    public static CacheManager getInstance() {
        return manager == null ? (manager = new CacheManager()) : manager;
    }

    public List<String> getSearchHistory() {
        return SharedPreferencesUtils.getInstance().getObject(getSearchHistoryKey(), List.class);
    }

    public void saveSearchHistory(Object obj) {
        SharedPreferencesUtils.getInstance().putObject(getSearchHistoryKey(), obj);
    }

    private String getSearchHistoryKey() {
        return "searchHistory";
    }


    private String getCollectionKey() {
        return "my_book_lists";
    }


    public void removeTocList(Context mContext, String bookId) {
        ACache.get(mContext).remove(getTocListKey(bookId));
    }

    private String getTocListKey(String bookId) {
        return bookId + "-bookToc";
    }

    public File getChapterFile(String bookId, int chapter) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        if (file != null && file.length() > 50)
            return file;
        return null;
    }


    /**
     * 获取缓存大小
     *
     * @return
     */
    public synchronized String getCacheSize() {
        long cacheSize = 0;

        try {
            String cacheDir = Constant.BASE_PATH;
            cacheSize += FileUtils.getFolderSize(cacheDir);
            if (FileUtils.isSdCardAvailable()) {
                String extCacheDir = AppUtils.getAppContext().getExternalCacheDir().getPath();
                cacheSize += FileUtils.getFolderSize(extCacheDir);
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        return FileUtils.formatFileSizeToString(cacheSize);
    }

    /**
     * 缓存章节信息
     *
     * @param bookId
     * @param chapter
     * @param data
     */
    public void saveChapterFile(String bookId, int chapter, ChapterRead.Chapter data) {
        File file = FileUtils.getChapterFile(bookId, chapter);
        FileUtils.writeFile(file.getAbsolutePath(), StringUtils.formatContent(data.body), false);
    }
}
