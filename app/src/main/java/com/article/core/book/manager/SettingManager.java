package com.article.core.book.manager;

import com.article.common.Constant;
import com.article.common.utils.AppUtils;
import com.article.common.utils.ScreenUtils;
import com.article.common.utils.SharedPreferencesUtils;

/**
 * Created by Amos on 2017/6/13.
 * Desc：
 */

public class SettingManager {
    private volatile static SettingManager manager;

    public static SettingManager getInstance() {
        return manager != null ? manager : (manager = new SettingManager());
    }

    /**
     * 保存书籍阅读字体大小
     *
     * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
     * @param fontSizePx
     * @return
     */
    public void saveFontSize(String bookId, int fontSizePx) {
        // 书籍对应
        SharedPreferencesUtils.getInstance().putInt(getFontSizeKey(bookId), fontSizePx);
    }

    /**
     * 保存全局生效的阅读字体大小
     *
     * @param fontSizePx
     */
    public void saveFontSize(int fontSizePx) {
        saveFontSize("", fontSizePx);
    }

    public int getReadFontSize(String bookId) {
        return SharedPreferencesUtils.getInstance().getInt(getFontSizeKey(bookId), ScreenUtils.dpToPxInt(16));
    }

    public int getReadFontSize() {
        return getReadFontSize("");
    }

    private String getFontSizeKey(String bookId) {
        return bookId + "-readFontSize";
    }

    public int getReadBrightness() {
        return SharedPreferencesUtils.getInstance().getInt(getLightnessKey(),
                (int) ScreenUtils.getScreenBrightness(AppUtils.getAppContext()));
    }

    private String getLightnessKey() {
        return "readLightness";
    }

    public void removeReadProgress(String bookId) {
        SharedPreferencesUtils.getInstance()
                .remove(getChapterKey(bookId))
                .remove(getStartPosKey(bookId))
                .remove(getEndPosKey(bookId));
    }

    private String getChapterKey(String bookId) {
        return bookId + "-chapter";
    }

    private String getStartPosKey(String bookId) {
        return bookId + "-startPos";
    }

    private String getEndPosKey(String bookId) {
        return bookId + "-endPos";
    }
    public boolean isNoneCover() {
        return SharedPreferencesUtils.getInstance().getBoolean("isNoneCover", false);
    }

    /**
     * 获取用户选择性别
     *
     * @return
     */
    public String getUserChooseSex() {
        return SharedPreferencesUtils.getInstance().getString("userChooseSex", Constant.Gender.MALE);
    }
}
