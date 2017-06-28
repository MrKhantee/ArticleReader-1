package com.article.widget.read;

/**
 * Created by Amos on 2017/6/28.
 * Desc：
 */

public interface OnReadStateChangeListener {
    void onChapterChanged(int chapter);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onCenterClick();

    void onFlip();
}
