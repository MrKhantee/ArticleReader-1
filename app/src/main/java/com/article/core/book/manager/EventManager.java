package com.article.core.book.manager;

import com.article.core.book.bean.support.RefreshCollectionIconEvent;
import com.article.core.book.bean.support.RefreshCollectionListEvent;
import com.article.core.book.bean.support.SubEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Amos on 2017/6/13.
 * Desc：EventBus管理器
 */

public class EventManager {
    public static void refreshCollectionList() {
        EventBus.getDefault().post(new RefreshCollectionListEvent());
    }

    public static void refreshCollectionIcon() {
        EventBus.getDefault().post(new RefreshCollectionIconEvent());
    }

    public static void refreshSubCategory(String minor, String type) {
        EventBus.getDefault().post(new SubEvent(minor, type));
    }


}
