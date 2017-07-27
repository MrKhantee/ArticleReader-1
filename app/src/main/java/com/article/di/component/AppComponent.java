package com.article.di.component;

import android.content.Context;

import com.article.CoreApplication;
import com.article.common.api.BookApi;
import com.article.common.api.FunApi;
import com.article.common.api.WelfareApi;
import com.article.di.module.AppModule;
import com.article.di.module.BookApiModule;
import com.article.di.module.FunApiModule;
import com.article.di.module.WelfareApiModule;

import dagger.Component;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */
@Component(modules = {AppModule.class, BookApiModule.class,
        FunApiModule.class, WelfareApiModule.class})
public interface AppComponent {

    CoreApplication getApplication();

    BookApi getBookApi();

    FunApi getFunApi();

    WelfareApi getWelfareApi();

    Context getContext();

}
