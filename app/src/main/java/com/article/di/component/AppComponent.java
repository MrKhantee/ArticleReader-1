package com.article.di.component;

import android.content.Context;

import com.article.CoreApplication;
import com.article.base.RealmHelper;
import com.article.common.api.BookApi;
import com.article.di.module.AppModule;
import com.article.di.module.BookApiModule;

import dagger.Component;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */
@Component(modules = {AppModule.class, BookApiModule.class})
public interface AppComponent {

    CoreApplication getApplication();

    BookApi getBookApi();

    RealmHelper getRealmHelper();

    Context getContext();
}
