package com.article.di.component;

import android.content.Context;

import com.article.CoreApplication;
import com.article.base.RealmHelper;
import com.article.common.api.BookApi;
import com.article.common.api.FunApi;
import com.article.di.module.AppModule;
import com.article.di.module.BookApiModule;
import com.article.di.module.FunApiModule;

import dagger.Component;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */
@Component(modules = {AppModule.class, BookApiModule.class, FunApiModule.class})
public interface AppComponent {

    CoreApplication getApplication();

    BookApi getBookApi();

    RealmHelper getRealmHelper();

    Context getContext();

    FunApi getFunApi();
}
